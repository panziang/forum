package net.forum.dao;

import net.forum.domain.Reply;
import net.forum.domain.Topic;
import net.forum.domain.User;
import net.forum.dto.PageDTO;
import net.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: forum
 * @description: topic处理
 * @author: LQY
 * @create: 2022-04-10 22:33
 */
public class TopicDao {
  QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

  // 开启驼峰映射  使得数据库的_命名可以转为小驼峰大写
  private BeanProcessor beanProcessor = new GenerousBeanProcessor();
  private RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);

  /** 根据分类id返回所有的topic数量 */
  public int getTotalTopicByCId(int cId) {
    long count = 0;
    String sql = "select count(*) from topic where c_id=? and `delete`=0";
    try {
      count = (long) queryRunner.query(sql, new ScalarHandler<>(), cId);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return (int) count;
  }

  /**
   * @Description: 返回满足需要的topicList @Param: [cId, from, pageSize]
   *
   * @return: java.util.List<net.forum.domain.Topic> @Author: lqy @Date: 2022/4/10
   */
  public List<Topic> getTopicListByCId(int cId, int from, int pageSize) {
    String sql =
        "select * from topic where c_id=? and `delete`=0 order by update_time desc limit ?,?";
    List<Topic> topicList = null;
    try {
      topicList =
          queryRunner.query(
              sql, new BeanListHandler<>(Topic.class, rowProcessor), cId, from, pageSize);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return topicList;
  }

  /**
   * @Description: 根据主题id找topic @Param: [topicId]
   *
   * @return: net.forum.domain.Topic @Author: lqy @Date: 2022/4/11
   */
  public Topic findTopicById(int topicId) {
    String sql = "select * from topic where id=?";
    Topic topic = null;
    try {
      topic = queryRunner.query(sql, new BeanHandler<>(Topic.class, rowProcessor), topicId);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return topic;
  }

  /**
   * @Description: 根据主题id和页数找当前页数的主题评论 @Param: [topicId, page, oageSize]
   *
   * @return: net.forum.dto.PageDTO<net.forum.domain.Reply> @Author: lqy @Date: 2022/4/11
   */
  public PageDTO<Reply> findReplyPageByTopicId(int topicId, int page, int oageSize) {
    return null;
  }

  /**
   * @Description: 得到当前页数的全部主题 @Param: [from, pageSize]
   *
   * @return: java.util.List @Author: lqy @Date: 2022/4/19
   */
  public List getAllTopic(int from, int pageSize) {
    String sql = "select * from topic where `delete`=0 limit ?,?";
    List topicList = null;
    try {
      topicList =
          queryRunner.query(sql, new BeanListHandler<>(Topic.class, rowProcessor), from, pageSize);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return topicList;
  }

  public int getTotalTopic() {
    long sum = 0;
    String sql = "select count(*) from topic where `delete`=0";
    try {
      sum = queryRunner.query(sql, new ScalarHandler<>());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return (int) sum;
  }

  public int save(User user, int cId, String title, String content) {
    if (title == "" || content == "") {
      return 0;
    }
    int flag = 0;
    String sql =
        "insert into topic(c_id,title,content,pv,user_id,userName,user_img,create_time,update_time,hot,`delete`) values(?,?,?,?,?,?,?,?,?,?,?)";
    Object[] params = {
      cId,
      title,
      content,
      1,
      user.getId(),
      user.getUserName(),
      user.getImg(),
      LocalDateTime.now(),
      LocalDateTime.now(),
      0,
      0
    };
    try {
      flag = queryRunner.update(sql, params);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return flag;
  }

  public List searchByKey(String searchKey) {
    List topicList = null;
    String sql =
        "select * from topic where (title like '%"
            + searchKey
            + "%'"
            + " or content like '%"
            + searchKey
            + "%') and `delete`=0";
    try {
      topicList = queryRunner.query(sql, new BeanListHandler<>(Topic.class, rowProcessor));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return topicList;
  }

  public int getTotalBySearchKey(String searchKey) {
    return this.searchByKey(searchKey).size();
  }

  public List getTopicListBySearchKey(String searchKey, int from, int pageSize) {
    List topicList = null;
    String sql =
        "select * from topic where (title like '%"
            + searchKey
            + "%'"
            + " or content like '%"
            + searchKey
            + "%') and `delete`=0 limit ?,?";
    try {
      topicList =
          queryRunner.query(sql, new BeanListHandler<>(Topic.class, rowProcessor), from, pageSize);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return topicList;
  }

  public void update(Topic topic) {
    String sql = "update topic set title=?,content=?,pv=?,update_time=? where id=?";
    Object[] params = {
      topic.getTitle(), topic.getContent(), topic.getPv(), LocalDateTime.now(), topic.getId()
    };
    try {
      queryRunner.update(sql, params);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
