package service.impl;

import entity.commercialOrder.CommercialOrder;
import entity.commercialOrder.OrderStatus;
import entity.commercialService.CommercialService;
import entity.user.Customer;
import exception.DuplicateServiceName;
import exception.FillRequirements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.commercialService.CommercialServiceRepoImpl;
import repository.commericalOrder.CommercialOrderRepoImpl;
import repository.user.customer.CustomerRepoImpl;
import service.base.BaseServiceImplTest;
import service.commercialOrder.CommercialOrderServiceImpl;
import service.commercialService.CommercialServiceServiceImpl;
import service.user.customer.CustomerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;



public class CommercialOrderServiceImpTest extends BaseServiceImplTest<CommercialOrder,Integer,
        CommercialOrderServiceImpl> {

    private CustomerRepoImpl customerRepo=new CustomerRepoImpl();
    private CustomerServiceImpl customerService=new CustomerServiceImpl(customerRepo);
    private CommercialServiceRepoImpl commercialServiceRepo=new CommercialServiceRepoImpl();
    private CommercialServiceServiceImpl commercialServiceService=new CommercialServiceServiceImpl(commercialServiceRepo);

    @BeforeEach
    void setup(){
        tClass= CommercialOrder.class;
        var repo=new CommercialOrderRepoImpl();
        service=new CommercialOrderServiceImpl(repo);
        entity1=new CommercialOrder();
        entity1.setComment("newComment");
        entity1.setCustomerDescription("someDescription");
        entity1.setCustomerPriceOffer(2324);

        Customer customer=new Customer();
        customer.setUsername("username");
        customer.setPassword("pasword");
        customer.setEmail("email");

        customerService.add(customer);

        CommercialService commercialService=new CommercialService();
        commercialServiceService.add(commercialService);

        entity1.setOrderCustomer(customer);
        entity1.setCommercialService(commercialService);
    }


    @AfterEach
    void tearDown(){
        service.hqlTruncate("CommercialOrder");
        service.hqlTruncate("User");
        service.hqlTruncate("Service");
    }



    @Test
    @Override
    protected void update() {

        try {
            service.add(entity1);
            entity1.setComment("changedComment");
            service.update(entity1,tClass);

            var loadedCommercialOrder = service.showInfo(entity1.getId(), CommercialOrder.class);

            assertAll(
                    () -> assertEquals(loadedCommercialOrder.getComment(), entity1.getComment())
            );
        } catch (DuplicateServiceName e) {
            System.out.println("service name already exists!");
        }
    }


    //adding order for a certain service by customer
    @Test
    @Override
    protected void add(){

        try {


            service.add(entity1);

            var loadedCommercialOrder = service.showInfo(entity1.getId(), tClass);

            assertAll(
                    ()->assertEquals(loadedCommercialOrder.getOrderStatus(), OrderStatus.WAITING_FOR_EXPERT_OFFERS)
            );
        }catch (FillRequirements e){
            System.out.println("please fill requirements");
        }
    }
}
