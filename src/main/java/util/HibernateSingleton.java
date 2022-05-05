package util;

import entity.user.Address;
import entity.commericalOffer.CommercialOffer;
import entity.commercialOrder.CommercialOrder;
import entity.base.User;
import entity.commercialService.CommercialService;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.Expert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSingleton {

    private HibernateSingleton() {}

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure() // goes and fetches configuration from hibernate.cfg.xml
                    .build();



            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(CommercialService.class)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(CommercialOffer.class)
                    .addAnnotatedClass(CommercialOrder.class)
                    .addAnnotatedClass(Admin.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Expert.class)
                    .addAnnotatedClass(User.class)
                    .buildMetadata()
                    .buildSessionFactory();

        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Session getSession(){

        if (LazyHolder.INSTANCE.isClosed()){
            return LazyHolder.INSTANCE.openSession();
        }else return LazyHolder.INSTANCE.getCurrentSession();
    }
}
