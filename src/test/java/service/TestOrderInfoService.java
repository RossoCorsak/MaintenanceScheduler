package service;

import dao.OrderInfoDao;
import org.junit.Test;

public class TestOrderInfoService {
    OrderInfoService ois = new OrderInfoService();
    @Test
    public void testRefuseOrder(){
        ois.refuseOrderByMaintainer(10000000000L,100000000);
    }
}
