package net.forum.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.forum.domain.User;
import net.forum.service.impl.UserServiceimpl;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-12 14:07
 */
@WebServlet(
    name = "userServlet",
    urlPatterns = {"/user"})
public class UserServlet extends BaseServlet {
  UserServiceimpl userServiceimpl = new UserServiceimpl();

  /**
   * @Description: 用户登录 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/12
   */
  public void login(HttpServletRequest req, HttpServletResponse resp) {
    Gson gson = new Gson();

    String phone = req.getParameter("phone");
    String pwd = req.getParameter("pwd");
    User user = userServiceimpl.login(phone, pwd);
    if (user != null) {
      //      req.getSession().setAttribute("user", user);
      resp.addCookie(new Cookie("userId", String.valueOf(user.getId())));
      resp.addCookie(new Cookie("loginFlag", String.valueOf(1)));
    } else {
      //      req.setAttribute("msg", "手机号或者密码错误！");
      //      resp.addCookie(new Cookie("msg", "手机号或者密码错误！"));
    }

    /** ajax模块 */
    resp.setHeader("Content-type", " application/json; charset=utf-8");

    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(user));
      //      System.out.println("user接口成功发送数据！");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @Description: 用户退出登录 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/12
   */
  public void logOut(HttpServletRequest req, HttpServletResponse resp) {
    // 清除session
    req.getSession().invalidate();

    // 清除cookie
    Cookie[] cookies = req.getCookies();
    for (Cookie cookie : cookies) {
      cookie.setMaxAge(0);
      resp.addCookie(cookie);
    }
    //    System.out.println("成功清除cookie");

    // 跳转首页或者登录页面
    // TODO: 2022/4/12 a
  }

  /**
   * @Description: 用户注册 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/12
   */
  public void register(HttpServletRequest req, HttpServletResponse resp) {
    String name = req.getParameter("name");
    String phone = req.getParameter("phone");
    String pwd = req.getParameter("pwd");
    String sex = req.getParameter("sex");
    User user = new User();
    user.setUserName(name);
    user.setPhone(phone);
    user.setPwd(pwd);
    user.setSex(sex.equals("男") ? 1 : 0);
    Map<String, String[]> map = req.getParameterMap();
    //    System.out.println(map);
    /** 把map的数据映射到user对象 */
    try {
      BeanUtils.populate(user, map);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    int flag = userServiceimpl.register(user);

    // 注册成功 返回值是1 跳转登录界面  ||  注册失败 返回值是0 跳转注册页面
    if (flag == 1) {
      // TODO: 2022/4/12
    } else {
      // TODO: 20224/12 a
    }

    Gson gson = new Gson();
    resp.setHeader("Content-type", " application/json; charset=utf-8");
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(flag));
      //      System.out.println("注册接口成功发送数据！");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getUserById(HttpServletRequest req, HttpServletResponse resp) {
    int userId = Integer.parseInt(req.getParameter("id"));
    User user = userServiceimpl.getUserById(userId);
    //    System.out.println(user);

    Gson gson = new Gson();
    resp.setHeader("Content-type", " application/json; charset=utf-8");

    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(user));
      //      System.out.println("user的getUserById成功发送数据！");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update(HttpServletRequest req, HttpServletResponse resp) {
    String userId = req.getParameter("id");
    String userName = req.getParameter("name");
    String phone = req.getParameter("phone");
    String pwd = req.getParameter("pwd");
    String sex = req.getParameter("sex");

    int flag = userServiceimpl.updateUser(userId, userName, phone, pwd, sex);

    //    System.out.println(flag);

    Gson gson = new Gson();
    resp.setHeader("Content-type", " application/json; charset=utf-8");
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(flag));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
