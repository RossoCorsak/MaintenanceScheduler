package dao;

import java.sql.*;
import java.util.List;

import jdbcUtil.DBUtil;

public abstract class BaseDao {

    public abstract List getAllInfo(ResultSet rs);

    //执行查询sql语句
    public List executeQuerySQL(String preparedSql, Object[] param) throws ClassNotFoundException {
        List list = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        /* 处理SQL,执行SQL */
        try {
            conn = DBUtil.getConectionDb(); // 得到数据库连接
            pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]); // 为预编译sql设置参数
                }
            }
            ResultSet rs = pstmt.executeQuery();// 执行SQL语句
            list = getAllInfo(rs);
        } catch (SQLException e) {
            e.printStackTrace(); // 处理SQLException异常
        } finally {
            try {
                DBUtil.CloseDB(null, pstmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //执行增、删、改sql
    public int executeUpdateSQL(String preparedSql, Object[] param) throws ClassNotFoundException{
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
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
        } catch (SQLException e) {
            e.printStackTrace(); // 处理SQLException异常
        } finally {
            try {
                DBUtil.CloseDB(null, pstmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return num;
    }



}
