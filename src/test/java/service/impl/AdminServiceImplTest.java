package service.impl;

import entity.base.User;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.Expert;
import exception.DuplicateUser;
import exception.FillRequirements;
import exception.NoSuchId;
import exception.WrongPasswordFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.user.admin.AdminRepoImpl;
import repository.user.customer.CustomerRepoImpl;
import repository.user.expert.ExpertRepoImpl;
import service.user.admin.AdminServiceImpl;
import service.base.UserServiceImplTest;
import service.user.customer.CustomerServiceImpl;
import service.user.expert.ExpertServiceImpl;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class AdminServiceImplTest extends UserServiceImplTest<Admin, AdminServiceImpl> {

    CustomerRepoImpl customerRepo=new CustomerRepoImpl();
    ExpertRepoImpl expertRepoImpl=new ExpertRepoImpl();
    CustomerServiceImpl customerService=new CustomerServiceImpl(customerRepo);
    ExpertServiceImpl expertService=new ExpertServiceImpl(expertRepoImpl);

    @BeforeEach
    void setup(){
        tClass=Admin.class;
        var repo=new AdminRepoImpl();
        service=new AdminServiceImpl(repo);
        entity1=new Admin();
        entity1.setUsername("username");
        entity1.setEmail("newEmail");
        entity1.setPassword("newPassword1234");
        entity2=new Admin();
        entity2.setUsername("entity2Username");
        entity2.setPassword("entity2Password");
        entity2.setEmail("entity2Email");
    }


    @AfterEach
    void tearDown(){
        service.hqlTruncate("User");
    }



    @Override
    @Test
    protected void update(){

        try {
            service.addUser(entity1, tClass);
            entity1.setPassword("newpassword123123");
            entity1.setEmail("newEmail");
            service.update(entity1,tClass);
            var loaded = service.showInfo(entity1.getId(), Admin.class);

            assertAll(
                    () -> assertNotNull(loaded),
                    () -> assertEquals(loaded.getEmail(), entity1.getEmail()),
                    () -> assertEquals(entity1.getPassword(), loaded.getPassword())
            );
        }catch (DuplicateUser e){
            System.out.println("username already exists");
        }catch (FillRequirements e){
            System.out.println("username,password nad email are mandatory");
        } catch (NoSuchId e){
            System.out.println("selected entity does not exist!");
        } catch (WrongPasswordFormat e){
            System.out.println("please enter password in the right format");
        }
    }



    //list all users
    @Test
    void List(){


        Customer customer=new Customer();
        Expert expert=new Expert();
        var admin=new Admin();

        admin.setUsername("admin");
        admin.setPassword("adminPassword1234");
        admin.setEmail("adminEmail");

        customer.setPassword("customerPassword1234");
        customer.setEmail("customerEmail");
        customer.setUsername("customer");

        expert.setUsername("expert");
        expert.setPassword("expertPassword1234");
        expert.setEmail("expertEmail");

        service.add(admin);
        customerService.add(customer);
        expertService.add(expert);
        List<User> list=service.showAllUsers();


        assertAll(
                ()->assertEquals(list.size(),3),
                ()->assertEquals(list.get(0).getDiscriminatorValue(),1),
                ()->assertEquals(list.get(1).getDiscriminatorValue(),2),
                ()->assertEquals(list.get(2).getDiscriminatorValue(),3)
        );

    }



//filter users based on certain criteria
    @Test
    void filterUser(){

        var user = new Admin();
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("email");
        user.setPassword("password");
        user.setUsername("username");
        service.add(user);

        var customer=new Customer();
        customer.setUsername("customerUsername");
        customer.setPassword("customerPassword");
        customer.setEmail("customerEmail");
        customerService.add(customer);

        var expert=new Expert();
        expert.setUsername("expertUsername");
        expert.setPassword("expertPassword");
        expert.setEmail("expertEmail");
        expertService.add(expert);

        User filteredUser=new Admin();
        filteredUser.setFirstname("f");

        List<User> filters = service.filterUsers(filteredUser);

        assertAll(
                ()->assertEquals(1,filters.size()),
                ()->assertEquals(filters.get(0).getUsername(),user.getUsername()),
                ()->assertEquals(filters.get(0).getEmail(),user.getEmail()),
                ()->assertEquals(filters.get(0).getPassword(),user.getPassword())
        );


    }
}
