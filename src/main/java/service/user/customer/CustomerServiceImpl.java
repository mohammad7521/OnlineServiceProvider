package service.user.customer;

import entity.user.Customer;
import repository.user.customer.CustomerRepo;
import service.base.UserServiceImpl;

public class CustomerServiceImpl extends UserServiceImpl<Customer,CustomerRepo> implements CustomerService {

    public CustomerServiceImpl(CustomerRepo repo) {
        super(repo);
    }
}
