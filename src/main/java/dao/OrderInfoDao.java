package dao;

import domain.OrderInfo;
import jdbcUtil.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoDao extends BaseDao{
    @Override
    public List<OrderInfo> getAllInfo(ResultSet rs) {
        List<OrderInfo> list = null;
        try{
            //判断rs是否为空
            if(null !=rs){
                //创建List
                list = new ArrayList<OrderInfo>();
                //遍历结果集
                while(rs.next()){
                    //创建goods对象
                    OrderInfo order_info = new OrderInfo();
                    //取出结果集中的值
                    order_info.setOid(rs.getLong("oid"));
                    order_info.setTime(rs.getTimestamp("time"));
                    order_info.setCoordinate_x(rs.getDouble("coordinate_x"));
                    order_info.setCoordinate_y(rs.getDouble("coordinate_y"));
                    order_info.setUid(rs.getInt("uid"));
                    order_info.setUname(rs.getString("uname"));
                    order_info.setUphone(rs.getString("uphone"));
                    order_info.setUaddress(rs.getString("uaddress"));
                    order_info.setContent(rs.getString("content"));
                    order_info.setMid(rs.getInt("mid"));
                    order_info.setMname(rs.getString("mname"));
                    order_info.setMphone(rs.getString("mphone"));
                    order_info.setStatus(rs.getString("status"));
                    //将该对象添加进集合
                    list.add(order_info);
                }
            }
            //返回list
            return list;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //执行insert，返回自增id
    public Long executeInsertSQL(String preparedSql, Object[] param) throws ClassNotFoundException{
        int num = 0;
        Long id = 0L;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        /* 处理SQL,执行SQL */
        try {
            conn = DBUtil.getConectionDb(); // 得到数据库连接
            pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]); // 为预编译sql设置参数
                }
            }
            num = pstmt.executeUpdate();// 执行SQL语句
            pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                id = rs.getLong("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 处理SQLException异常
        } finally {
            try {
                DBUtil.CloseDB(null, pstmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }


    //增
    //新增订单，返回自增订单的oid
    public Long addOrderInfo(OrderInfo order_info) {
        String preparedSql = "INSERT INTO order_info VALUES(NULL,?,?,?,?,?,?,?,?,NULL,NULL,NULL,?);";
        Object[] param = new Object[9];
        param[0] = order_info.getTime();
        param[1] = order_info.getCoordinate_x();
        param[2] = order_info.getCoordinate_y();
        param[3] = order_info.getUid();
        param[4] = order_info.getUname();
        param[5] = order_info.getUphone();
        param[6] = order_info.getUaddress();
        param[7] = order_info.getContent();
        param[8] = "待分配";
        Long oid = 0L;
        try {
            oid = executeInsertSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return oid;
    }


    //删
    //删除维修工，返回影响行数
    public int deleteOrderInfoByOid(Long oid) {
        String preparedSql = "DELETE FROM order_info WHERE oid = ?;";
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
    //通过oid查找订单
    public OrderInfo findOrderInfoByOid(Long oid) {
        String preparedSql = "SELECT * FROM order_info where oid = ?;";
        Object[] param = new Object[1];
        param[0] = oid;
        List<OrderInfo> list = null;
        OrderInfo order_info = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                order_info = list.get(0);
            }
            return order_info;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //通过uid找某个用户的所有订单
    public List<OrderInfo> findOrderInfoByUid(Integer uid) {
        String preparedSql = "SELECT * FROM order_info where uid = ?;";
        Object[] param = new Object[1];
        param[0] = uid;
        List<OrderInfo> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过mid找某个维修工的所有订单
    public List<OrderInfo> findOrderInfoByMid(Integer mid) {
        String preparedSql = "SELECT * FROM order_info where mid = ? AND status != ?;";
        Object[] param = new Object[2];
        param[0] = mid;
        param[1] = "待分配";
        List<OrderInfo> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过uid和status找某个用户某个状态的订单
    public List<OrderInfo> findOrderInfoByStatusAndUid(String status, Integer uid){
        String preparedSql = "SELECT * FROM order_info where status = ? AND uid = ?;";
        Object[] param = new Object[2];
        param[0] = status;
        param[1] = uid;
        List<OrderInfo> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过mid和status找某个维修工某个状态的订单
    public List<OrderInfo> findOrderInfoByStatusAndMid(String status, Integer mid){
        String preparedSql = "SELECT * FROM order_info where status = ? AND mid = ?;";
        Object[] param = new Object[2];
        param[0] = status;
        param[1] = mid;
        List<OrderInfo> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回所有订单
    public List<OrderInfo> findAllOrderInfo() {
        String preparedSql = "SELECT * FROM order_info;";
        Object[] param = null;
        List<OrderInfo> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //改
    //通过订单oid和维修工mid把维修工信息填入订单
    public int alterOrderInfoMaintainerByOidAndMid(Long oid, Integer mid) {
        String preparedSql = "UPDATE order_info, (SELECT mid, name, phone FROM maintainer WHERE mid = ?) as mtnr SET order_info.mid = mtnr.mid, order_info.mname = mtnr.name, order_info.mphone = mtnr.phone WHERE order_info.oid = ?;";
        Object[] param = new Object[2];
        param[0] = mid;
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
    public int alterOrderInfoStatusByOid(String status, Long oid) {
        String preparedSql = "UPDATE order_info set status = ? WHERE oid = ?;";
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

}
