package dao;

import domain.Manager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao extends BaseDao{
    @Override
    public List<Manager> getAllInfo(ResultSet rs) {
        List<Manager> list = null;
        try{
            //判断rs是否为空
            if(null !=rs){
                //创建List
                list = new ArrayList<Manager>();
                //遍历结果集
                while(rs.next()){
                    //创建goods对象
                    Manager manager = new Manager();
                    //取出结果集中的值
                    manager.setId(rs.getInt("id"));
                    manager.setPassword(rs.getString("password"));
                    manager.setName(rs.getString("name"));
                    manager.setPhone(rs.getString("phone"));
                    manager.setEmail(rs.getString("email"));
                    //将该对象添加进集合
                    list.add(manager);
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
    //新增管理员，返回影响行数
    public int addManager(Manager manager) {
        String preparedSql = "INSERT INTO manager VALUES(NULL,?,?,?,?);";
        Object[] param = new Object[4];
        param[0] = manager.getPassword();
        param[1] = manager.getName();
        param[2] = manager.getPhone();
        param[3] = manager.getEmail();
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //删
    //删除管理员，返回影响行数
    public int deleteManagerById(Integer id) {
        String preparedSql = "DELETE FROM manager WHERE id = ?;";
        Object[] param = new Object[1];
        param[0] = id;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //查
    //通过id查找管理员
    public Manager findManagerById(Integer id) {
        String preparedSql = "SELECT * FROM manager where id = ?";
        Object[] param = new Object[1];
        param[0] = id;
        List<Manager> list = null;
        Manager manager = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                manager = list.get(0);
            }
            return manager;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过phone查找管理员
    public Manager findManagerByPhone(String phone) {
        String preparedSql = "SELECT * FROM manager where phone = ?";
        Object[] param = new Object[1];
        param[0] = phone;
        List<Manager> list = null;
        Manager manager = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                manager = list.get(0);
            }
            return manager;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



    //改


}