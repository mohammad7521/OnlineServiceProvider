package repository.user.customer;

import entity.user.Customer;
import repository.base.BaseRepo;
import repository.base.UserRepo;

public interface CustomerRepo extends BaseRepo<Customer,Integer>, UserRepo<Customer> {
}
