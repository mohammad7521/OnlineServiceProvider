package repository.base;

import entity.base.User;

import javax.persistence.Query;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class UserRepoImpl <T extends User> extends BaseRepoImpl<T, Integer> implements UserRepo<T>{


    @Override
    public T findByUsername(String username,Class<T> tClass) {
        List<T> result=new ArrayList<>();
        try(var session=sessionFactory.openSession()) {
            var trx=session.beginTransaction();

            try {
                var cb = session.getCriteriaBuilder();
                var cq = cb.createQuery(tClass);
                Root<T> root = cq.from(tClass);
                cq.select(root).where(cb.equal(root.get("username"), username));
                Query query = session.createQuery(cq);
                result = query.getResultList();
                trx.commit();
                session.close();

            }catch (Exception e){
                trx.rollback();
            }

        }
        return result.get(0);
    }




    @Override
    public List<User> showAllUsers() {
        var session=sessionFactory.openSession();

        List<User> list=new ArrayList<>();
        String query="select * from users";
        var hql=session.createNativeQuery(query,User.class);
        list=hql.getResultList();
        session.close();
        return list;

    }




//    @Override
//    public List<User> filter(User user){
//        var session=sessionFactory.openSession();
//        String query="select * from users where " +
//                "(firstname is null or firstname like ?)and" +
//                "(lastname is null or lastname like ?) and" +
//                "(username is null or username like ?) and" +
//                "(password is null or password like ?) and" +
//                "(email is null or email like ?)";
//
//        var hql=session.createNativeQuery(query,User.class);
//
//        hql.setParameter(1,user.getFirstname());
//        hql.setParameter(2,user.getLastname());
//        hql.setParameter(3,user.getUsername());
//        hql.setParameter(4,user.getPassword());
//        hql.setParameter(5,user.getEmail());
//
//        List<User> userList=hql.getResultList();
//        return userList;
//    }


    @Override
    public T addUser(T t){
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
}
