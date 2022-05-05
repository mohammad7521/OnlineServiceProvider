package service.base;

import entity.base.User;
import exception.DuplicateUser;
import exception.FillRequirements;
import exception.NoSuchId;
import exception.WrongPasswordFormat;
import repository.base.BaseRepo;
import repository.base.UserRepo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;


public abstract class UserServiceImpl <T extends User,R extends UserRepo<T> & BaseRepo<T, Integer>>
        extends BaseServiceImpl<T, Integer, R> implements UserService<T>{

    public UserServiceImpl(R repo) {
        super(repo);
    }

    @Override
    public T findByUsername(String username,Class<T> tClass) {
        if (!checkUsername(username,tClass)){
            throw new NoSuchId();
        }
        return repo.findByUsername(username,tClass);
    }



    @Override
    public boolean checkUsername(String username,Class<T> tClass){
        boolean flag = false;
        List<T> list = repo.showAll(tClass);

        for (T t : list) {
            if (username.equals(t.getUsername())) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    @Override
    public boolean logIn(String username,String password,Class<T> tClass){
        boolean logInCheck = false;
        T t = findByUsername(username,tClass);

        if (t.getPassword().equals(password)) {
            logInCheck = true;
        }
        return logInCheck;
    }


    @Override
    public boolean passwordMinCheck(String password) {
        boolean flag=false;
        char[] chars = password.toCharArray();
        if (chars.length >= 8) {
            for (int i = 0; i < chars.length; i++) {
                if (Character.isDigit(chars[i]))
                   break;
            }
            for (int i = 0; i < chars.length; i++) {
                if (Character.isLetter(chars[i])) {
                    flag=true;
                }
            }
        }
        return flag;
    }

    @Override
    public T addUser (T t,Class<T> tClass){
        if (t.getEmail()==null || t.getPassword()==null || t.getUsername()==null){
            throw new FillRequirements();
        }

        if (!passwordMinCheck(t.getPassword())){
            throw new WrongPasswordFormat();
        }
        if (checkUsername(t.getUsername(),tClass)){
            throw new DuplicateUser();
        }

        Date date=new Date(System.currentTimeMillis());
        Time time=new Time(System.currentTimeMillis());
        t.setDate(date);
        t.setTime(time);
        return repo.addUser(t);
    }


    @Override
    public void changePassword(String newPassword,T t){
        if (passwordMinCheck(newPassword)){
            t.setPassword(newPassword);
            repo.update(t);
        }
    }


    @Override
    public List<User> showAllUsers(){
        return repo.showAllUsers();
    }


    @Override
    public List<User> filterUsers(User user){
        List<User> userList=showAllUsers();
        List<User> outPut;
        List<User> filterByFirstName;
        List<User> filterByLastName;
        List<User> filterByUsername;


        outPut=userList.stream().filter((type)->type.getDiscriminatorValue()==user.getDiscriminatorValue())
                .collect(Collectors.toList());

        if(user.getFirstname()!=null){
            filterByFirstName=outPut.stream().filter((f)->f.getFirstname().startsWith(user.getFirstname()))
                    .collect(Collectors.toList());
        }else filterByFirstName=outPut;

        if (user.getLastname()!=null){
            filterByLastName=filterByFirstName.stream().filter((l)->l.getLastname().startsWith(user.getLastname()))
                    .collect(Collectors.toList());
        }else filterByLastName=filterByFirstName;

        if (user.getEmail()!=null){
            filterByUsername=filterByLastName.stream().filter((u)->u.getUsername().startsWith(user.getUsername()))
                    .collect(Collectors.toList());;
        }else filterByUsername=filterByLastName;

        return filterByUsername;
    }

}
