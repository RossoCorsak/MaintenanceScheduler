package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.Maintainer;
import domain.OrderSchedule;
import service.MaintainerService;
import service.OrderScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;




@WebServlet("/ManagerGetEngineersServlet")
public class ManagerGetEngineersServlet extends HttpServlet {
    MaintainerService maintainer_service = new MaintainerService();
    OrderScheduleService order_schedule_service = new OrderScheduleService();
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

        List<Maintainer> engineers = maintainer_service.getAllMaintainer();
        //生成json
        JSONObject json = new JSONObject();
        json.put("engineers", engineers);
        //转换json字符串
        String jsonStr = json.toJSONString();
        //写入response，用字节流
        //response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
        //response.addHeader("Access-Control-Allow-Origin","*");

        System.out.println("管理员访问所有维修工信息");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request,response);
    }
}
