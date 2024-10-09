package edu.du.chap17.service;

import java.sql.Connection;
import java.sql.SQLException;

import edu.du.chap17.dao.ArticleDao;
import edu.du.chap17.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadArticleService {

	@Autowired
	ArticleDao articleDao;

	private static ReadArticleService instance = new ReadArticleService();
	public static ReadArticleService getInsteance() {
		return instance;
	}

	private ReadArticleService() {
	}

	public Article readArticle(int articleId) throws ArticleNotFoundException {
		return selectArticle(articleId, true);
	}

	private Article selectArticle(int articleId, boolean increaseCount)
			throws ArticleNotFoundException {
		Connection conn = null;
		try {
			Article article = articleDao.selectById(articleId);
			if (article == null) {
				throw new ArticleNotFoundException(
						"�Խñ��� �������� ����: " + articleId);
			}
			if (increaseCount) {
				articleDao.increaseReadCount(articleId);
				article.setReadCount(article.getReadCount() + 1);
			}
			return article;
		} finally {
		}
	}

	public Article getArticle(int articleId) throws ArticleNotFoundException {
		return selectArticle(articleId, false);
	}
}
