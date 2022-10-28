package net.forum.dto;

/**
 * @program: forum
 * @description: 主题分页类
 * @author: LQY
 * @create: 2022-04-10 19:00
 */
import java.util.List;

/** 泛型 */
public class PageDTO<T> {

  /** 当前页码 */
  private int pageNum;

  /** 每页最大展示的记录数 */
  private int pageSize;

  /** 总记录数 */
  private int totalRecord;

  /** 总页数 */
  private int totalPage;

  /** 数据集合 */
  private List<T> list;

  /** 构造函数 */
  public PageDTO(int pageNum, int pageSize, int totalRecord, List list) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.totalRecord = totalRecord;
    this.list = list;

    /** 计算总页数 */
    this.totalPage = totalRecord / pageSize;
    // 不能整除就加一
    if (totalRecord % pageSize != 0) {
      this.totalPage++;
    }
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalRecord() {
    return totalRecord;
  }

  public void setTotalRecord(int totalRecord) {
    this.totalRecord = totalRecord;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "PageDTO{"
        + "pageNum="
        + pageNum
        + ", pageSize="
        + pageSize
        + ", totalRecord="
        + totalRecord
        + ", totalPage="
        + totalPage
        + ", list="
        + list
        + '}';
  }
}
