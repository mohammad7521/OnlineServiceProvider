package service.user.admin;

import entity.base.User;
import entity.user.Admin;
import repository.user.admin.AdminRepo;
import service.base.UserServiceImpl;

import java.util.List;


public class AdminServiceImpl extends UserServiceImpl<Admin,AdminRepo> implements AdminService {



    public AdminServiceImpl(AdminRepo repo) {
        super(repo);
    }

    public List filter(User user){
        return null;
    }

}
