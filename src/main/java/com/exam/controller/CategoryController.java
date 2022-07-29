package com.exam.controller;

import com.exam.exception.CategoryNotFoundException;
import com.exam.model.exam.Category;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
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

    //get all category
//    @GetMapping("/")
//    public ResponseEntity<?> getCategories(
//    		@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//    		@RequestParam(value = "pageSize", defaultValue = "4", required = false)Integer pageSize
//    		){
//        return ResponseEntity.ok(this.categoryService.getCategories(pageNumber,pageSize));
//    }
    @GetMapping("/")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //update
    @PutMapping("/")
    public Category update(@Valid @RequestBody Category category){
        return this.categoryService.updateCategory(category);
    }
//    @PutMapping("/{categoryId}")
//    public Category update(@Valid @PathVariable("categoryId") @RequestBody Category category){
//
//        return this.categoryService.updateCategory(category);
//    }

    //delete
    @DeleteMapping("/{categoryId}")
    public  void delete(@PathVariable("categoryId") Long  categoryId) throws CategoryNotFoundException{
        this.categoryService.deleteCategory(categoryId);
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(CategoryNotFoundException ex) {
    	
        return ResponseEntity.ok(ex.getMessage());
    }
}
