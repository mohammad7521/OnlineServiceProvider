package repository.base;



import entity.base.BaseEntity;
import java.io.Serializable;
import java.util.List;



public interface BaseRepo<T extends BaseEntity<ID>,ID extends Serializable> {



    public T add(T t);


    public T remove(T t);


    public void update(T t);

    //based on id
    public T showInfo(int ID, Class<T> tClass);



    public int hqlTruncate (String tableName);



    public List<T> showAll (Class<T> tClass);

}
