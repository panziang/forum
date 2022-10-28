package net.forum.dao;

import net.forum.domain.Category;
import net.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-09 19:15
 */
public class CategoryDao {
  QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

  // 开启驼峰映射  使得数据库的_命名可以转为小驼峰大写
  private BeanProcessor beanProcessor = new GenerousBeanProcessor();
  private RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);

  /**
   * @Description: 根据id找分类 @Param: [id]
   *
   * @return: net.forum.domain.Category @Author: lqy @Date: 2022/4/10
   */
  public Category getCategoryById(int id) {
    String sql = "select * from category where id=?";
    Category category = null;
    try {
      category = queryRunner.query(sql, new BeanHandler<>(Category.class, rowProcessor), id);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return category;
  }

  /**
   * @Description: 查询所有分类 返回列表 @Param: []
   *
   * @return: java.util.List @Author: lqy @Date: 2022/4/10
   */
  public List getCategoryList() {
    String sql = "select * from category order by weight desc";
    List<Category> categoryList = null;
    try {
      categoryList = queryRunner.query(sql, new BeanListHandler<>(Category.class, rowProcessor));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categoryList;
  }
}
