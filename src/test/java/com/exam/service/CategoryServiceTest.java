package com.exam.service;

import com.exam.exception.CategoryNotFoundException;
import com.exam.exception.UserNotFoundException;
import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.CategoryRepository;
import com.exam.service.impl.CategoryServiceImpl;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @InjectMocks
    CategoryServiceImpl categoryService;
    @Mock
    CategoryRepository categoryRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getCategoriesTest(){
        List<Category> catList = new ArrayList<>();
        Category catOne = new Category();
        Category catTwo = new Category();
        Category catThree = new Category();
        catList.add(catOne);
        catList.add(catTwo);
        catList.add(catThree);
        when(categoryRepository.findAll()).thenReturn(catList);
        //test
        Set<Category> list = categoryService.getCategories();
        assertEquals(3, list.size());
        verify(categoryRepository, times(1)).findAll();

    }
    @SneakyThrows
    @Test
    public void getCategoryByIdTest(){
        Category category = new Category();
        category.setCid(99L);
        category.setTitle("GK");
        category.setDescription("General knowledge category");
        when(categoryRepository.findById(99L)).thenReturn(Optional.of(category));
        Category catObj = categoryService.getCategory(99L);

        assertEquals("GK", catObj.getTitle());
        assertEquals("General knowledge category",catObj.getDescription());

    }
    @Test
    public void addCategoryTest(){
        Category category = new Category();
        category.setTitle("Programming");
        category.setDescription("Java 8 feature");
        when(categoryRepository.save(category)).thenReturn(category);
        assertThat(categoryService.addCategory(category).getTitle())
                .isEqualTo(category.getTitle());

    }
    @Test
    public void updateCategoryTest(){
        Category category = new Category();
        category.setTitle("Java");
        category.setDescription("Java 8 feature");
        when(categoryRepository.save(category)).thenReturn(category);
        assertThat(categoryService.addCategory(category).getTitle())
                .isEqualTo(category.getTitle());

    }

    @Test
    public void getCategoryExceptionTest() {
        assertThatThrownBy(() -> {
            categoryService.getCategory(999L);
        }).isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category with this username not found in database !!");

    }
}
