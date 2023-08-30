package service;

import dao.MaintainerDao;
import dao.OrderInfoDao;
import dao.OrderScheduleDao;
import domain.Maintainer;
import domain.OrderInfo;
import domain.OrderSchedule;
import mapUtil.amapUtil;
import timeUtil.getCurrentTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class OrderScheduleService {
    private static Long distance_threshold = 10000000L;
    OrderInfoDao order_info_dao = new OrderInfoDao();
    OrderScheduleDao order_schedule_dao = new OrderScheduleDao();
    MaintainerDao maintainer_dao = new MaintainerDao();
    public void OrderSchedule(){
        //获取所有待分配订单中最近访问时间最早的那一个
        OrderSchedule latest_order = order_schedule_dao.findOrderScheduleLatestAccessWaiting();
        if(latest_order==null){
            System.out.println(getCurrentTime.getTime() + " 当前无待分配订单，退出");
            //当前无待分配订单，退出
            return;
        }
        //获取所有空闲中的维修人员
        List<Maintainer> maintainer_list = maintainer_dao.findMaintainerByStatus("空闲中");
        if(maintainer_list.isEmpty()){
            System.out.println(getCurrentTime.getTime() + " 当前无空闲中维修人员，退出 " + latest_order);
            //修改order_schedule中订单latest_access_time为当前时间
            order_schedule_dao.alterOrderScheduleLatestAccessTimeByOid(latest_order.getOid(), new Date(System.currentTimeMillis()));
            //当前无空闲中维修人员，退出
            return;
        }
        //获取上次分配的mid（记录在OrderInfo中）
        Integer last_mid = latest_order.getMid();
        //获取所有空闲维修人员到订单位置的距离
        List<Long> distance_list = this.getDistanceBetweenMaintainersAndOrder(maintainer_list,latest_order);
        //获取最小距离的索引，排除上次的mid
        int min_distance_index = this.getMinimumDistanceIndexExceptLastMid(distance_list,maintainer_list,last_mid);
        //判断最小距离是否超出最远距离限制
        if(min_distance_index==-1){
            System.out.println(getCurrentTime.getTime() + " 当前订单与最近空闲中维修人员距离太远 " + latest_order);
            //修改订单latest_access_time为当前时间
            order_schedule_dao.alterOrderScheduleLatestAccessTimeByOid(latest_order.getOid(), new Date(System.currentTimeMillis()));
            //当前最近空闲中维修人员距离太远，退出
            return;
        }else if(min_distance_index==-2){
            //地图api无法获取距离，可能是经纬度出错
            System.out.println(getCurrentTime.getTime() + " 无距离计算，请检查数据库是否为空，或经纬度是否位于中国大陆陆地上 " + latest_order);
            //修改订单latest_access_time为当前时间
            order_schedule_dao.alterOrderScheduleLatestAccessTimeByOid(latest_order.getOid(), new Date(System.currentTimeMillis()));
            return;
        }
        Integer mid = maintainer_list.get(min_distance_index).getMid();
        Long oid = latest_order.getOid();
        //分配订单给索引对应的维修工，修改维修工状态
        maintainer_dao.alterMaintainerStatusByMid("待确认",mid);
        //修改订单状态
        //修改order_info订单信息表的状态
        order_info_dao.alterOrderInfoStatusByOid("待确认",oid);
        //在order_info中填入维修工信息
        order_info_dao.alterOrderInfoMaintainerByOidAndMid(oid, mid);
        //在order_schedule中修改状态并填入维修工mid
        order_schedule_dao.alterOrderScheduleMidAndStatusByOid(oid,mid,"待确认");

        System.out.println(getCurrentTime.getTime() + " 分配成功");
    }

    //计算所有空闲状态的维修人员的位置到订单的位置的距离，排除上次分配的mid
    public List<Long> getDistanceBetweenMaintainersAndOrder(List<Maintainer> maintainer_list, OrderSchedule order){
        List<String> org_list = new ArrayList<String>();
        for(Maintainer maintainer:maintainer_list){
            String origin = maintainer.getCoordinate_x().toString() + "," + maintainer.getCoordinate_y().toString();
            org_list.add(origin);
        }
        String dest = order.getCoordinate_x().toString() + "," + order.getCoordinate_y().toString();
        //调用amaUtil里的函数获取距离，返回值为String，单位为米

        List<String> results = amapUtil.getDistanceArray(org_list, dest);
        if(results==null){
            return null;
        }
        //转换成Double
        List<Long> distance_list = new ArrayList<Long>();
        for(String result:results){
            Long dist = Long.parseLong(result);
            distance_list.add(dist);
        }
        return distance_list;
    }

    //计算比最远距离小的最小值的索引，如果没有就返回-1，如果距离list为空，则返回-2
    public int getMinimumDistanceIndexExceptLastMid(List<Long> distance_list, List<Maintainer> maintainer_list, Integer last_mid){
        if(distance_list==null){
            return -2;
        }
        Long min_dist = distance_threshold + 1L;
        int min_dist_index = -1;
        for(int i=0;i<distance_list.size();i++){
            if((distance_list.get(i)<min_dist)&&(!Objects.equals(maintainer_list.get(i).getMid(),last_mid))){
                min_dist=distance_list.get(i);
                min_dist_index=i;
            }
        }
        return min_dist_index;
    }

    /********管理员********/
    public List<OrderSchedule> getAllOrderSchedule(){
        List<OrderSchedule> list = order_schedule_dao.findAllOrderSchedule();
        return list;
    }
}
