package edu.du.sb1024.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name= "t_board")
@ToString
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardIdx;
	
	private String title;
	
	private String contents;

	@ColumnDefault("0") //default 0
	private Integer hitCnt;
	
	private String creatorId;
	
	private String createdDatetime;
	
	private String updaterId;
	
	private String updatedDatetime;

	private String type;

	@Column(columnDefinition = "varchar(2) default 'N'")
	private String deletedYn;

	@OneToMany(mappedBy="board")
	private List<Comment> comments;

//	@Column
//	private String keyword;
}
