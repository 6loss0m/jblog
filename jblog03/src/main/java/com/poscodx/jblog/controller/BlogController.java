package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/{id}")
public class BlogController {
	/*
	// 정적 처리 해주기
	// Optional 사용해서 분기 처리 필요.
	// default값 처리하는거 필요
	@RequestMapping({"",  "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Long categoryNo,
			@PathVariable("postNo") Long postNo) {
		// 각 변수 null 처리
		return "BlogController.index() called ...";
	}
	
	// POST
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "BlogController.index() called ...";
	}
	
	@RequestMapping("/admin/basic")
	public String adminCategory(@PathVariable("id") String blogId) {
		return "BlogController.index() called ...";
	}
	*/

}
