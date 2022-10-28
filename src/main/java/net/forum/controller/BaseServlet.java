package net.forum.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-09 11:47
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

  /**
   * @Description: 子类的servlet被访问会调用service方法，若子类的service方法没有重写，就会调用父类BaseServlet的service方法 @Param:
   * [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/9
   */
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String method = req.getParameter("method");

    if (method != null) {
      try {
        // 获取当前访问类的字节码对象，以及字节码对象内的具体方法对象
        Method targetMethod =
            this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
        // 执行该方法 参数：this 原方法的参数
        targetMethod.invoke(this, req, resp);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
