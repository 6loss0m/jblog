package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<CategoryVo> findById(String blogId) {
		return sqlSession.selectList("category.findById", blogId);
	}

	public boolean insert(CategoryVo vo) {
		int count = sqlSession.insert("category.insert", vo);
		return count == 1;
	}

	public void delete(Long no) {
		sqlSession.delete("post.deleteByNo",no);
		sqlSession.delete("category.deleteByNo",no);
	}

	public List<CategoryVo> findByIdWithPost(String blogId) {
		return sqlSession.selectList("category.findByIdWithPost", blogId);
	}

}
