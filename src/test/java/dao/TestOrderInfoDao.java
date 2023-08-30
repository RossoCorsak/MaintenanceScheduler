package dao;

import domain.OrderInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestOrderInfoDao {
    OrderInfoDao oidao;
    @Before
    public void setUp(){
        oidao = new OrderInfoDao();
    }
    @Test
    public void testFindAllOrderInfo(){
        List<OrderInfo> list = oidao.findAllOrderInfo();
        Assert.assertNotNull("获取所有订单出错", list);
    }
    @Test
    public void testAddOrderInfo(){
        OrderInfo oi = new OrderInfo();
        Date time = new Date(System.currentTimeMillis());
        oi.setTime(time);
        oi.setCoordinate_x(175.633153);
        oi.setCoordinate_y(22.6555423);
        oi.setUid(100000000);
        oi.setUname("李");
        oi.setUphone("13100055885");
        oi.setUaddress("北京");
        oi.setContent("电视机坏了");
        Long oid = oidao.addOrderInfo(oi);
        Assert.assertNotEquals("订单插入出错，oid无法获取", Optional.ofNullable(oid), 0L);
    }
}
