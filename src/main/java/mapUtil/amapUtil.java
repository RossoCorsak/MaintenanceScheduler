package mapUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class amapUtil {
    private static String key = "a00c38027df3bcad71da4d244a65e55d";


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            return null;
        } finally { // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 距离测量API服务地址
     *
     * @param origins     出发点
     * @param destination 目的地
     * @return
     */
    public static String getDistanceUrl(String origins, String destination) {
        String urlString = "http://restapi.amap.com/v3/distance?origins=" + origins + "&destination=" + destination + "&key=" + key + "&type=1";
        return urlString;
    }
    /**
     * 获取二个地点之间的行车距离
     * @param origins 出发地
     * @param destination 目的地
     * @return
     */
    public static String getDistance(String origins, String destination) {
        String jsonString = sendGet(getDistanceUrl(origins,destination));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String distance = jsonObject.getJSONArray("results").getJSONObject(0).getString("distance");
        return distance;
    }

    /**
     * 距离测量API服务地址
     *
     * @param origins     出发点
     * @param destination 目的地
     * @return
     */
    public static String getDistanceArrayUrl(List<String> origins, String destination) {
        String urlString = "http://restapi.amap.com/v3/distance?origins=";
        for(int i=0;i<origins.size()-1;i++){
            urlString = urlString + origins.get(i) + "|";
        }
        urlString = urlString + origins.get(origins.size()-1);

        urlString = urlString + "&destination=" + destination + "&key=" + key + "&type=1";
        return urlString;

    }
    /**
     * 获取多个起点与终点的行车距离
     * @param origins 出发地
     * @param destination 目的地
     * @return
     */
    public static List<String> getDistanceArray(List<String> origins, String destination) {
        String jsonString = sendGet(getDistanceArrayUrl(origins,destination));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        if(jsonArray.isEmpty()){
            return null;
        }
        List<String> distArray = new ArrayList<String>();
        for(int i=0;i<origins.size();i++){
            String distance = jsonArray.getJSONObject(i).getString("distance");
            distArray.add(distance);
        }
        //String distance = jsonObject.getJSONArray("results").getJSONObject(0).getString("distance");
        return distArray;
    }
}
