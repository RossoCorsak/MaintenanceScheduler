package service;

import dao.UserDao;
import domain.User;

import java.util.List;
import java.util.Objects;

public class UserService {
    private UserDao user_dao = new UserDao();

    /********用户********/
    //注册，失败0，成功1，已被注册2
    public int registerUser(User user){
        //先查询这个电话号码有没有注册过
        User exist_user = user_dao.findUserByPhone(user.getPhone());
        if(exist_user!=null){
            //此号码已被注册
            return 2;
        }
        int num = user_dao.addUser(user);
        if(num==1){
            //注册成功
            return 1;
        }else{
            //注册失败
            return 0;
        }
    }

    //登录
    public User loginUser(String phone, String password){
        User user = user_dao.findUserByPhone(phone);
        if(user==null){
            return null;
        }
        if(Objects.equals(user.getPassword(),password)){
            return user;
        }else{
            return null;
        }
    }

    /********管理员********/
    public List<User> getAllUser(){
        List<User> list = user_dao.findAllUser();
        return list;
    }
}
