package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
//	 @RequestMapping("{blogId:(?!assets).*}") -> class BlogController()

	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	@Autowired
	private FileUploadService fileUploadService;

	// 정적 처리 해주기
	// Optional 사용해서 분기 처리 필요.
	// default값 처리하는거 필요
	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		// blogVo
		BlogVo blogVo = blogService.getBlog(blogId);
		// 존재하지않는 사용자 블로그 접근 시 main으로 redirect
		if(blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		// categoryVo
		List<CategoryVo> categoryList = categoryService.getCategorys(blogId);
		model.addAttribute("categoryList", categoryList);
		
		// 각 변수 null 처리, isPresent() 없으면 boolean
		// 1. blogId
		if (!(categoryNo.isPresent() || postNo.isPresent())) {
			System.out.println("[ " + blogId + " ]의 블로그 접속 ");
			// psotVo
			List<PostVo> postList = postService.getPosts(blogId);
			model.addAttribute("postList", postList);
			return "blog/main";
			// 2. blogId, categoryNo
		} else if (!postNo.isPresent()) {
			System.out.println("[ " + blogId + " ]의 카테고리" + categoryNo + "번의 게시글들 출력 ");
			List<PostVo> categoryPostList = postService.getCategoryPosts(blogId, categoryNo.get());
			model.addAttribute("categoryPostList", categoryPostList);
			return "blog/category";
			// 3. blogId, categoryNo, postNo
		} else {
			System.out.println("[ " + blogId + " ]의 카테고리" + categoryNo + "번, " + postNo + "번 게시글 출력 ");
			List<PostVo> categoryPostList = postService.getCategoryPosts(blogId, categoryNo.get());
			model.addAttribute("categoryPostList", categoryPostList);
			
			PostVo post = postService.getPost(blogId, postNo.get());
			model.addAttribute("post", post);
			
			return "blog/post";
		}
	}

	// GET
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		System.out.println("[ " + blogId + " ]의 블로그 관리자 페이지 접속 ");
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin-basic";
	}

	// POST
	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String adminBasic(
			@PathVariable("id") String blogId,
			BlogVo vo,
			@RequestParam("f") MultipartFile file,
			Model model) {
		vo.setImage(blogService.getBlog(blogId).getImage());
		vo.setBlogId(blogId);
		if(!file.isEmpty()) {
			String url = fileUploadService.restore(file);
			vo.setImage(url);
		}
		blogService.updateBlog(vo);
		return "redirect:/" + blogId + "/admin/basic";
	}

	// GET
	@RequestMapping(value = {"/admin/category","/admin/category/{categoryNo}"}, method = RequestMethod.GET)
	public String adminCategory(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			Model model) {
		// blogVo
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		
		if (categoryNo.isPresent()){
			System.out.println(categoryNo);
			categoryService.deleteCategory(categoryNo.get());
			return "redirect:/"+blogId+"/admin/category";
		}
		//categoryList
		List<CategoryVo> categoryList = categoryService.getCategoryWithPost(blogId);
		model.addAttribute("categoryList", categoryList);
		
		//categoryCount
		
		
		return "blog/admin-category";
	}

	// POST
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String adminCategory(@PathVariable("id") String blogId, CategoryVo vo) {
		vo.setBlogId(blogId);
		categoryService.addCategory(vo);
		return "redirect:category";
	}

	// GET
	@RequestMapping(value = "/admin/post", method = RequestMethod.GET)
	public String adminPost(@PathVariable("id") String blogId, Model model) {
		// blogVo
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		// categoryVo
		List<CategoryVo> categoryList = categoryService.getCategorys(blogId);
		model.addAttribute("categoryList", categoryList);
		
		return "blog/admin-write";
	}

	// POST
	@RequestMapping(value = "/admin/post", method = RequestMethod.POST)
	public String adminPost(@PathVariable("id") String blogId, PostVo vo, Model model) {
		postService.addPost(vo);
		// blogVo
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		
		//categoryList
		List<CategoryVo> categoryList = categoryService.getCategorys(blogId);
		model.addAttribute("categoryList", categoryList);
		
		return "redirect:/" + blogId + "/admin/post";
	}

}
