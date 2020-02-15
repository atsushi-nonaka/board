package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事情報を操作する.
 * 
 * @author nonaa
 *
 */
@Controller
@RequestMapping("")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	* 
	*記事リストを全て表示させる.
	*
	*@param model リクエストスコープ
	*@return 掲示板HTML
	*/
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		
		for(Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		
		model.addAttribute("articleList", articleList);
		return "board/board";
	}
	
	/**
	 * 記事の登録をする.
	 * 
	 * @param model リクエストスコープ
	 * @return indexメソッド
	 */
	@RequestMapping("insert-article")
	public String insertArticle(ArticleForm articleForm) {
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insertArticle(article);
		return "redirect:/";
	}
	
	@RequestMapping("insert-comment")
	public String insertComment(CommentForm commentForm) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(commentForm.getIntArticleId());
		commentRepository.insertComment(comment);
		return "redirect:/";
	}
}
