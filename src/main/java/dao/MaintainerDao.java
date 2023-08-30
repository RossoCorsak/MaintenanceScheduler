package dao;

import domain.Maintainer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaintainerDao extends BaseDao{
    @Override
    public List<Maintainer> getAllInfo(ResultSet rs) {
        List<Maintainer> list = null;
        try{
            //判断rs是否为空
            if(null !=rs){
                //创建List
                list = new ArrayList<Maintainer>();
                //遍历结果集
                while(rs.next()){
                    //创建goods对象
                    Maintainer maintainer = new Maintainer();
                    //取出结果集中的值
                    maintainer.setMid(rs.getInt("mid"));
                    maintainer.setPassword(rs.getString("password"));
                    maintainer.setName(rs.getString("name"));
                    maintainer.setGender(rs.getString("gender"));
                    maintainer.setPhone(rs.getString("phone"));
                    maintainer.setCoordinate_x(rs.getDouble("coordinate_x"));
                    maintainer.setCoordinate_y(rs.getDouble("coordinate_y"));
                    maintainer.setStatus(rs.getString("status"));
                    //将该对象添加进集合
                    list.add(maintainer);
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
    //新增维修工，返回影响行数
    public int addMaintainer(Maintainer maintainer) {
        String preparedSql = "INSERT INTO maintainer VALUES(NULL,?,?,?,?,?,?,?);";
        Object[] param = new Object[7];
        param[0] = maintainer.getPassword();
        param[1] = maintainer.getName();
        param[2] = maintainer.getGender();
        param[3] = maintainer.getPhone();
        param[4] = maintainer.getCoordinate_x();
        param[5] = maintainer.getCoordinate_y();
        param[6] = maintainer.getStatus();
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //删
    //删除维修工，返回影响行数
    public int deleteMaintainerByMid(Integer mid) {
        String preparedSql = "DELETE FROM maintainer WHERE mid = ?;";
        Object[] param = new Object[1];
        param[0] = mid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }


    //查
    //通过uid查找维修工
    public Maintainer findMaintainerByMid(Integer mid) {
        String preparedSql = "SELECT * FROM maintainer where mid = ?";
        Object[] param = new Object[1];
        param[0] = mid;
        List<Maintainer> list = null;
        Maintainer maintainer = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                maintainer = list.get(0);
            }
            return maintainer;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过phone查找维修工
    public Maintainer findMaintainerByPhone(String phone) {
        String preparedSql = "SELECT * FROM maintainer where phone = ?";
        Object[] param = new Object[1];
        param[0] = phone;
        List<Maintainer> list = null;
        Maintainer maintainer = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            if(list != null && !list.isEmpty()) {
                maintainer = list.get(0);
            }
            return maintainer;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //通过status找维修工
    public List<Maintainer> findMaintainerByStatus(String status) {
        String preparedSql = "SELECT * FROM maintainer where status = ?";
        Object[] param = new Object[1];
        param[0] = status;
        List<Maintainer> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回所有维修工
    public List<Maintainer> findAllMaintainer() {
        String preparedSql = "SELECT * FROM maintainer";
        Object[] param = null;
        List<Maintainer> list = null;
        try {
            list = executeQuerySQL(preparedSql, param);
            return list;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //改
    //通过mid修改维修工状态
    public int alterMaintainerStatusByMid(String status, Integer mid) {
        String preparedSql = "UPDATE maintainer set status = ? WHERE mid = ?;";
        Object[] param = new Object[2];
        param[0] = status;
        param[1] = mid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

    //通过mid修改维修工坐标
    public int alterMaintainerCoordinateByMid(Double coordinate_x, Double coordinate_y, Integer mid){
        String preparedSql = "UPDATE maintainer set coordinate_x = ?, coordinate_y = ? WHERE mid = ?;";
        Object[] param = new Object[3];
        param[0] = coordinate_x;
        param[1] = coordinate_y;
        param[2] = mid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

    //通过mid修改维修工密码
    public int alterMaintainerPasswordByMid(String password, Integer mid){
        String preparedSql = "UPDATE maintainer set password = ? WHERE mid = ?;";
        Object[] param = new Object[2];
        param[0] = password;
        param[1] = mid;
        int num = 0;
        try {
            num = executeUpdateSQL(preparedSql, param);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return num;
    }

}
