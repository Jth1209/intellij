package edu.du.sb1024.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BoardDto {
	
	private int boardIdx;
	
	private String title;
	
	private String contents;
	
	private int hitCnt;
	
	private String creatorId;
	
	private String createdDatetime;
	
	private String updaterId;
	
	private String updatedDatetime;

	private String uname;

	private String type;

	private List<BoardFileDto> fileList;

	private List<Comment> comments;
}
