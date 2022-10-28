package net.forum.service.impl;

import net.forum.dao.CategoryDao;
import net.forum.domain.Category;
import net.forum.service.CategoryService;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-09 19:08
 */
public class CategoryServiceimpl implements CategoryService {
  private CategoryDao categoryDao = new CategoryDao();

  @Override
  public List<Category> getCategoryList() {
    return categoryDao.getCategoryList();
  }
}
