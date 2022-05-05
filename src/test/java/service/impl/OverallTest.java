package service.impl;


import entity.commercialOrder.CommercialOrder;
import entity.commercialOrder.OrderStatus;
import entity.commercialService.CommercialService;
import entity.commercialService.defaultServices.MainServices;
import entity.commericalOffer.CommercialOffer;
import entity.user.Customer;
import entity.user.Expert;
import entity.user.SignUpStatus;
import org.junit.jupiter.api.*;
import repository.commercialOffer.CommercialOfferRepoImpl;
import repository.commercialService.CommercialServiceRepoImpl;
import repository.commericalOrder.CommercialOrderRepoImpl;
import repository.user.customer.CustomerRepoImpl;
import repository.user.expert.ExpertRepoImpl;
import service.commercialOffer.CommercialOfferServiceImpl;
import service.commercialOrder.CommercialOrderServiceImpl;
import service.commercialService.CommercialServiceServiceImpl;
import service.user.customer.CustomerServiceImpl;
import service.user.expert.ExpertServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;



//since adding commercialOffers to the database requires adding commercialServices,commercialOrders,Customers
//and users,I have created a simple overall test of the application from start to finish
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OverallTest {

    private static CommercialOrderServiceImpl commercialOrderService=new CommercialOrderServiceImpl(new CommercialOrderRepoImpl());
    private static ExpertServiceImpl expertService=new ExpertServiceImpl(new ExpertRepoImpl());
    private static CustomerServiceImpl customerService=new CustomerServiceImpl(new CustomerRepoImpl());
    private static CommercialServiceServiceImpl commercialServiceService=new CommercialServiceServiceImpl(new CommercialServiceRepoImpl());
    private static CommercialOrder commercialOrder=new CommercialOrder();
    private static CommercialOfferRepoImpl repo=new CommercialOfferRepoImpl();
    private static CommercialOfferServiceImpl commercialOfferService=new CommercialOfferServiceImpl(repo);
    private static CommercialOffer commercialOffer=new CommercialOffer();
    private static Customer customer=new Customer();
    private static Expert expert=new Expert();

    @BeforeAll
    static void setup(){

        customer.setEmail("customerEmail");
        customer.setPassword("customerPassword");
        customer.setUsername("customerUsername");
        customer.setBalance(20000000);
        customerService.add(customer);

        CommercialService commercialService=new CommercialService();
        commercialService.setName(MainServices.CLEANING.toString());
        commercialService.setBasePrice(500);
        commercialServiceService.add(commercialService);


        expert.setUsername("expertUsername");
        expert.setPassword("expertPassword");
        expert.setEmail("expertEmail");
        expert.setSignUpStatus(SignUpStatus.SUBMITTED);
        expert.addExpertCommercialService(commercialService);
        expertService.add(expert);


        //1
        //order being added by customer
        commercialOrder.setOrderCustomer(customer);
        commercialOrder.setCustomerPriceOffer(600);
        commercialOrder.setCommercialService(commercialService);
        commercialOrderService.add(commercialOrder);


        commercialOffer.setOfferExpert(expert);
        commercialOffer.setOfferedPrice(700);
        commercialOffer.setOfferCommercialOrder(commercialOrder);

    }





//2
//setting an offer by the expert for a specific order
    @Test
    @Order(1)
    void addOffer(){
        commercialOfferService.add(commercialOffer);
        var id=commercialOffer.getId();
        var loaded=commercialOfferService.showInfo(id,CommercialOffer.class);

        assertAll(
                ()->assertEquals(loaded.getOfferCommercialOrder().getId(),commercialOrder.getId()),
                ()->assertEquals(loaded.getOfferExpert().getId(),expert.getId()),
                ()->assertEquals(commercialOrder.getOrderCustomer().getId(),customer.getId()),
                ()->assertEquals(loaded.getOfferCommercialOrder().getOrderStatus(), OrderStatus.EXPERT_CHOOSING),
                ()->assertNotNull(loaded.getOfferedPrice())
        );
    }


//3
//accepting offer by the customer
    @Test
    @Order(2)
    void selectOfferByCustomer(){
        commercialOrderService.selectOfferByCustomer(commercialOrder,commercialOffer);

        var loadedCommercialOrder=commercialOrderService.showInfo(commercialOrder.getId(),CommercialOrder.class);
        var loadedCommercialOffer=commercialOfferService.showInfo(commercialOffer.getId(), CommercialOffer.class);

        assertAll(
                ()->assertEquals(loadedCommercialOrder.getOrderStatus(),OrderStatus.EXPERT_CHOOSING),
                ()->assertTrue(loadedCommercialOffer.isSelected())
        );
    }


//4
//order being accepted by the expert
    @Test
    @Order(3)
    void orderSelectByExpert(){
        commercialOrderService.orderSelectByExpert(commercialOrder);

        var loadedCommercialOrder=commercialOrderService.showInfo(commercialOrder.getId(),CommercialOrder.class);
        var loadedCommercialOffer=commercialOfferService.showInfo(commercialOffer.getId(), CommercialOffer.class);

        assertAll(
                ()->assertEquals(loadedCommercialOrder.getOrderStatus(),OrderStatus.EXPERT_ON_WAY),
                ()->assertTrue(loadedCommercialOffer.isAccepted())
        );
    }

//5
//expert starting the order
    @Test
    @Order(4)
    void orderStart(){
        commercialOrderService.orderStart(commercialOrder);

        var loadedCommercialOrder=commercialOrderService.showInfo(commercialOrder.getId(),CommercialOrder.class);
        var loadedCommercialOffer=commercialOfferService.showInfo(commercialOffer.getId(), CommercialOffer.class);

        assertAll(
                ()->assertEquals(loadedCommercialOrder.getOrderStatus(),OrderStatus.STARTED)
        );
    }


//6
//expert finishing the order
    @Test
    @Order(5)
    void orderFinish(){
        commercialOrderService.orderFinish(commercialOrder,4);

        var loadedCommercialOrder=commercialOrderService.showInfo(commercialOrder.getId(),CommercialOrder.class);
        var loadedCommercialOffer=commercialOfferService.showInfo(commercialOffer.getId(), CommercialOffer.class);

        assertAll(
                ()->assertEquals(loadedCommercialOrder.getOrderStatus(),OrderStatus.PAID),
                ()->assertEquals(loadedCommercialOrder.getScore(),4)
                );
    }
}
