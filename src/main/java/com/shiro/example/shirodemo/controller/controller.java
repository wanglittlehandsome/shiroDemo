package com.shiro.example.shirodemo.controller;


import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.stereotype.Controller
@RequestMapping("/home")
public class Controller {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/detail")
    public String detail(){
        return "index";
    }

    @RequestMapping(value="/index",method = RequestMethod.POST)
    @ResponseBody
    public String index(HttpServletRequest request){
        String userNo = request.getParameter("name");
        String password =request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(userNo, password, "login");
        Subject currentUser = SecurityUtils.getSubject();
        try {
            System.out.print("对用户[" + userNo + "]进行登录验证..验证开始");
            currentUser.login(token);
            if (currentUser.isAuthenticated()) {
                System.out.print("用户[" + userNo + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                currentUser.getSession().setAttribute("userName", userNo);
                currentUser.getSession().setAttribute("userNo", userNo);
            }
            System.out.print("对用户[" + userNo + "]进行登录验证..验证通过");
            return "验证通过";
        } catch (UnknownAccountException uae) {
            System.out.print("对用户[" + userNo + "]进行登录验证..验证未通过,未知账户");
            //info.success = false;
            //info.err.mess = "未知账户";
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            System.out.print("对用户[" + userNo + "]进行登录验证..验证未通过,错误的凭证");
            return "密码不正确";
        } catch (LockedAccountException lae) {
            System.out.print("对用户[" + userNo + "]进行登录验证..验证未通过,账户已锁定");
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            System.out.print("对用户[" + userNo + "]进行登录验证..验证未通过,错误次数过多");
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            System.out.print("对用户[" + userNo + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            return "用户名或者密码错误";
        }
    }
}
