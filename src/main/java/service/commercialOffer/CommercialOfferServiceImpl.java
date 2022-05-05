package service.commercialOffer;

import entity.commercialOrder.CommercialOrder;
import entity.commercialOrder.OrderStatus;
import entity.commercialService.CommercialService;
import entity.commericalOffer.CommercialOffer;
import entity.user.SignUpStatus;
import exception.AccountNotActivated;
import exception.ExpertNotQualified;
import exception.FillRequirements;
import repository.commercialOffer.CommercialOfferRepo;
import repository.commericalOrder.CommercialOrderRepo;
import repository.commericalOrder.CommercialOrderRepoImpl;
import service.base.BaseServiceImpl;
import service.commercialOrder.CommercialOrderServiceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

public class CommercialOfferServiceImpl extends BaseServiceImpl<CommercialOffer,Integer,CommercialOfferRepo>
    implements CommercialOfferService {

    protected CommercialOrderRepoImpl commercialOrderRepo;
    protected CommercialOrderServiceImpl commercialOrderService;

    public CommercialOfferServiceImpl(CommercialOfferRepo repo) {
        super(repo);
    }


    @Override

    //2
    //offer added by expert for a specific order
    public CommercialOffer add(CommercialOffer commercialOffer){
        if (commercialOffer.getOfferExpert()==null || commercialOffer.getOfferCommercialOrder()==null
        || commercialOffer.getOfferedPrice()<1){
            throw new FillRequirements();
        }
        if (commercialOffer.getOfferExpert().getSignUpStatus()!= SignUpStatus.SUBMITTED){
            throw new AccountNotActivated();
        }
        Set<CommercialService> expertCommercialService=commercialOffer.getOfferExpert().getExpertCommercialServices();

        for (CommercialService cs:expertCommercialService){
            if (commercialOffer.getOfferCommercialOrder().getCommercialService().getId()!=cs.getId()){
                throw new ExpertNotQualified();
            }
        }
        CommercialOrder commercialOrder=commercialOffer.getOfferCommercialOrder();
        commercialOrder.addCommercialOffer(commercialOffer);
        commercialOrder.setOrderStatus(OrderStatus.EXPERT_CHOOSING);

        commercialOrderService=new CommercialOrderServiceImpl(new CommercialOrderRepoImpl());
        commercialOrderService.update(commercialOrder,CommercialOrder.class);
        Date date=new Date(System.currentTimeMillis());
        Time time=new Time(System.currentTimeMillis());
        commercialOffer.setDate(date);
        commercialOffer.setTime(time);
        return repo.add(commercialOffer);
    }


}
