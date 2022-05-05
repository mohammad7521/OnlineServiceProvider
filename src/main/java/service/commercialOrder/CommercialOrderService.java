package service.commercialOrder;

import entity.commercialOrder.CommercialOrder;
import entity.commericalOffer.CommercialOffer;
import service.base.BaseService;

public interface CommercialOrderService extends BaseService<CommercialOrder,Integer> {

    //selecting an offer as favorite by the customer
    void selectOfferByCustomer(CommercialOrder commercialOrder, CommercialOffer commercialOffer);

    //order being selected by expert
    void orderSelectByExpert(CommercialOrder commercialOrder);

    //order start
    void orderStart(CommercialOrder commercialOrder);

    void orderFinish(CommercialOrder commercialOrder,int score);
}
