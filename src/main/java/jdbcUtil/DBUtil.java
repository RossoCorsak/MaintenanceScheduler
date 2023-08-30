package jdbcUtil;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DBUtil {
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String username="root";//数据库登录用户
    private static String password="123456";//密码
    private static String database="maintenance_scheduler";//数据库名字
    private static String url="jdbc:mysql://localhost:3306/"+database+"?useUnicode=true&characterEncoding=utf-8";

    static{
        try{
            Class.forName(driver);//加载驱动
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    //连接数据库的方法
    public static Connection getConectionDb(){
        Connection conn=null;
        try{
            conn=DriverManager.getConnection(url,username,password);//获得数据库连接
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    //关闭数据库的方法
    public static void CloseDB(ResultSet rs,PreparedStatement stm,Connection conn) throws SQLException {
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(stm!=null){
            try{
                stm.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }


}