package com.exam.repo;

import com.exam.model.exam.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	// Pagination
	
//	Page<Category> findById(@Param("catId")Long cid, Pageable pageable);
}
