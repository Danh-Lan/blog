package com.post.blog.service;

import com.post.blog.model.Category;

import java.util.Set;

public interface CategoryService {
    Category saveCategory(Category category);
    Set<Category> getAllCategories();
    Category updateCategory(Integer Id, Category updatedCategory);
    void deleteCategory(Integer id);
}
