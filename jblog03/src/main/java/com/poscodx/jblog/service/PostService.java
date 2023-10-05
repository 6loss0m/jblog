package com.poscodx.jblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public List<PostVo> getPosts(String blogId) {
		return postRepository.findById(blogId);
	}
	
	public List<PostVo> getCategoryPosts(String blogId, Long categoryNo) {
		return postRepository.findByIdAndNo(blogId, categoryNo);
	}

	public void addPost(PostVo vo) {
		postRepository.insert(vo);
	}

	public PostVo getPost(String blogId, Long no) {
		return postRepository.FindByNo(blogId, no);
	}

}
