package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> findById(String blogId) {
		return sqlSession.selectList("post.findById", blogId);
	}

	public boolean insert(PostVo vo) {
		int count = sqlSession.insert("post.insert", vo);
		return count == 1;
	}

	public List<PostVo> findByIdAndNo(String blogId, Long categoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("categoryNo", categoryNo);
		return sqlSession.selectList("post.findByIdAndNo", map);

	}

	public PostVo FindByNo(String blogId, Long no) {
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("categoryNo", no);
		return sqlSession.selectOne("post.findByNo", no);
	}

}
