package service;


import dao.MaintainerDao;
import dao.OrderInfoDao;
import dao.OrderScheduleDao;
import domain.OrderInfo;
import domain.OrderSchedule;

import java.util.List;
import java.util.Objects;

public class OrderInfoService {
    private OrderInfoDao order_info_dao = new OrderInfoDao();
    private OrderScheduleDao order_schedule_dao = new OrderScheduleDao();
    private MaintainerDao maintainer_dao = new MaintainerDao();


    /********用户********/
    //用户创建订单，返回是否创建成功,返回0创建失败，返回1创建成功
    public int addOrderByUser(OrderInfo order){
        //往order_info表里插入，返回自增的订单oid
        Long oid = order_info_dao.addOrderInfo(order);
        //oid为0则插入失败
        if(oid==0){
            return 0;
        }
        order.setOid(oid);
        //往order_schedule表里插入
        int num = order_schedule_dao.addOrderSchedule(this.getOrderScheduleByOrderInfo(order));
        //如果num为0，则插入失败
        if(num==0){
            //删除刚刚在order_info表里的插入
            order_info_dao.deleteOrderInfoByOid(oid);
            return 0;
        }
        //插入成功
        return 1;

    }

    //输入order_info生成对应的order_schedule
    public OrderSchedule getOrderScheduleByOrderInfo(OrderInfo order){
        OrderSchedule order_schedule = new OrderSchedule();
        order_schedule.setOid(order.getOid());
        order_schedule.setTime(order.getTime());
        order_schedule.setLatest_access_time(order.getTime());
        order_schedule.setCoordinate_x(order.getCoordinate_x());
        order_schedule.setCoordinate_y(order.getCoordinate_y());
        order_schedule.setMid(order.getMid());
        order_schedule.setStatus(order.getStatus());
        return order_schedule;
    }

    //用户取消订单，返回1则取消成功，返回0则取消失败
    public int cancelOrderByUser(Long oid){
        //获取用户要取消的订单
        OrderInfo order = order_info_dao.findOrderInfoByOid(oid);
        if(order==null){
            return 0;
        }
        //判断订单状态
        if(Objects.equals(order.getStatus(), "待分配")){
            //如果订单状态是待分配，则取消
            order_schedule_dao.alterOrderScheduleStatusByOid("已取消",oid);
            order_info_dao.alterOrderInfoStatusByOid("已取消",oid);
            //取消成功
            return 1;
        } else if (Objects.equals(order.getStatus(), "待确认")) {
            //如果订单状态是待确认，则取消并修改维修工状态
            order_schedule_dao.alterOrderScheduleStatusByOid("已取消",oid);
            order_info_dao.alterOrderInfoStatusByOid("已取消",oid);
            maintainer_dao.alterMaintainerStatusByMid("空闲中",order.getMid());
            //取消成功
            return 1;
        }else{
            //当前订单已无法取消，取消失败
            return 0;
        }
    }

    //用户查看自己的所有订单
    public List<OrderInfo> getAllOrderByUser(Integer uid){
        List<OrderInfo> list = order_info_dao.findOrderInfoByUid(uid);
        return list;
    }


    /*******维修工*******/
    //维修工确认订单，确认成功返回1，失败返回0
    public int acceptOrderByMaintainer(Long oid, Integer mid){
        //获取订单
        OrderInfo order = order_info_dao.findOrderInfoByOid(oid);
        //判断订单状态（防止已经被取消）
        if(Objects.equals(order.getStatus(), "待确认")){
            //如果是待确认，则修改状态
            order_schedule_dao.alterOrderScheduleStatusByOid("工作中", oid);
            order_info_dao.alterOrderInfoStatusByOid("工作中", oid);
            maintainer_dao.alterMaintainerStatusByMid("工作中", mid);
            return 1;
        }else{
            //订单已被取消，接受失败
            maintainer_dao.alterMaintainerStatusByMid("空闲中", mid);
            //考虑加一个更新维修工坐标的步骤
            return 0;
        }
    }

    //维修工拒绝订单
    public int refuseOrderByMaintainer(Long oid, Integer mid){
        //获取订单
        OrderInfo order = order_info_dao.findOrderInfoByOid(oid);
        //判断订单状态
        if(Objects.equals(order.getStatus(), "待确认")){
            order_schedule_dao.alterOrderScheduleStatusByOid("待分配",oid);
            order_info_dao.alterOrderInfoStatusByOid("待分配", oid);
        }
        //可先更新定位
        //修改维修工状态
        maintainer_dao.alterMaintainerStatusByMid("空闲中", mid);
        return 1;
    }

    //维修工完成订单
    public int completeOrderByMaintainer(Long oid, Integer mid){
        //获取订单
        //OrderInfo order = order_info_dao.findOrderInfoByOid(oid);
        //修改订单状态
        order_schedule_dao.alterOrderScheduleStatusByOid("已完成", oid);
        order_info_dao.alterOrderInfoStatusByOid("已完成", oid);
        //修改维修工状态
        maintainer_dao.alterMaintainerStatusByMid("空闲中", mid);

        return 1;
    }

    //维修工查看自己的所有订单
    public List<OrderInfo> getAllOrderByMaintainer(Integer mid){
        List<OrderInfo> list = order_info_dao.findOrderInfoByMid(mid);
        return list;
    }

    //维修工查看自己当前的订单
    public OrderInfo getWorkingOrderByMaintainer(Integer mid){
        List<OrderInfo> list = order_info_dao.findOrderInfoByStatusAndMid("工作中", mid);
        if(list.isEmpty()){
            return null;
        }
        else{
            //只能有一个工作中订单
            return list.get(0);
        }
    }


    /********管理员********/
    //管理员查看所有订单
    public List<OrderInfo> getAllOrder(){
        List<OrderInfo> list = order_info_dao.findAllOrderInfo();
        return list;
    }

    //管理员删除订单
    public int deleteOrder(Long oid){
        OrderInfo order_info = order_info_dao.findOrderInfoByOid(oid);
        //查无此订单，则返回0
        if(order_info==null){
            return 0;
        }
        if(Objects.equals(order_info.getStatus(), "待确认") || Objects.equals(order_info.getStatus(),"工作中")){
            //需要修改维修工状态
            Integer mid = order_info.getMid();
            maintainer_dao.alterMaintainerStatusByMid("空闲中", mid);

        }
        //删除订单
        order_schedule_dao.deleteOrderScheduleByOid(oid);
        int num = order_info_dao.deleteOrderInfoByOid(oid);
        return 1;
    }
}
