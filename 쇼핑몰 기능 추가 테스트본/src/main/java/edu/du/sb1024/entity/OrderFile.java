package edu.du.sb1024.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name= "o_file")
public class OrderFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	private int orderIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	private Long mid;

	@Column(columnDefinition = "varchar(2) default 'N'")
	private String deletedYn;

}
