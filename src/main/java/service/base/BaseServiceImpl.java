package service.base;

import entity.base.BaseEntity;
import exception.NoSuchUser;
import repository.base.BaseRepo;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;



public abstract class BaseServiceImpl <T extends BaseEntity<ID>, ID extends Serializable, R extends BaseRepo<T, ID>>
        implements BaseService<T, ID> {

    protected final R repo;

    protected BaseServiceImpl(R repo) {
        this.repo = repo;
    }


    @Override
    public T add(T t) {
        t.setTime(new Time(System.currentTimeMillis()));
        t.setDate(new Date(System.currentTimeMillis()));
       return repo.add(t);
    }



    @Override
    public T remove(T t) {
        return repo.remove(t);
    }



    @Override
    public void update(T t,Class<T> tClass) {
        if (!checkId(t.getId(),tClass)){
            throw new NoSuchUser();
        }
        repo.update(t);
    }



    @Override
    public T showInfo(int id, Class<T> tClass) {
        if (!checkId(id,tClass)){
            throw new NoSuchUser();
        }
        return repo.showInfo(id, tClass);
    }


    @Override
    public List<T> showAll(Class<T> tClass) {
        return repo.showAll(tClass);
    }

    @Override
    public boolean checkId(int Id, Class<T> tClass) {
        boolean flag = false;
        List<T> tlist = showAll(tClass);

        for (T t: tlist) {
            if (t.getId()==Id) {
                flag = true;
                break;
            }
        }
        return flag;
    }



    @Override
    public int hqlTruncate(String tableName) {
        return repo.hqlTruncate(tableName);
    }
}
