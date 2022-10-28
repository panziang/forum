package net.forum.domain;

import java.time.LocalDateTime;

/**
 * @program: forum
 * @description: 分类bean
 * @author: LQY
 * @create: 2022-04-07 17:32
 */

/*
 `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 `name` varchar(128) DEFAULT NULL,
 `weight` int(11) DEFAULT NULL,
 `create_time` datetime DEFAULT NULL,
* */
public class Category {
  private int id;
  private String name;

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  private int weight;
  private LocalDateTime createTime;
  private String img;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "Category{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", weight="
        + weight
        + ", createTime="
        + createTime
        + ", img='"
        + img
        + '\''
        + '}';
  }
}
