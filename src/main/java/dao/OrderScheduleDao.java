package dao;


import domain.OrderSchedule;
import jdbcUtil.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderScheduleDao extends BaseDao{
    @Override
    public List<OrderSchedule> getAllInfo(ResultSet rs) {
        List<OrderSchedule> list = null;
        try{
            //判断rs是否为空
            if(null !=rs){
                //创建List
                list = new ArrayList<OrderSchedule>();
                //遍历结果集
                while(rs.next()){
                    //创建goods对象
                    OrderSchedule order_schedule = new OrderSchedule();
                    //取出结果集中的值
                    order_schedule.setOid(rs.getLong("oid"));
                    order_schedule.setTime(rs.getTimestamp("time"));
                    order_schedule.setLatest_access_time(rs.getTimestamp("latest_access_time"));
                    order_schedule.setCoordinate_x(rs.getDouble("coordinate_x"));
                    order_schedule.setCoordinate_y(rs.getDouble("coordinate_y"));
                    order_schedule.setMid(rs.getInt("mid"));
                    order_schedule.setStatus(rs.getString("status"));

                    //将该对象添加进集合
                    list.add(order_schedule);
                }
            }
            //返回list
            return list;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    //增
    //新增订单调度，返回影响行数
    public int addOrderSchedule(OrderSchedule order_schedule) {
        String preparedSql = "INSERT INTO order_schedule VALUES(?,?,?,?,?,?,?);";
        Object[] param = new Object[7];
        param[0] = order_schedule.getOid();
        param[1] = order_schedule.getTime();
        param[2] = order_schedule.getLatest_access_time();
        param[3] = order_schedule.getCoordinate_x();
        param[4] = order_schedule.getCoordinate_y();
        param[5] = order_schedule.getMid();
        param[6] = order_schedule.getStatus();
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //删
    //删除订单调度，返回影响行数
    public int deleteOrderScheduleByOid(Long oid) {
        String preparedSql = "DELETE FROM order_schedule WHERE oid = ?;";
        Object[] param = new Object[1];
        param[0] = oid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //查
    //查找访问时间最早的待分配订单
    public OrderSchedule findOrderScheduleLatestAccessWaiting() {
        String preparedSql = "SELECT * FROM order_schedule where status = ? ORDER BY latest_access_time ASC LIMIT 1;";
        Object[] param = new Object[1];
        param[0] = "待分配";
        List<OrderSchedule> list = null;
        OrderSchedule order_schedule = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                order_schedule = list.get(0);
            }
            return order_schedule;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //通过uid找某个用户的所有订单
    public List<OrderSchedule> findOrderScheduleByUid(String uid) {
        String preparedSql = "SELECT * FROM order_schedule where uid = ?;";
        Object[] param = new Object[1];
        param[0] = uid;
        List<OrderSchedule> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过mid找某个维修工的所有订单
    public List<OrderSchedule> findOrderScheduleByMid(String mid) {
        String preparedSql = "SELECT * FROM order_schedule where mid = ?;";
        Object[] param = new Object[1];
        param[0] = mid;
        List<OrderSchedule> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过oid查找订单
    public OrderSchedule findOrderScheduleByOid(Long oid) {
        String preparedSql = "SELECT * FROM order_schedule where oid = ?;";
        Object[] param = new Object[1];
        param[0] = oid;
        List<OrderSchedule> list = null;
        OrderSchedule order_schedule = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                order_schedule = list.get(0);
            }
            return order_schedule;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回所有订单
    public List<OrderSchedule> findAllOrderSchedule() {
        String preparedSql = "SELECT * FROM order_schedule;";
        Object[] param = null;
        List<OrderSchedule> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //改
    //通过订单oid修改最近访问时间
    public int alterOrderScheduleLatestAccessTimeByOid(Long oid, Date latest_access_time) {
        String preparedSql = "UPDATE order_schedule SET latest_access_time = ? WHERE oid = ?;";
        Object[] param = new Object[2];
        param[0] = latest_access_time;
        param[1] = oid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

    //通过oid修改订单状态
    public int alterOrderScheduleStatusByOid(String status, Long oid) {
        String preparedSql = "UPDATE order_schedule set status = ? WHERE oid = ?;";
        Object[] param = new Object[2];
        param[0] = status;
        param[1] = oid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

    //通过订单oid修改维修工mid和状态status
    public int alterOrderScheduleMidAndStatusByOid(Long oid, Integer mid, String status) {
        String preparedSql = "UPDATE order_schedule SET mid = ?, status = ? WHERE oid = ?;";
        Object[] param = new Object[3];
        param[0] = mid;
        param[1] = status;
        param[2] = oid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

}
