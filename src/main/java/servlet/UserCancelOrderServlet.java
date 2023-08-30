package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.OrderInfo;
import service.OrderInfoService;
import timeUtil.getCurrentTime;
import token.JwtHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;




@WebServlet("/UserCancelOrderServlet")
public class UserCancelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证token
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
        Integer uid = Math.toIntExact(JwtHelper.getId(token));
        String name = JwtHelper.getName(token);

        OrderInfoService order_info_service = new OrderInfoService();

        // read form fields
        Long oid = Long.parseLong(request.getParameter("oid"));

        int num = order_info_service.cancelOrderByUser(oid);

        if(num!=0){
            System.out.println(getCurrentTime.getTime() + "用户取消订单 oid:" + oid);
            //登录成功
            boolean success = true;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", null);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));

        } else {
            System.out.println(getCurrentTime.getTime() + "用户取消订单失败 oid:" + oid);
            //取消订单失败
            boolean success = false;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", "order can not be canceled in current status ");
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
