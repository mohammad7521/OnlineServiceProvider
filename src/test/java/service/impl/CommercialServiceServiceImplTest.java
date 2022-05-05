package service.impl;

import entity.commercialService.CommercialService;
import entity.commercialService.defaultServices.MainServices;
import exception.DuplicateServiceName;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.commercialService.CommercialServiceRepoImpl;
import service.base.BaseServiceImplTest;
import service.commercialService.CommercialServiceServiceImpl;
import static org.junit.jupiter.api.Assertions.*;



public class CommercialServiceServiceImplTest extends BaseServiceImplTest<CommercialService,Integer,
        CommercialServiceServiceImpl> {

    @BeforeEach
    void setup(){
        tClass= CommercialService.class;
        var repo=new CommercialServiceRepoImpl();
        service=new CommercialServiceServiceImpl(repo);
        entity1=new CommercialService();
        entity1.setName(String.valueOf(MainServices.CLEANING));
        entity1.setBasePrice(2000);
        entity1.setDescription("cleaning houses");
        entity2=new CommercialService();
        entity2.setParentCommercialService(entity1);
    }


    @AfterEach
    void tearDown(){
        service.hqlTruncate("Service");
    }


    @Test
    void commercialServiceAdd(){

        try {
            service.add(entity1);
            service.add(entity2);
        }catch (DuplicateServiceName e){
            System.out.println("service name already exists!");
        }

        var loadedCommercialService=service.showInfo(entity1.getId(),CommercialService.class);
        assertAll(
                ()->assertEquals(loadedCommercialService.getName(),entity1.getName()),
                ()->assertEquals(loadedCommercialService.getBasePrice(),entity1.getBasePrice()),
                ()->assertEquals(loadedCommercialService.getDescription(),entity1.getDescription()),
                ()->assertEquals(loadedCommercialService.getSubCommercialServices().size(),1)
        );

    }


    

    @Test
    @Override
    protected void update() {

        try {
            service.add(entity1);
            entity1.setDescription("newDescription");
            entity1.setName("newName");
            service.add(entity1);
            entity1.setName("newNewName");
            entity1.setDescription("newNewDescription");
            service.update(entity1,tClass);

            var loadedCommercialService = service.showInfo(entity1.getId(), CommercialService.class);

            assertAll(
                    () -> assertEquals(loadedCommercialService.getName(), entity1.getName()),
                    () -> assertEquals(loadedCommercialService.getDescription(), entity1.getDescription())
            );
        } catch (DuplicateServiceName e) {
            System.out.println("service name already exists!");
        }
    }

}
