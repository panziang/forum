package net.forum.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.forum.domain.Category;
import net.forum.service.impl.CategoryServiceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @program: forum
 * @description: Category类的处理
 * @author: LQY
 * @create: 2022-04-10 15:09
 */
@WebServlet(
    name = "CategoryServlet",
    urlPatterns = {"/category"})
public class CategoryServlet extends BaseServlet {
  private CategoryServiceimpl categoryServiceimpl = new CategoryServiceimpl();

  /**
   * @Description: 返回全部分类 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/10
   */
  public void getCategoryList(HttpServletRequest req, HttpServletResponse resp) {
    List<Category> categoryList = categoryServiceimpl.getCategoryList();
    //    for (Category category : categoryList) {
    //      System.out.println(category.toString());
    //    }
    //    System.out.println(categoryList.toString());

    /** ajax部分 */
    Gson gson = new Gson();

    try {
      resp.setHeader("Content-type", " application/json; charset=utf-8");
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(categoryList));
      //      System.out.println("category接口成功发送数据！");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
