package edu.du.sb1024.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table (name = "products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Products {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private String des;
    private String status;
    private String type;
    private Long oid;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
