package net.forum.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.forum.domain.Reply;
import net.forum.domain.Topic;
import net.forum.domain.User;
import net.forum.dto.PageDTO;
import net.forum.service.impl.TopicServiceimpl;
import net.forum.service.impl.UserServiceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-10 21:51
 */
@WebServlet(
    name = "TopicServlet",
    urlPatterns = {"/topic"})
public class TopicServlet extends BaseServlet {

  private TopicServiceimpl topicServiceimpl = new TopicServiceimpl();
  private UserServiceimpl userServiceimpl = new UserServiceimpl();

  /** 默认分页的size */
  private static final int pageSize = 5;

  /**
   * @Description: 得到当前页数的主题 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/19
   */
  public void getAllTopic(HttpServletRequest req, HttpServletResponse resp) {
    int page = 1;
    String currentPage = req.getParameter("page");
    if (currentPage != "" && currentPage != null) {
      page = Integer.valueOf(currentPage);
    }
    PageDTO<Topic> pageDTO = topicServiceimpl.getAllPageDto(page, pageSize);

    //    System.out.println(String.valueOf(pageDTO.getTotalRecord()) + pageDTO.getList());
    resp.setContentType("application/json; charset=utf-8");
    Gson gson = new Gson();
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(pageDTO));
    } catch (IOException e) {
      e.printStackTrace();
    }
    //    System.out.println("getAllTopic成功发送数据！");
  }

  /**
   * @Description: 搜索跳转后返回相关信息的PageDto @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/24
   */
  public void getSearchPageDto(HttpServletRequest req, HttpServletResponse resp) {
    String searchKey = req.getParameter("searchKey");
    int page = 1;
    String currentPage = req.getParameter("page");
    if (currentPage != "" && currentPage != null) {
      page = Integer.valueOf(currentPage);
    }
    PageDTO<Topic> pageDTO = topicServiceimpl.getSearchPageDto(searchKey, page, pageSize);

    resp.setContentType("application/json; charset=utf-8");
    Gson gson = new Gson();
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(pageDTO));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @Description: 分页接口 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/11
   */
  public void getPageDtoByCId(HttpServletRequest req, HttpServletResponse resp) {
    int cId = Integer.valueOf(req.getParameter("cId"));

    // 默认首页
    int page = 1;
    String currentPage = req.getParameter("page");
    if (currentPage != null && currentPage != "") {
      page = Integer.valueOf(currentPage);
    }

    PageDTO<Topic> pageDTO = topicServiceimpl.getPageDTOByCId(cId, page, pageSize);
    req.setAttribute("pageDTO", pageDTO);
    //    System.out.println(pageDTO.toString());

    /** ajax测试模块 */
    Gson gson = new Gson();
    try {
      //      resp.setCharacterEncoding("utf-8");
      resp.setHeader("Content-type", " application/json; charset=utf-8");
      resp.getWriter().write(gson.toJson(pageDTO));
      //      System.out.println("成功发送数据");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @Description: 根据主题id查询某个具体主题的评论详情 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/11
   */
  public void findDetailByTopicId(HttpServletRequest req, HttpServletResponse resp) {
    String topicId = req.getParameter("topicId");

    // 默认页码是第一页
    int page = 1;
    String currentPage = req.getParameter("page");
    if (currentPage != "" && currentPage != null) {
      page = Integer.valueOf(currentPage);
    }
    Topic topic = topicServiceimpl.findTopicById(Integer.parseInt(topicId));
    PageDTO<Reply> pageDTO =
        topicServiceimpl.findReplyPageByTopicId(Integer.parseInt(topicId), page, pageSize);
    //    System.out.println(pageDTO.toString());
    resp.setContentType("application/json; charset=utf-8");
    Gson gson = new Gson();
    Object[] objects = {topic, pageDTO};
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(objects));
    } catch (IOException e) {
      e.printStackTrace();
    }

    /** 处理浏览量 在前端点击topic后每个session只能计算一次 另一个条件得是登录后 */
    Cookie[] cookies = req.getCookies();
    for (Cookie cookie : cookies) {
      //      System.out.println("............." + cookie.getName() + "........." +
      if (cookie.getName().equals("loginFlag") && Integer.valueOf(cookie.getValue()) == 1) {
        String sessionReadKey = "is_read_" + topicId;
        Boolean isRead = (Boolean) req.getSession().getAttribute(sessionReadKey);
        //        System.out.println(isRead);
        if (isRead == null) {
          req.getSession().setAttribute(sessionReadKey, true);
          topic.setPv(topic.getPv() + 1);
          topicServiceimpl.update(topic);
        }
      }
    }
  }

  /**
   * @Description: 主题发布 @Param: [req, resp]
   *
   * @return: void @Author: lqy @Date: 2022/4/17
   */
  public void publishTopic(HttpServletRequest req, HttpServletResponse resp) {

    int cId = Integer.valueOf(req.getParameter("cId"));
    int userId = Integer.valueOf(req.getParameter("userId"));
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    User user = userServiceimpl.getUserById(userId);
    int flag = topicServiceimpl.save(user, cId, title, content);

    resp.setContentType("application/json;charset=utf-8");
    Gson gson = new Gson();
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(flag));
    } catch (IOException e) {
      e.printStackTrace();
    }

    //    System.out.println("flag=" + flag);
  }

  /** 查询搜索框中相关的所有信息 */
  public void searchByKey(HttpServletRequest req, HttpServletResponse resp) {
    String searchKey = req.getParameter("searchKey");
    List topicList = topicServiceimpl.searchByKey(searchKey);

    resp.setContentType("application/json;charset=utf-8");
    Gson gson = new Gson();
    try {
      PrintWriter out = resp.getWriter();
      out.write(gson.toJson(topicList));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
