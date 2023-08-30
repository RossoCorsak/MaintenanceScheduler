package servlet;

import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import timeUtil.getCurrentTime;
import token.JwtHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/LoginUserServlet")
public class LoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService user_service = new UserService();

        // read form fields
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        System.out.println("phone:" + phone + ", password:" + password);

        User user = user_service.loginUser(phone, password);

        if(user!=null){
            System.out.println(getCurrentTime.getTime() + " 用户登录成功 phone:" + phone);
            //登录成功
            boolean success = true;
            //创建token
            String token = JwtHelper.createToken(user.getUid(),user.getName());
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("userData", user);
            json.put("token", token);
            //转换json字符串
            String jsonStr = json.toJSONString();
            //写入response，用字节流
            response.getOutputStream().write(jsonStr.getBytes("UTF-8"));

        } else {
            System.out.println(getCurrentTime.getTime() + "用户登录失败");
            //登录失败
            boolean success = false;
            //放入JSON
            JSONObject json = new JSONObject();
            json.put("success", success);
            json.put("error", null);
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
