package net.forum.service;

import net.forum.domain.User;

public interface UserService {

  int register(User user);

  User login(String phone, String pwd);

  User getUserById(int userId);

  int updateUser(String userId, String userName, String phone, String pwd, String sex);
}
