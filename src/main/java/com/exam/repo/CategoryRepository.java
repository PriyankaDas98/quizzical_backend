package com.exam.repo;

import com.exam.model.exam.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	// Pagination
	
//	Page<Category> findById(@Param("catId")Long cid, Pageable pageable);
}
