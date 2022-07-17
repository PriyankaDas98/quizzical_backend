package com.exam.service;

import com.exam.exception.CategoryNotFoundException;
import com.exam.model.exam.Category;


import java.util.Set;

public interface CategoryService {
//    public Category addCategory(Category category);
//    public Category updateCategory(Category category);
//    public Set<Category> getCategories(Integer pageNumber, Integer pageSize);
//    public  Category getCategory(Long categoryId) throws CategoryNotFoundException;
//    public  void deleteCategory(Long categoryId) throws CategoryNotFoundException;
public Category addCategory(Category category);
    public Category updateCategory(Category category);
//    public Set<Category> getCategories(Integer pageNumber, Integer pageSize);
    public Set<Category> getCategories();
    public  Category getCategory(Long categoryId) throws CategoryNotFoundException;
    public  void deleteCategory(Long categoryId) throws CategoryNotFoundException;

}
