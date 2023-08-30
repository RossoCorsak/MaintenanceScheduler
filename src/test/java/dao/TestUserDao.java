package dao;

import domain.User;
import org.junit.Test;

import java.util.List;

public class TestUserDao {
    @Test
    public void testAddUser(){
        UserDao userdao = new UserDao();
        User user = new User("88888888", "刘备", "男", "13155512345", "广东省广州市天河区华南理工大学");
        System.out.println(user);
        int num = userdao.addUser(user);
        if(num>0){
            System.out.println("插入成功");
        }
        else{
            System.out.println("插入失败");
        }
    }

    @Test
    public void testFindUserByPhone(){
        UserDao userdao = new UserDao();
        User user = userdao.findUserByPhone("13155512345");
        System.out.println(user);
    }

    @Test
    public void testFindAllUser(){
        UserDao userdao = new UserDao();
        List<User> list = userdao.findAllUser();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }


}
