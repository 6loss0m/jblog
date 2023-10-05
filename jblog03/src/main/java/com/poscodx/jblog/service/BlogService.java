package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public Boolean addBlog(BlogVo vo) {
		return blogRepository.insert(vo);
	}

	public BlogVo getBlog(String blogId) {
		return blogRepository.findById(blogId);
	}

	public void updateBlog(BlogVo vo) {
		blogRepository.update(vo);
	}

}
