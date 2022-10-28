package net.forum.domain;

import java.time.LocalDateTime;

/**
 * @program: forum
 * @description: 评论bean
 * @author: LQY
 * @create: 2022-04-08 15:16
 */
public class Reply {
  private int id;
  private int topicId;
  private int replyId;
  private int floor;
  private String content;
  private int userId;
  private String userName;
  private String userImg;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private int delete;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTopicId() {
    return topicId;
  }

  public void setTopicId(int topicId) {
    this.topicId = topicId;
  }

  public int getReplyId() {
    return replyId;
  }

  public void setReplyId(int replyId) {
    this.replyId = replyId;
  }

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  public int getDelete() {
    return delete;
  }

  public void setDelete(int delete) {
    this.delete = delete;
  }

  @Override
  public String toString() {
    return "Reply{"
        + "id="
        + id
        + ", topicId="
        + topicId
        + ", replyId="
        + replyId
        + ", floor="
        + floor
        + ", content='"
        + content
        + '\''
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
        + ", delete="
        + delete
        + '}';
  }
}
