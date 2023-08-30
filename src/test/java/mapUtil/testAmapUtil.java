package mapUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testAmapUtil {
    amapUtil amap = new amapUtil();
    @Test
    public void testCalculateDistance(){
        //amap.calculateDistance(175.132215,21.653123,176.512315,22.333512);
        System.out.println(amapUtil.getDistance("116.481028,39.989643","114.465302,40.004717"));
    }
    @Test
    public void testGetDistanceArray(){
        List<String> org = new ArrayList<String>();
        org.add("116.481028,39.989643");
        org.add("114.481028,39.989643");
        org.add("115.481028,39.989643");
        List<String> list = amapUtil.getDistanceArray(org,"114.465302,40.004717");
        for(String dist:list){
            System.out.println(dist);
        }
    }
}
