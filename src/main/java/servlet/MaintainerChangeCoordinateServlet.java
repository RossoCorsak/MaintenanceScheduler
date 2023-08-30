package servlet;

import com.alibaba.fastjson.JSONObject;
import service.MaintainerService;
import service.OrderInfoService;
import token.JwtHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/MaintainerChangeCoordinateServlet")
public class MaintainerChangeCoordinateServlet extends HttpServlet {
    MaintainerService maintainer_service = new MaintainerService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token==null){
            //token为空
            boolean success = false;
            String error = "token is null";//放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", error);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
            return;
        }
        Integer mid = Math.toIntExact(JwtHelper.getId(token));
        String name = JwtHelper.getName(token);

        //可以添加验证

        //请求参数获取
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        //修改定位
        int num = maintainer_service.updateCoordinate(longitude, latitude, mid);

        if(num!=0){
            boolean success = true;
            JSONObject json = new JSONObject();
            json.put("success", success);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
            //
            System.out.println("维修工mid：" + mid + "，成功修改定位");
        }else{
            boolean success = false;
            String error = "Failed to change location";
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", error);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request,response);
    }
}