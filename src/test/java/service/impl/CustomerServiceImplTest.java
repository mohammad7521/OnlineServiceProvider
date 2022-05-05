package service.impl;
import entity.user.Customer;
import exception.DuplicateUser;
import exception.FillRequirements;
import exception.NoSuchId;
import exception.WrongPasswordFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.user.customer.CustomerRepoImpl;
import service.base.UserServiceImplTest;
import service.user.customer.CustomerServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class CustomerServiceImplTest extends UserServiceImplTest<Customer,CustomerServiceImpl> {

    @BeforeEach
    void setup(){
        tClass= Customer.class;
        var repo=new CustomerRepoImpl();
        service=new CustomerServiceImpl(repo);

        entity1=new Customer();
        entity1.setUsername("username");
        entity1.setEmail("newEmail");
        entity1.setPassword("newPassword1234");

        entity2=new Customer();
        entity2.setUsername("entity2Username");
        entity2.setPassword("entity2Password");
        entity2.setEmail("entity2Email");


    }


    @AfterEach
    void tearDown(){
        service.hqlTruncate("User");
    }



    @Test
    @Override
    protected void update(){

        try {
            service.addUser(entity1, tClass);
            entity1.setPassword("newpassword123123");
            entity1.setEmail("newEmail");
            service.update(entity1,tClass);

            var loaded = service.showInfo(entity1.getId(), Customer.class);

            assertAll(
                    () -> assertNotNull(loaded),
                    () -> assertEquals(loaded.getEmail(), entity1.getEmail()),
                    () -> assertEquals(entity1.getPassword(), loaded.getPassword())
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
    }

}
