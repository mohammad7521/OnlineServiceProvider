package service.base;

import entity.base.BaseEntity;
import exception.NoSuchId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.Serializable;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@Disabled
public abstract class BaseServiceImplTest
        <T extends BaseEntity<ID>, ID extends Serializable,S extends BaseService<T,ID>> {

    protected S service;
    protected T entity1;
    protected T entity2;
    protected T entity3;
    protected Class<T> tClass;

    @Test
    protected void add() {
        service.add(entity1);
        var id=entity1.getId();
        var loaded=service.showInfo(id,tClass);

        assertNotNull(loaded);
        assertEquals(id,loaded.getId());
    }

    @Test
    void remove() {


        service.add(entity1);
        var id=entity1.getId();

        try {
            service.remove(entity1);

            var loaded = service.showAll(tClass);

            assertEquals(loaded.size(), 0);
        }catch (NoSuchId e){
            System.out.println("this entity does not exist");
        }
    }

    protected abstract void update();

    @Test
    void showInfo() {
        service.add(entity1);
        var id=entity1.getId();
        var loaded=service.showInfo(id,tClass);

        assertAll(
                ()->assertEquals(loaded.getId(),entity1.getId()),
                ()->assertNotNull(loaded)
        );
    }

    @Test
    void showAll() {
        service.add(entity1);

        List<T> list=service.showAll(tClass);

        assertNotNull(list);
        assertEquals(list.size(),1);
    }

}