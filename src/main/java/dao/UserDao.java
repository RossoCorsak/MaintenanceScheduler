package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import domain.User;
import jdbcUtil.DBUtil;

public class UserDao extends BaseDao{
    @Override
    public List<User> getAllInfo(ResultSet rs) {
        List<User> list = null;
        try{
            //判断rs是否为空
            if(null !=rs){
                //创建List
                list = new ArrayList<User>();
                //遍历结果集
                while(rs.next()){
                    //创建goods对象
                    User user = new User();
                    //取出结果集中的值
                    user.setUid(rs.getInt("uid"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setGender(rs.getString("gender"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    //将该对象添加进集合
                    list.add(user);
                }
            }
            //返回list
            return list;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //返回所有用户
    public List<User> findAllUser() {
        String preparedSql = "SELECT * FROM user";
        Object[] param = null;
        List<User> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过uid查找用户
    public User findUserByUid(Integer uid) {
        String preparedSql = "SELECT * FROM user where uid = ?";
        Object[] param = new Object[1];
        param[0] = uid;
        List<User> list = null;
        User user = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                user = list.get(0);
            }
            return user;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过phone查找
    public User findUserByPhone(String phone) {
        String preparedSql = "SELECT * FROM user where phone = ?";
        Object[] param = new Object[1];
        param[0] = phone;
        List<User> list = null;
        User user = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                user = list.get(0);
            }
            return user;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过uid和password查找用户
    /*public Users findUsersByUidAndPassword(Users users) {
        String preparedSql = "SELECT * FROM users WHERE uid = ? AND password = ?";
        Object[] param = new Object[2];
        param[0] = users.getUid();
        param[1] = users.getPassword();
        List<Users> list = null;
        Users bean = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null) {
                if(!list.isEmpty()){
                    bean = list.get(0);
                }
            }
            return bean;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }*/


    //用户不需要知道自己的uid
    //执行insert，返回自增id
    /*public int executeInsertSQL(String preparedSql, Object[] param) throws ClassNotFoundException{
        int num = 0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

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
                id = rs.getInt("LAST_INSERT_ID()");
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
    }*/

    //新增用户
    public int addUser(User user) {
        String preparedSql = "INSERT INTO user VALUES(NULL,?,?,?,?,?);";
        Object[] param = new Object[5];
        param[0] = user.getPassword();
        param[1] = user.getName();
        param[2] = user.getGender();
        param[3] = user.getPhone();
        param[4] = user.getAddress();
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

    //删除用户，返回收影响行数
    public int deleteUserByUid(Integer uid) {
        String preparedSql = "DELETE FROM user WHERE uid = ?;";
        Object[] param = new Object[1];
        param[0] = uid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }
}
