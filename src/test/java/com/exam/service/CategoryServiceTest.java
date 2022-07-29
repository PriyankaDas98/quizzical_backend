package com.exam.service;

import com.exam.exception.CategoryNotFoundException;
import com.exam.model.exam.Category;
import com.exam.repo.CategoryRepository;
import com.exam.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    @DisplayName("Get all the Categories")
    public void getCategoriesTest(){
        when(categoryRepository.findAll()).thenReturn(Stream
                .of(new Category())
                .collect(Collectors.toList()));

        assertThat(categoryServiceImpl.getCategories().size()).isEqualTo(1);

    }
    @Test
    @DisplayName("Create Category test")
    public void addCategoryTest(){
        Category category = new Category();
        category.setTitle("Programming");
        category.setDescription("Java 8 feature");
        when(categoryRepository.save(category)).thenReturn(category);
        assertThat(categoryServiceImpl.addCategory(category).getTitle())
                .isEqualTo(category.getTitle());

    }
    @Test
    @DisplayName("get category by id test")
    public void getCategory() throws CategoryNotFoundException {
        Category category = new Category();
        category.setCid(99L);
        category.setTitle("GK");
        when(categoryRepository.findById(99L)).thenReturn(Optional.of(category));
        assertThat(categoryServiceImpl.getCategory(99L).getTitle())
                .isEqualTo("GK");
    }
}
