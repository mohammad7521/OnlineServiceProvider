package service.base;

import entity.base.User;

import java.util.List;

public interface UserService <T extends User>{

    public T findByUsername(String username,Class<T> tClass);

    public boolean checkUsername(String username,Class<T> tClass);

    //checking if the password matches the required length and complexity.
    public boolean passwordMinCheck(String password);

    public boolean logIn(String username,String password,Class<T> tClass);

    public void changePassword(String newPassword,T t);

    public T addUser(T t,Class<T> tClass);

    public List<User> showAllUsers();

    public List<User> filterUsers(User user);

}
