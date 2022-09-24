package com.exam.controller;

import com.exam.exception.CategoryNotFoundException;
import com.exam.model.exam.Category;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
//@CrossOrigin("http://localhost:4200")
public class CategoryController {
	

    @Autowired
    private CategoryService categoryService;

    //add category
    @PostMapping("/")
    public ResponseEntity<Category> add(@Valid @RequestBody Category category){
        Category category1 = this.categoryService.addCategory(category);
        return ResponseEntity.ok(category1);
    }

    //getCategory
    @GetMapping("/{categoryId}")
    public Category get(@PathVariable("categoryId") Long categoryId) throws CategoryNotFoundException{
        return this.categoryService.getCategory(categoryId);
    }

    @GetMapping("/")
    public ResponseEntity<Set<Category>> getCategories(){
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //update
    @PutMapping("/")
    public Category update(@Valid @RequestBody Category category){
        return this.categoryService.updateCategory(category);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public  void delete(@PathVariable("categoryId") Long  categoryId) throws CategoryNotFoundException{
        this.categoryService.deleteCategory(categoryId);
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    public String exceptionHandler(CategoryNotFoundException ex) {
    	
        return (ex.getMessage());
    }
}
