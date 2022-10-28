package net.forum.dao;

import net.forum.domain.Reply;
import net.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: forum
 * @description: 处理reply类的bean
 * @author: LQY
 * @create: 2022-04-11 20:34
 */
public class ReplyDao {
  QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

  // 开启驼峰映射  使得数据库的_命名可以转为小驼峰大写
  private BeanProcessor beanProcessor = new GenerousBeanProcessor();
  private RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);

  /**
   * @Description: 根据话题id找所有评论数量 @Param: [TopicId]
   *
   * @return: int @Author: lqy @Date: 2022/4/11
   */
  public int getTotalReplyByTopicId(int TopicId) {
    String sql = "select count(*) from reply where topic_id=?";
    long count = 0;
    try {
      count = queryRunner.query(sql, new ScalarHandler<>(), TopicId);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return (int) count;
  }

  /**
   * @Description: 根据topicID与要查询的位置 查询数量返回reply的list列表 @Param: [topicId, from, pageSize]
   *
   * @return: java.util.List @Author: lqy @Date: 2022/4/11
   */
  public List getReplyListByTopicId(int topicId, int from, int pageSize) {
    String sql =
        "select * from reply where topic_id=? and ISNULL(reply_id) order by create_time asc limit ?,?";
    List<Reply> replyList = null;

    try {
      replyList =
          queryRunner.query(
              sql, new BeanListHandler<>(Reply.class, rowProcessor), topicId, from, pageSize);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return replyList;
  }
}
