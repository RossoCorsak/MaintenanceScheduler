package dao;

import org.junit.Test;

import java.util.Date;

public class TestOrderScheduleDao {
    OrderScheduleDao osd = new OrderScheduleDao();
    @Test
    public void testAlterOrderScheduleMidAndStatusByOid(){
        int num = osd.alterOrderScheduleMidAndStatusByOid(10000000000L,100000000,"待确认");
        System.out.println(num);
    }
    @Test
    public void testAlterOrderScheduleLatestAccessTimeByOid(){
        int num = osd.alterOrderScheduleLatestAccessTimeByOid(10000000000L, new Date(System.currentTimeMillis()));
        System.out.println(num);
    }

}
