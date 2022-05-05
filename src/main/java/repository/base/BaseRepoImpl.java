package repository.base;


import entity.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import util.HibernateSingleton;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;


@RequiredArgsConstructor
public abstract class BaseRepoImpl<T extends BaseEntity<ID>,ID extends Serializable> implements BaseRepo<T,ID> {


    protected SessionFactory sessionFactory= HibernateSingleton.getInstance();


    @Override
    public T add(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.save(t);
                transaction.commit();
                session.close();
                return t;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }


    @Override
    public T remove(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();

            try {

                session.remove(t);
                transaction.commit();
                session.close();
                return t;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }


    @Override
    public void update(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.update(t);
                transaction.commit();
                session.close();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    //based on id
    @Override
    public T showInfo(int ID, Class<T> tClass) {

        T t=null;
        try (var session = sessionFactory.openSession()) {
            var trx = session.beginTransaction();

            try {
                t = session.find(tClass,ID);
                trx.commit();
                session.close();

            } catch (Exception e) {
                trx.rollback();
            }
        }
        return t;
    }



    @Override
    public int hqlTruncate (String tableName) {
        int returnQueryInt;
        var session =sessionFactory.openSession();
        var transaction=session.beginTransaction();
        try {
            String hql = String.format("delete from %s",tableName);
            Query query = session.createQuery(hql);
            returnQueryInt=query.executeUpdate();
            transaction.commit();
            session.close();

        }catch (Exception e){
            transaction.rollback();
            throw e;
        }

        return returnQueryInt;
    }



    @Override
    public List<T> showAll(Class<T> tClass){
        var session=sessionFactory.openSession();

        List<T> list;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(tClass);
        criteria.from(tClass);
        list = session.createQuery(criteria).getResultList();
            session.close();
        return list;
    }




}
