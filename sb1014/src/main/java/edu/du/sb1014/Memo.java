package edu.du.sb1014;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(name = "test")
@Getter
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String text;

}
