package service;

import dao.ManagerDao;
import domain.Manager;


import java.util.Objects;

public class ManagerService {
    private ManagerDao manager_dao = new ManagerDao();

    //注册，失败0，成功1，已被注册2
    public int registerManager(Manager manager){
        //先查询这个电话号码有没有注册过
        Manager exist_manager = manager_dao.findManagerByPhone(manager.getPhone());
        if(exist_manager!=null){
            //此号码已被注册
            return 2;
        }
        int num = manager_dao.addManager(manager);
        if(num==1){
            //注册成功
            return 1;
        }else{
            //注册失败
            return 0;
        }
    }

    //登录
    public Manager loginManager(String phone, String password){
        Manager manager = manager_dao.findManagerByPhone(phone);
        if(manager==null){
            return null;
        }
        if(Objects.equals(manager.getPassword(),password)){
            return manager;
        }else{
            return null;
        }
    }
}
