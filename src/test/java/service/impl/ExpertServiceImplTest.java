package service.impl;


import entity.commercialService.CommercialService;
import entity.user.Expert;
import entity.user.SignUpStatus;
import exception.DuplicateUser;
import exception.FillRequirements;
import exception.NoSuchId;
import exception.WrongPasswordFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.commercialService.CommercialServiceRepoImpl;
import repository.user.expert.ExpertRepoImpl;
import service.base.UserServiceImplTest;
import service.commercialService.CommercialServiceServiceImpl;
import service.user.expert.ExpertServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class ExpertServiceImplTest extends UserServiceImplTest<Expert,ExpertServiceImpl> {

    @BeforeEach
    void setup(){
        tClass= Expert.class;
        var repo=new ExpertRepoImpl();
        service=new ExpertServiceImpl(repo);

        entity1=new Expert();
        entity1.setUsername("username");
        entity1.setEmail("newEmail");
        entity1.setPassword("newPassword1234");
    }




    @AfterEach
    void tearDown(){
        service.hqlTruncate("User");
        service.hqlTruncate("Service");
    }

    @Test
    void selectService(){
        CommercialService commercialService=new CommercialService();
        CommercialServiceRepoImpl commercialServiceRepo=new CommercialServiceRepoImpl();
        CommercialServiceServiceImpl commercialServiceService=new CommercialServiceServiceImpl(commercialServiceRepo);
        entity1.addExpertCommercialService(commercialService);

        entity1.addExpertCommercialService(commercialService);
        service.add(entity1);

        commercialService.addServiceExpert(entity1);
        commercialServiceService.add(commercialService);

        var loadedEntity=service.showInfo(entity1.getId(),tClass);
        assertAll(
                ()->assertEquals(1,loadedEntity.getExpertCommercialServices().size())
        );
        entity1.removeExpertCommercialService(commercialService);
        service.remove(entity1);

    }


    @Test
    @Override
    protected void update() {

        try {
            service.add(entity1);
            entity1.setPassword("newpassword123123");
            entity1.setEmail("newEmail");
            entity1.setSignUpStatus(SignUpStatus.SUBMITTED);
            service.update(entity1,tClass);
            var loaded = service.showInfo(entity1.getId(), tClass);

            assertAll(
                    () -> assertNotNull(loaded),
                    () -> assertEquals(loaded.getEmail(), entity1.getEmail()),
                    () -> assertEquals(entity1.getPassword(), loaded.getPassword()),
                    ()->assertEquals(entity1.getSignUpStatus(),loaded.getSignUpStatus())
            );
        }catch (DuplicateUser e){
            System.out.println("username alredy exists");
        }catch (FillRequirements e){
            System.out.println("username,password nad email are mandatory");
        } catch (NoSuchId e){
            System.out.println("selected entity does not exist!");
        } catch (WrongPasswordFormat e){
            System.out.println("please enter password in the right format");
        }
        service.remove(entity1);
    }


    @Test
    void submitExpert(){
        service.add(entity1);
        entity1.setSignUpStatus(SignUpStatus.PENDING);
        service.update(entity1,tClass);

        var loadedEntity=service.showInfo(entity1.getId(),tClass);

        assertEquals(loadedEntity.getSignUpStatus(),SignUpStatus.PENDING);
    }
}
