package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.OrderSchedule;
import service.MaintainerService;
import service.OrderInfoService;
import service.OrderScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/ManagerDeleteOrderServlet")
public class ManagerDeleteOrderServlet extends HttpServlet {
    MaintainerService maintainer_service = new MaintainerService();
    OrderInfoService order_info_service = new OrderInfoService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String token = request.getHeader("Authorization");
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
        String name = JwtHelper.getName(token);*/

        //可以添加验证

        //获取order的id
        Long oid = Long.parseLong(request.getParameter("orderId"));
        //删除订单
        int num = order_info_service.deleteOrder(oid);

        String status = "";
        String message = "";

        if(num==1){
            //删除成功
            status = "success";
            message = "订单删除成功";
        }else{
            //删除失败
            status = "failed";
            message = "订单删除失败，请检查订单号";
        }

        //生成json
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("message", message);
        //转换json字符串
        String jsonStr = json.toJSONString();
        //写入response，用字节流
        //response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
        //response.addHeader("Access-Control-Allow-Origin","*");

        System.out.println("管理员删除订单 oid：" + oid);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }
}
