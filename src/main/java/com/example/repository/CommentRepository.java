package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

	public List<Comment> findByArticleId(int articleId) {
		String sql = "SELECT * FROM comments WHERE article_id=:article_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("article_id", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);		
		return commentList;

	}

	public void insert(Comment comment) {
		String insertSql = "INSERT INTO comments (name, content, article_id) VALUES(:name, :content, :articleId) ";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(insertSql, param);
	}

	public void deleteByArticleId(int articleId) {
		String deleteSql = "DELETE FROM comments WHERE article_id=:article_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("article_id", articleId);
		template.update(deleteSql, param);
	}

}
