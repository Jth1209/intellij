package edu.du.chap17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public class ReplyingRequest extends WritingRequest {

	public ReplyingRequest(String writerName, String title, String password, String content) {//상속을 받고 본인 필드값을 초기화 해줘야 당연히 상속받은 메소드를 본인걸로 초기화 할 수 있다.
		super(writerName, title, password, content);
		this.setWriterName(writerName);
		this.setTitle(title);
		this.setPassword(password);
		this.setContent(content);
		// TODO Auto-generated constructor stub
	}

	private int parentArticleId;

	public int getParentArticleId() {
		return parentArticleId;
	}
	public void setParentArticleId(int parentArticleId) {
		this.parentArticleId = parentArticleId;
	}
	
}
