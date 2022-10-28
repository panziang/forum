package net.forum.service;

import net.forum.domain.Reply;
import net.forum.domain.Topic;
import net.forum.domain.User;
import net.forum.dto.PageDTO;

import java.util.List;

public interface TopicService {

  PageDTO<Topic> getPageDTOByCId(int cId, int page, int pageSize);

  Topic findTopicById(int topicId);

  PageDTO<Reply> findReplyPageByTopicId(int topicId, int page, int oageSize);

  PageDTO<Topic> getAllPageDto(int page, int pageSize);

  int save(User user, int cId, String title, String content);

  List searchByKey(String searchKey);

  PageDTO<Topic> getSearchPageDto(String searchKey, int page, int pageSize);

  void update(Topic topic);
}
