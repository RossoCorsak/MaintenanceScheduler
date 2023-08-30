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
import java.util.List;


@WebServlet("/UserGetOrderInfoServlet")
public class UserGetOrderInfoServlet extends HttpServlet {
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


        List<OrderInfo> orders = order_info_service.getAllOrderByUser(uid);

        if(orders!=null){
            System.out.println(getCurrentTime.getTime() + "用户查看所有订单 uid:" + uid);
            //登录成功
            boolean success = true;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("orders", orders);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));

        } else {
            System.out.println(getCurrentTime.getTime() + "用户查看订单失败 uid:" + uid);
            //取消订单失败
            boolean success = false;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", "check if the uid in token is correct");
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

