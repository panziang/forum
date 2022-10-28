package net.forum.dao;

import net.forum.domain.User;
import net.forum.util.CommonUtil;
import net.forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-12 14:41
 */
public class UserDao {
  QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

  // 开启驼峰映射  使得数据库的_命名可以转为小驼峰大写
  private BeanProcessor beanProcessor = new GenerousBeanProcessor();
  private RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);

  public int save(User user) throws Exception {
    String sql =
        "insert into user(phone,pwd,sex,img,create_time,`role`,userName) values(?,?,?,?,?,?,?)";
    Object[] params = {
      user.getPhone(),
      user.getPwd(),
      user.getSex(),
      user.getImg(),
      user.getCreateTime(),
      user.getRole(),
      user.getUserName()
    };
    int flag = 0;
    try {
      flag = queryRunner.update(sql, params);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      throw new Exception("插入新用户失败！");
    }
    return flag;
  }

  public User getUserByPhone_Pwd(String phone, String pwd) {
    User user = null;
    String sql = "select * from user where phone=? and pwd=?";
    try {
      user =
          queryRunner.query(
              sql, new BeanHandler<>(User.class, rowProcessor), phone, CommonUtil.MD5(pwd));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }

  public User getUserById(int userId) {
    String sql = "select * from user where id=?";
    User user = null;
    try {
      user = queryRunner.query(sql, new BeanHandler<>(User.class, rowProcessor), userId);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }

  public int updateUser(String userId, String userName, String phone, String pwd, String sex) {
    int flag = 0;
    int _sex;
    String sql;
    Object[] params;
    pwd = CommonUtil.MD5(pwd);
    _sex = sex.equals("男") ? 1 : 0;
    System.out.println(sex + _sex);
    sql = "update user set userName=?,phone=?,pwd=?,sex=? where id=?";
    params = new Object[] {userName, phone, pwd, _sex, userId};
    try {
      flag = queryRunner.update(sql, params);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    /**
     * if (userName.equals("")) { pwd = CommonUtil.MD5(pwd); _sex = sex.equals("男") ? 1 : 0; sql =
     * "update user set phone=?,pwd=?,sex=? where id=?"; params = new Object[] {phone, pwd, _sex,
     * userId}; try { flag = queryRunner.update(sql, params); } catch (SQLException throwables) {
     * throwables.printStackTrace(); } } else if (phone.equals("")) { pwd = CommonUtil.MD5(pwd);
     * _sex = sex.equals("男") ? 1 : 0; sql = "update user set pwd=?,sex=? where id=?"; params = new
     * Object[] {pwd, _sex, userId}; try { flag = queryRunner.update(sql, params); } catch
     * (SQLException throwables) { throwables.printStackTrace(); } } else if (pwd.equals("")) { _sex
     * = sex.equals("男") ? 1 : 0; sql = "update user set sex=? where id=?"; params = new Object[]
     * {_sex, userId}; try { flag = queryRunner.update(sql, params); } catch (SQLException
     * throwables) { throwables.printStackTrace(); } } else { pwd = CommonUtil.MD5(pwd); _sex =
     * sex.equals("男") ? 1 : 0; sql = "update user set userName=?,phone=?,pwd=?,sex=? where id=?";
     * params = new Object[] {userName, phone, pwd, _sex, userId}; try { flag =
     * queryRunner.update(sql, params); } catch (SQLException throwables) {
     * throwables.printStackTrace(); } }
     */
    return flag;
  }
}
