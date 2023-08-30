package servlet;


import com.alibaba.fastjson.JSONObject;
import domain.OrderInfo;
import service.MaintainerService;
import service.OrderInfoService;
import token.JwtHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/MaintainerGetOrderInfoServlet")
public class MaintainerGetOrderInfoServlet extends HttpServlet {
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

        List<OrderInfo> orders = order_info_service.getAllOrderByMaintainer(mid);
        boolean success = true;
        //生成json
        JSONObject json = new JSONObject();
        json.put("success", success);
        json.put("orders", orders);
        //转换json字符串
        String jsonStr = json.toJSONString();
        //写入response，用字节流
        response.getOutputStream().write(jsonStr.getBytes("UTF-8"));

        System.out.println("维修工mid：" + mid + "，成功访问自己的所有订单");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request,response);
    }
}
