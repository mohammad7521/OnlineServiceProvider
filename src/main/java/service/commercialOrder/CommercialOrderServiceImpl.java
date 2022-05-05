package service.commercialOrder;

import entity.commercialOrder.CommercialOrder;
import entity.commercialOrder.OrderStatus;
import entity.commericalOffer.CommercialOffer;
import exception.*;
import repository.commercialOffer.CommercialOfferRepoImpl;
import repository.commericalOrder.CommercialOrderRepo;
import service.base.BaseServiceImpl;
import service.commercialOffer.CommercialOfferServiceImpl;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;


public class CommercialOrderServiceImpl extends BaseServiceImpl<CommercialOrder,Integer,CommercialOrderRepo>
    implements CommercialOrderService{

    public CommercialOrderServiceImpl(CommercialOrderRepo repo) {
        super(repo);
    }
    protected CommercialOfferServiceImpl commercialOfferService;


    //1
    //adding order by customer
    @Override
    public CommercialOrder add(CommercialOrder commercialOrder){


        if (commercialOrder.getCommercialService()==null || commercialOrder.getOrderCustomer()==null){
            throw new FillRequirements();
        }
        if (commercialOrder.getCustomerPriceOffer()<commercialOrder.getCommercialService()
                .getBasePrice())
            throw new LowPrice();
        else

        commercialOrder.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFERS);
        Date date=new Date(System.currentTimeMillis());
        Time time=new Time(System.currentTimeMillis());
        commercialOrder.setDate(date);
        commercialOrder.setTime(time);
        return repo.add(commercialOrder);
    }


    //3
    //setting an offer as selected by the user
    @Override
    public void selectOfferByCustomer(CommercialOrder commercialOrder, CommercialOffer commercialOffer){

        if (commercialOffer.getOfferCommercialOrder().getId()==commercialOrder.getId()) {
            if (commercialOrder.getOrderCustomer().getBalance() >= commercialOffer.getOfferedPrice()) {
                commercialOrder.setChosenCommercialOffer(commercialOffer);
//                commercialOrder.setOrderStatus(OrderStatus.EXPERT_CHOOSING);
                commercialOrder.setCustomerPriceOffer(commercialOffer.getOfferedPrice());
                repo.update(commercialOrder);
                commercialOffer.setSelected(true);
                commercialOfferService=new CommercialOfferServiceImpl(new CommercialOfferRepoImpl());
                commercialOfferService.update(commercialOffer, CommercialOffer.class);
            } else throw new LowBalance();
        }else throw new WrongOffer();
    }




    //4
    //expert accepting an offer then the expert is on their way
    @Override
    public void orderSelectByExpert(CommercialOrder commercialOrder){

        if (commercialOrder.getChosenCommercialOffer().isSelected()) {
            commercialOrder.setOrderStatus(OrderStatus.EXPERT_ON_WAY);
            commercialOrder.getChosenCommercialOffer().setAccepted(true);
            repo.update(commercialOrder);

            commercialOfferService=new CommercialOfferServiceImpl(new CommercialOfferRepoImpl());
            commercialOfferService.update(commercialOrder.getChosenCommercialOffer(),CommercialOffer.class);
        }
    }


    //5
    //expert starting the order
    @Override
    public void orderStart(CommercialOrder commercialOrder){

        if (commercialOrder.getOrderStatus()==OrderStatus.EXPERT_ON_WAY) {
            commercialOrder.setOrderStatus(OrderStatus.STARTED);

            LocalDateTime startTime=LocalDateTime.now();

            commercialOrder.getChosenCommercialOffer().setStartTime(startTime);
            commercialOrder.setStartTime(startTime);

            repo.update(commercialOrder);

            commercialOfferService=new CommercialOfferServiceImpl(new CommercialOfferRepoImpl());
            commercialOfferService.update(commercialOrder.getChosenCommercialOffer(),CommercialOffer.class);
        }
    }


    //6
    //expert finishing the order
    @Override
    public void orderFinish(CommercialOrder commercialOrder,int score){

        if (score>5 || score<1){
            throw new WrongScore();
        }
        if (commercialOrder.getOrderStatus()==OrderStatus.STARTED) {
            commercialOrder.setOrderStatus(OrderStatus.DONE);

            LocalDateTime finishTime=LocalDateTime.now();
            commercialOrder.setFinishTime(finishTime);
            commercialOrder.getChosenCommercialOffer().setFinishTime(finishTime);
            commercialOrder.setDuration(Duration.between(commercialOrder.getStartTime(),finishTime));

            var customer=commercialOrder.getOrderCustomer();
            var offer=commercialOrder.getChosenCommercialOffer();
            var expert=offer.getOfferExpert();

            offer.setDuration(Duration.between(commercialOrder.getStartTime(),finishTime));
            customer.setBalance(customer.getBalance()-offer.getOfferedPrice());
            expert.setBalance(offer.getOfferedPrice());

            commercialOrder.setOrderStatus(OrderStatus.PAID);
            commercialOrder.setScore(score);

            repo.update(commercialOrder);
            commercialOfferService=new CommercialOfferServiceImpl(new CommercialOfferRepoImpl());
            commercialOfferService.update(commercialOrder.getChosenCommercialOffer(),CommercialOffer.class);
        }
    }

}
