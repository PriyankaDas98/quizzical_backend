package com.exam.repo;

import com.exam.exception.CategoryNotFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.exam.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class CategoryRepoTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Testing add category")
     void addCategoryTest(){
        Category category = new Category();
        category.setTitle("GK");
        category.setDescription("History");
        assertThat(categoryRepository.save(category)).isNotNull();

    }
    @Test
    @DisplayName("Testing get category")
    void getCategoryTest(){
        assertThat(categoryRepository.findById(70L)).isNotNull();

    }
    @Test
    @DisplayName("Should get all categories")
    void getCategoriesTest(){
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList.size()).isNotPositive();
    }

    @Test
    @DisplayName("Should delete category")
    void deleteCategoryTest(){
        Category category = new Category();
        category.setTitle("AI/ML");
        category.setDescription("Nothing");
        // saved  new category
        Category savedInDb = categoryRepository.save(category);
         // delete the category
        categoryRepository.delete(savedInDb);

        assertThat(categoryRepository.findById(savedInDb.getCid())).isEmpty();

    }
}
