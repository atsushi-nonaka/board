package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメント情報を操作するレポジトリ.
 * 
 * @author nonaa
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名 */
	private final static String TABLE_NAME = "comments";
	
	/**
	 * コメント情報を登録するローマッパー.
	 */
	private final static RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) ->{
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
	/**
	 * 記事IDからコメントを取得.
	 * 
	 * @param articleId 記事ID
	 * @return コメントリスト
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT id, name, content, article_id FROM " + TABLE_NAME + " WHERE article_id = :article_id ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("article_id", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}
	
	/**
	 * コメント情報の登録.
	 * 
	 * @param comment コメント
	 */
	public void insertComment(Comment comment) {
		String sql = "INSERT INTO " + TABLE_NAME + "(name, content, article_id) VALUES(:name, :content, :articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}
	
}
