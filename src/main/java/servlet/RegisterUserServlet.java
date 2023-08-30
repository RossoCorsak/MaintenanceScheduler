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



@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService user_service = new UserService();

        // read form fields
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setName(name);
        user.setGender(gender);
        user.setAddress(address);



        int num = user_service.registerUser(user);

        if(num==1){
            //注册成功
            //自动登录
            user = user_service.loginUser(phone,password);
            System.out.println(getCurrentTime.getTime() + " 用户注册成功 phone:" + phone + " password:" + password);
            //注册成功
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

        } else if(num==2){
            System.out.println(getCurrentTime.getTime() + " 用户注册失败，号码已被注册 phone:" + phone);
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
            System.out.println(getCurrentTime.getTime() + " 用户注册失败");
            //登录失败
            boolean success = false;
            String error = "register failed,check user data";
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
