package edu.du.sb1024.entity;

import lombok.*;

import javax.persistence.*;

@Entity
//@Data
@Getter
@Setter
@Table(name="comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString
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

