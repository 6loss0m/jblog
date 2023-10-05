package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryVo> getCategorys(String blogId) {
		return categoryRepository.findById(blogId);
	}
	public List<CategoryVo> getCategoryWithPost(String blogId) {
		return categoryRepository.findByIdWithPost(blogId);
	}

	public void addCategory(CategoryVo vo) {
		categoryRepository.insert(vo);
	}
	
	public void deleteCategory(Long no) {
		categoryRepository.delete(no);
	}

}
