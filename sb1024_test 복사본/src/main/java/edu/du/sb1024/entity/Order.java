package edu.du.sb1024.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table (name = "orders")
//@ToString
public class Order {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private String des;
    private String status;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
