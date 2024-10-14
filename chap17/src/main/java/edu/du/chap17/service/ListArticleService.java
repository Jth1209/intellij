package edu.du.chap17.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.du.chap17.dao.ArticleDao;
import edu.du.chap17.model.Article;
import edu.du.chap17.model.ArticleListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListArticleService {

	@Autowired
	ArticleDao articleDao;

	private static ListArticleService instance = new ListArticleService();
	public static ListArticleService getInstance() {
		return instance;
	}
	
	public static final int COUNT_PER_PAGE = 5;

	public ArticleListModel getArticleList(int requestPageNumber) {
		if (requestPageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "
					+ requestPageNumber);
		}
        int totalArticleCount = articleDao.selectCount();

        if (totalArticleCount == 0) {
            return new ArticleListModel();
        }

        int totalPageCount = calculateTotalPageCount(totalArticleCount);

        int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE;//페이지의 첫번째 칼럼을 가져오기 위해서는 0 부터 5개씩 짤라야 한다. 그래서 firstRow를 0
        int endRow = firstRow + COUNT_PER_PAGE;//endRow를 5로 나오게끔 설정 이후 5가 넘어가면 

        if (endRow > totalArticleCount) {
            endRow = totalArticleCount;
        }
        List<Article> articleList = articleDao.select(firstRow,
                endRow);

        ArticleListModel articleListView = new ArticleListModel(
                articleList, requestPageNumber, totalPageCount, firstRow+1,
                endRow);
        return articleListView;
    }

	private int calculateTotalPageCount(int totalArticleCount) {
		if (totalArticleCount == 0) {
			return 0;
		}
		int pageCount = totalArticleCount / COUNT_PER_PAGE;
		if (totalArticleCount % COUNT_PER_PAGE > 0) {
			pageCount++;
		}
		return pageCount;
	}
}
