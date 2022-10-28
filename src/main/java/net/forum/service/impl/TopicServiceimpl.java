package net.forum.service.impl;

import net.forum.dao.ReplyDao;
import net.forum.dao.TopicDao;
import net.forum.domain.Reply;
import net.forum.domain.Topic;
import net.forum.domain.User;
import net.forum.dto.PageDTO;
import net.forum.service.TopicService;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-10 22:02
 */
public class TopicServiceimpl implements TopicService {
  private TopicDao topicDao = new TopicDao();
  private ReplyDao replyDao = new ReplyDao();

  /**
   * @Description: 根据分类id和需要的页数及每页最大展示条数返回PageDTO @Param: [cId, page, pageSize]
   *
   * @return: net.forum.dto.PageDTO<net.forum.domain.Topic> @Author: lqy @Date: 2022/4/19
   */
  @Override
  public PageDTO<Topic> getPageDTOByCId(int cId, int page, int pageSize) {
    // 总查询记录数
    int totalTopic = topicDao.getTotalTopicByCId(cId);

    int from = (page - 1) * pageSize;

    // 分页查询
    List<Topic> topicList = topicDao.getTopicListByCId(cId, from, pageSize);
    PageDTO<Topic> pageDTO = new PageDTO<Topic>(page, pageSize, totalTopic, topicList);

    return pageDTO;
  }

  @Override
  public Topic findTopicById(int topicId) {
    return topicDao.findTopicById(topicId);
  }

  @Override
  public PageDTO<Reply> findReplyPageByTopicId(int topicId, int page, int pageSize) {
    int totalReply = replyDao.getTotalReplyByTopicId(topicId);

    int from = (page - 1) * pageSize;
    // 分页查询
    List<Reply> replyList = replyDao.getReplyListByTopicId(topicId, from, pageSize);
    PageDTO<Reply> replyPageDTO = new PageDTO<Reply>(page, pageSize, totalReply, replyList);

    return replyPageDTO;
  }

  @Override
  public PageDTO<Topic> getAllPageDto(int page, int pageSize) {
    int totalTopic = topicDao.getTotalTopic();
    int from = (page - 1) * pageSize;
    List topucList = topicDao.getAllTopic(from, pageSize);
    PageDTO<Topic> pageDTO = new PageDTO<>(page, pageSize, totalTopic, topucList);
    return pageDTO;
  }

  @Override
  public int save(User user, int cId, String title, String content) {
    int flag = 0;
    flag = topicDao.save(user, cId, title, content);
    return flag;
  }

  @Override
  public List searchByKey(String searchKey) {
    return topicDao.searchByKey(searchKey);
  }

  @Override
  public PageDTO<Topic> getSearchPageDto(String searchKey, int page, int pageSize) {
    int total = topicDao.getTotalBySearchKey(searchKey);
    int from = (page - 1) * pageSize;
    List topicList = topicDao.getTopicListBySearchKey(searchKey, from, pageSize);
    PageDTO<Topic> pageDTO = new PageDTO<>(page, pageSize, total, topicList);
    return pageDTO;
  }

  @Override
  public void update(Topic topic) {
    topicDao.update(topic);
  }
}
