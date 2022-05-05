package service.base;

import entity.base.BaseEntity;
import java.io.Serializable;
import java.util.List;


public interface BaseService <T extends BaseEntity <ID>,ID extends Serializable> {

    public T add(T t);

    public T remove(T t);

    public void update (T t,Class<T> tClass);

    public T showInfo(int id,Class<T> tClass);

    public List<T> showAll(Class<T> tClass);

    public boolean checkId(int id,Class<T> tclass);

    public int hqlTruncate(String tableName);

}
