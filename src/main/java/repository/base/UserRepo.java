package repository.base;


import entity.base.User;

import java.util.List;

public interface UserRepo<T extends User> {

    T findByUsername(String username,Class<T> tClass);

    List<User> showAllUsers();

//    List<User> filter(User user);

    public T addUser(T t);
}
