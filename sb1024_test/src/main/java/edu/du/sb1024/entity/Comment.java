package edu.du.sb1024.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//o
    @Column
    private String nick;//o
    @Column
    private String content;//o
    @Column
    private String createdTime;
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;
}

