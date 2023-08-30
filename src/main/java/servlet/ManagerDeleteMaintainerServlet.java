package servlet;

import com.alibaba.fastjson.JSONObject;
import service.MaintainerService;
import service.OrderInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ManagerDeleteMaintainerServlet")
public class ManagerDeleteMaintainerServlet extends HttpServlet {
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

        //获取维修工的mid
        Integer mid = Integer.parseInt(request.getParameter("engineerId"));
        //删除维修工
        int num = maintainer_service.deleteMaintainer(mid);

        String status;
        String message;

        if(num==1){
            //删除成功
            status = "success";
            message = "工程师删除成功";
        }else{
            //删除失败
            status = "failed";
            message = "删除失败，工程师id错误或当前工程师有分配订单未完成";
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

        System.out.println("管理员删除工程师 mid：" + mid);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request,response);
    }
}
