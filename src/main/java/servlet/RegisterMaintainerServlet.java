package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.Maintainer;
import service.MaintainerService;
import timeUtil.getCurrentTime;
import token.JwtHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/RegisterMaintainerServlet")
public class RegisterMaintainerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MaintainerService maintainer_service = new MaintainerService();

        // read form fields
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        Double coordinate_x = Double.parseDouble(request.getParameter("longitude"));
        Double coordinate_y = Double.parseDouble(request.getParameter("latitude"));

        Maintainer maintainer = new Maintainer();
        maintainer.setPhone(phone);
        maintainer.setPassword(password);
        maintainer.setName(name);
        maintainer.setGender(gender);
        maintainer.setCoordinate_x(coordinate_x);
        maintainer.setCoordinate_y(coordinate_y);
        maintainer.setStatus("空闲中");



        int num = maintainer_service.registerMaintainer(maintainer);

        if(num==1){
            //注册成功
            //自动登录
            maintainer = maintainer_service.loginMaintainer(phone,password);
            System.out.println(getCurrentTime.getTime() + " 维修工注册成功 phone:" + phone + " password:" + password);
            //注册成功
            boolean success = true;
            //创建token
            String token = JwtHelper.createToken(maintainer.getMid(),maintainer.getName());
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("engineerData", maintainer);
            json.put("token", token);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));

        } else if(num==2){
            System.out.println(getCurrentTime.getTime() + " 维修工注册失败，号码已被注册 phone:" + phone);
            //注册失败
            boolean success = false;
            String error = "phone has been registered";
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", error);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
        }else {
            System.out.println(getCurrentTime.getTime() + " 维修工注册失败");
            //登录失败
            boolean success = false;
            String error = "register failed,check engineer data";
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
