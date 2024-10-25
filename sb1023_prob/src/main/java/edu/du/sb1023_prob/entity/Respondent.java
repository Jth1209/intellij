package edu.du.sb1023_prob.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="respondent")
public class Respondent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="res_id")
	private int id;
	private int age;
	private String location;

	@OneToMany(mappedBy="res")
	private List<AnsweredData> answeredData;

}
