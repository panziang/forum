package net.forum.domain;

import java.time.LocalDateTime;

/**
 * @program: forum
 * @description: 用户发布主题bean
 * @author: LQY
 * @create: 2022-04-08 15:06
 */
public class Topic {
  private int id;
  private int c_id;
  private String title;
  private String content;
  private int pv;
  private int userId;
  private String userName;
  private String userImg;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private int hot;
  private int delete;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getC_id() {
    return c_id;
  }

  public void setC_id(int c_id) {
    this.c_id = c_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getPv() {
    return pv;
  }

  public void setPv(int pv) {
    this.pv = pv;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserImg() {
    return userImg;
  }

  public void setUserImg(String userImg) {
    this.userImg = userImg;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public int getHot() {
    return hot;
  }

  public void setHot(int hot) {
    this.hot = hot;
  }

  public int getDelete() {
    return delete;
  }

  public void setDelete(int delete) {
    this.delete = delete;
  }

  @Override
  public String toString() {
    return "Topic{"
        + "id="
        + id
        + ", c_id="
        + c_id
        + ", title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", pv="
        + pv
        + ", userId="
        + userId
        + ", userName='"
        + userName
        + '\''
        + ", userImg='"
        + userImg
        + '\''
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", hot="
        + hot
        + ", delete="
        + delete
        + '}';
  }
}
