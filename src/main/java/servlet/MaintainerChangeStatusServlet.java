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
import java.util.Objects;


@WebServlet("/MaintainerChangeStatusServlet")
public class MaintainerChangeStatusServlet extends HttpServlet {
    MaintainerService maintainer_service = new MaintainerService();
    OrderInfoService order_info_service = new OrderInfoService();
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
        String status = request.getParameter("status");
        //判断要修改成什么状态
        if(Objects.equals(status,"空闲中")){
            int num = maintainer_service.onDuty(mid);
            if(num==1){
                boolean success = true;
                JSONObject json = new JSONObject();
                json.put("success", success);
                //转换json字符串
                String jsonStr = json.toJSONString();
                //写入response，用字节流
                response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
                //
                System.out.println("维修工上班 mid：" + mid);
            }else{
                boolean success = false;
                String error = "Can not on duty";
                //放入JSON
                JSONObject json = new JSONObject();
                json.put("success", success);
                json.put("error", error);
                //转换json字符串
                String jsonStr = json.toJSONString();
                //写入response，用字节流
                response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
            }
        }else if(Objects.equals(status,"休息中")){
            int num = maintainer_service.offDuty(mid);
            if(num==1){
                boolean success = true;
                JSONObject json = new JSONObject();
                json.put("success", success);
                //转换json字符串
                String jsonStr = json.toJSONString();
                //写入response，用字节流
                response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
                //
                System.out.println("维修工下班 mid：" + mid);
            }else{
                boolean success = false;
                String error = "Can not off duty";
                //放入JSON
                JSONObject json = new JSONObject();
                json.put("success", success);
                json.put("error", error);
                //转换json字符串
                String jsonStr = json.toJSONString();
                //写入response，用字节流
                response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
            }
        }else{
            boolean success = false;
            String error = "Failed to change status";
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
