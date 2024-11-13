package edu.du.sb1024.entity;

import lombok.Data;

@Data
public class OrderFileDto {
	
	private int idx;
	
	private int orderIdx;
	
	private String originalFileName;
	
	private String storedFilePath;
	
	private long fileSize;
}
