package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.Maintainer;
import service.MaintainerService;
import token.JwtHelper;
import timeUtil.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/LoginMaintainerServlet")
public class LoginMaintainerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MaintainerService maintainer_service = new MaintainerService();

        // read form fields
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        System.out.println("phone:" + phone + ", password:" + password);

        Maintainer maintainer = maintainer_service.loginMaintainer(phone, password);

        if(maintainer!=null){
            System.out.println(getCurrentTime.getTime() + " 维修工登录成功 phone:" + phone);
            //登录成功
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
            response.getOutputStream().write(jsonStr.getBytes());

        } else {
            System.out.println(getCurrentTime.getTime() + " 维修工登录失败");
            //登录失败
            boolean success = false;
            String error = "login failed";
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", error);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes());
        }


    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request,response);
    }
}
