package service;

import dao.MaintainerDao;
import dao.OrderInfoDao;
import dao.OrderScheduleDao;
import domain.Maintainer;
import domain.OrderInfo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MaintainerService {
    private MaintainerDao maintainer_dao = new MaintainerDao();
    private OrderInfoDao order_info_dao = new OrderInfoDao();
    private OrderScheduleDao order_schedule_dao = new OrderScheduleDao();

    /********维修工********/
    //注册，失败0，成功1，已被注册2
    public int registerMaintainer(Maintainer maintainer){
        //先查询这个电话号码有没有注册过
        Maintainer exist_maintainer = maintainer_dao.findMaintainerByPhone(maintainer.getPhone());
        if(exist_maintainer!=null){
            //此号码已被注册
            return 2;
        }
        int num = maintainer_dao.addMaintainer(maintainer);
        if(num==1){
            //注册成功
            return 1;
        }else{
            //注册失败
            return 0;
        }
    }

    //登录
    public Maintainer loginMaintainer(String phone, String password){
        Maintainer maintainer = maintainer_dao.findMaintainerByPhone(phone);
        if(maintainer==null){
            return null;
        }
        if(Objects.equals(maintainer.getPassword(),password)){
            return maintainer;
        }else{
            return null;
        }
    }

    //上班，失败0，成功1
    public int onDuty(Integer mid){
        Maintainer maintainer = maintainer_dao.findMaintainerByMid(mid);
        //状态不为休息中，则上班失败
        if(!Objects.equals(maintainer.getStatus(),"休息中")){
            return 0;
        }
        int num = maintainer_dao.alterMaintainerStatusByMid("空闲中", mid);
        return num;
    }

    //下班，失败0，成功1
    public int offDuty(Integer mid){
        Maintainer maintainer = maintainer_dao.findMaintainerByMid(mid);
        //判断状态
        if(!Objects.equals(maintainer.getStatus(),"空闲中")){
            //当前状态不为空闲中
            if(Objects.equals(maintainer.getStatus(),"待确认")){
                //当前状态为待确认，则拒绝订单并修改状态
                //获取当前分配待确认的订单
                Long oid = order_info_dao.findOrderInfoByStatusAndMid("待确认",mid).get(0).getOid();
                //修改订单状态
                order_schedule_dao.alterOrderScheduleStatusByOid("待分配",oid);
                order_info_dao.alterOrderInfoStatusByOid("待分配", oid);
                //修改维修工状态
                maintainer_dao.alterMaintainerStatusByMid("休息中",mid);
                //返回成功
                return 1;
            }else{
                //当前状态不能下班
                return 0;
            }
        }else{
            //修改维修工状态
            maintainer_dao.alterMaintainerStatusByMid("休息中",mid);
            return 1;
        }
    }

    //更新坐标
    public int updateCoordinate(Double c_x, Double c_y, Integer mid){
        int num = maintainer_dao.alterMaintainerCoordinateByMid(c_x, c_y, mid);
        return num;
    }

    //修改密码
    public int changePassword(String ex_password, String new_password, Integer mid){
        Maintainer maintainer = maintainer_dao.findMaintainerByMid(mid);
        if(maintainer==null){
            return 0;
        }

        if(!Objects.equals(maintainer.getPassword(),ex_password)){
            //原密码不一样，更改失败
            return 0;
        }else{
            int num = maintainer_dao.alterMaintainerPasswordByMid(new_password, mid);
            return num;
        }
    }


    /********管理员********/
    //查看所有维修工
    public List<Maintainer> getAllMaintainer(){
        List<Maintainer> list = maintainer_dao.findAllMaintainer();
        return list;
    }

    //删除维修工
    public int deleteMaintainer(Integer mid){
        int num = maintainer_dao.deleteMaintainerByMid(mid);
        return num;
    }
}
