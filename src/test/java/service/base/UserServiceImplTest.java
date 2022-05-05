package service.base;

import entity.base.User;
import exception.DuplicateUser;
import exception.FillRequirements;
import exception.WrongPasswordFormat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


@Disabled
public abstract class UserServiceImplTest<T extends User,S extends UserService<T> & BaseService<T, Integer>>
    extends BaseServiceImplTest<T,Integer,S> {

    @Test
    void findByUsername(){
        service.addUser(entity1,tClass);
        
        var loadedEntity=service.findByUsername(entity1.getUsername(),tClass);

        assertNotNull(loadedEntity);

    }


    @Test
    void addUser(){

        try {
            service.addUser(entity1,tClass);
        }catch (DuplicateUser e){
            System.out.println("username already exists");
        }catch (WrongPasswordFormat e){
            System.out.println("password is in wrong format");
        }catch (FillRequirements e){
            System.out.println("username,password and email are mandatory");
        }

        var loadedEntity=service.showInfo(entity1.getId(),tClass);

        assertAll(
                ()->assertEquals(loadedEntity.getId(),entity1.getId()),
                ()->assertEquals(loadedEntity.getUsername(),entity1.getUsername()),
                ()->assertEquals(loadedEntity.getPassword(),entity1.getPassword()),
                ()->assertNotNull(loadedEntity.getTime()),
                ()->assertNotNull(loadedEntity.getDate())
        );
    }


    protected abstract void update();


    @Test
    void changePassword(){

        try {
            service.addUser(entity1, tClass);
            String newPassword = "newPassword1234";
            service.changePassword(newPassword, entity1);

        var loadedEntity=service.showInfo(entity1.getId(),tClass);

        assertEquals(loadedEntity.getPassword(),newPassword);
        }catch (WrongPasswordFormat e){
            System.out.println("password is in wrong format");
        }
    }
}
