package com.poscodx.jblog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}
			model.addAllAttributes(result.getModel()); // Map으로 산출
			return "user/join";

		}
		// 1. 유저 추가
		userService.addUser(userVo);
		
		// 2. 기본 블로그 설정
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(userVo.getName() + "님의 블로그 입니다.");
		blogVo.setBlogId(userVo.getId());
		blogVo.setImage("/assets/images/default.jpg");
		System.out.println(blogVo);
		blogService.addBlog(blogVo);
		
		// 3. 미분류 카테고리 생성
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(userVo.getId());
		categoryVo.setName("카테고리 없음");
		categoryVo.setDescription("분류되지 않은 게시글들을 위한 카테고리입니다.");
		categoryService.addCategory(categoryVo);
		
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String auth(HttpSession session,
			@RequestParam(value = "accountName", required = true, defaultValue = "") String id,
			@RequestParam(value = "password", required = true, defaultValue = "") String password, Model model) {
		UserVo authUser = userService.getUser(id, password);
		if (authUser == null) {
			model.addAttribute("id", id);
			return "user/login";
		}
		/* 인증 처리 */
		session.setAttribute("authUser", authUser);
		System.out.println(authUser + " 로그인 성공 !");
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
		return "redirect:/";
	}

}
