package net.forum.service.impl;

import net.forum.dao.UserDao;
import net.forum.domain.User;
import net.forum.service.UserService;
import net.forum.util.CommonUtil;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-12 14:14
 */
public class UserServiceimpl implements UserService {
  UserDao userDao = new UserDao();

  @Override
  public int register(User user) {
    // 普通用户 赋值1
    user.setRole(0);
    // LocalDateTime.now()获取当前时间
    user.setCreateTime(LocalDateTime.now());
    user.setImg(getRandomImg());
    // 密码加密
    user.setPwd(CommonUtil.MD5(user.getPwd()));

    try {
      return userDao.save(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public User login(String phone, String pwd) {
    User user = userDao.getUserByPhone_Pwd(phone, pwd);

    return user;
  }

  @Override
  public User getUserById(int userId) {
    return userDao.getUserById(userId);
  }

  @Override
  public int updateUser(String userId, String userName, String phone, String pwd, String sex) {
    int flag = userDao.updateUser(userId, userName, phone, pwd, sex);

    return flag;
  }

  /** 放在cdn的随机头像 图片的链接地址 */
  private static final String[] headImg = {
    "https://img1.baidu.com/it/u=583582599,306470958&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img1.baidu.com/it/u=1087930174,4287979888&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
    "https://img2.baidu.com/it/u=2090606195,1473750087&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img2.baidu.com/it/u=3104493191,2880401081&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img2.baidu.com/it/u=1742064249,2154824212&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img0.baidu.com/it/u=2869855171,615441262&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img2.baidu.com/it/u=2090606195,1473750087&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img0.baidu.com/it/u=2479875036,2226038759&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
    "https://img1.baidu.com/it/u=1667463067,1949106086&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
    "https://img1.baidu.com/it/u=1487886557,3317274354&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
  };

  private String getRandomImg() {
    int size = headImg.length;
    Random random = new Random();
    int index = random.nextInt(size);
    return headImg[index];
  }
}
