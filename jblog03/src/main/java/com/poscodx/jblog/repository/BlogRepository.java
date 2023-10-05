package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(BlogVo vo) {
		int count = sqlSession.insert("blog.insert", vo);
		return count == 1;
	}

	public BlogVo findById(String blogId) {
		return sqlSession.selectOne("blog.findById", blogId);
	}

	public void update(BlogVo vo) {
		sqlSession.update("blog.update", vo);
	}

}
