package repository.user.admin;

import entity.user.Admin;
import repository.base.BaseRepo;
import repository.base.UserRepo;

public interface AdminRepo extends BaseRepo<Admin,Integer>, UserRepo<Admin> {


}
