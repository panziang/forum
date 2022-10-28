package net.forum.domain;

import java.time.LocalDateTime;
/**
 * @program: forum
 * @description: 用户bean
 * @author: LQY
 * @create: 2022-04-07 17:27
 */
public class User {
  private int id;
  private String phone;
  private String pwd;
  private int sex;
  private String img;
  private LocalDateTime createTime;
  private int role;
  private String userName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", phone='"
        + phone
        + '\''
        + ", pwd='"
        + pwd
        + '\''
        + ", sex="
        + sex
        + ", img='"
        + img
        + '\''
        + ", createTime="
        + createTime
        + ", role="
        + role
        + ", userName='"
        + userName
        + '\''
        + '}';
  }
}
