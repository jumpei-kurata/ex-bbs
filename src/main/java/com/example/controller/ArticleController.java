package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/twitter")
public class ArticleController {

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();

		for (Article article : articleList) {
			Integer articleId = article.getId();
			List<Comment> commentList = commentRepository.findByArticleId(articleId);
			article.setCommentList(commentList);
			System.out.println(commentList);
		}
		model.addAttribute("articleList", articleList);
//		for(Integer i =0; i<articleList.size(); i++) {
//			Integer articleId = articleList.get(i).getId();
//			List<Comment> commentList = commentRepository.findByArticleId(articleId);
//			articleList.get(i).setCommentList(commentList);
//		}

		return "twitter";
	}

	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm articleForm) {
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		return "redirect:/twitter";
	}

	@RequestMapping("/insertComment")
	public String insertArticle(CommentForm commentForm) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		commentRepository.insert(comment);
		return "redirect:/twitter";
	}

	@RequestMapping("/deleteArticle")
	public String deleteArticle(Integer id) {
		articleRepository.deletedById(id);
		commentRepository.deleteByArticleId(id);
		return "redirect:/twitter";
	}
}
