package service;

import domain.User;
import org.junit.Test;

public class testUserService {
    UserService user_service = new UserService();
    @Test
    public void testRegisterUser(){
        User user = new User();
        user.setName("张飞");
        user.setPassword("123456abc");
        user.setGender("男");
        user.setPhone("13100098500");
        user.setAddress("北京市");
        int num = user_service.registerUser(user);
        System.out.println(num);
    }
    @Test
    public void testLoginUser(){
        User user = user_service.loginUser("13100098500","123456ab");
        if(user==null){
            System.out.println("失败");
        }else{
            System.out.println(user.getName());
        }
    }
}
