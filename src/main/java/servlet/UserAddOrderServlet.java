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


@WebServlet("/UserAddOrderServlet")
public class UserAddOrderServlet extends HttpServlet {
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
        BigDecimal x = new BigDecimal(Double.parseDouble(request.getParameter("coordinate_x")));
        Double coordinate_x = x.setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        BigDecimal y = new BigDecimal(Double.parseDouble(request.getParameter("coordinate_y")));
        Double coordinate_y = y.setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        String uname = request.getParameter("uname");
        String uphone = request.getParameter("uphone");
        String uaddress = request.getParameter("uaddress");
        String content = request.getParameter("content");



        //
        OrderInfo order_info = new OrderInfo();
        order_info.setTime(new Date(System.currentTimeMillis()));
        order_info.setUid(uid);
        order_info.setCoordinate_x(coordinate_x);
        order_info.setCoordinate_y(coordinate_y);
        order_info.setUname(uname);
        order_info.setUphone(uphone);
        order_info.setUaddress(uaddress);
        order_info.setContent(content);
        order_info.setStatus("待分配");


        int num = order_info_service.addOrderByUser(order_info);

        if(num!=0){
            System.out.println(getCurrentTime.getTime() + " 用户新建订单 " + order_info);
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
            System.out.println(getCurrentTime.getTime() + " 用户新建订单失败");
            //登录失败
            boolean success = false;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", "check if parameter is null");
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

