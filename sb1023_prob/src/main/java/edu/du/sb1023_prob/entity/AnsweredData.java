package edu.du.sb1023_prob.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="answeredData")
@Data
public class AnsweredData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ad_id")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="res_id")
	private Respondent res;
//	@ElementCollection
//	@Column(name="results")
//	private List<String> results;
	@ElementCollection
	@Column(name="results")
	private List<String> result;

}
