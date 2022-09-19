package com.exam.service.impl;

import com.exam.exception.CategoryNotFoundException;
import com.exam.model.exam.Category;
import com.exam.repo.CategoryRepository;
import com.exam.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        logger.info("new category added.");
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        logger.info("category has been updated");
        return this.categoryRepository.save(category);
    }


    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public Category getCategory(Long categoryId) throws CategoryNotFoundException {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

    }


    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        this.categoryRepository.delete(category);
        logger.info("category deleted successfully");


    }


}
