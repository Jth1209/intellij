package edu.du.sb1014_2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false , unique = true)
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false , unique = true)
    private String name;

    private String email;

}
