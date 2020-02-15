package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * 記事のデータベースを操作する.
 * 
 * @author nonaa
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名 */
	private final static String TABLE_NAME = "articles";
	
	/** 記事情報をセットするローマッパー */
	private final static RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) ->{
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	/**
	 * 記事の全件検索を行う.
	 * 
	 * @return 記事リスト
	 */
	public List<Article> findAll(){
		String sql = "SELECT id, name, content FROM " + TABLE_NAME + " ORDER BY id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
	
	/**
	 *記事の登録を行う. 
	 *
	 *@param 記事
	 */
	public void insertArticle(Article article) {
		String sql = "INSERT INTO " + TABLE_NAME + "(name, content) VALUES(:name, :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}
	
}
