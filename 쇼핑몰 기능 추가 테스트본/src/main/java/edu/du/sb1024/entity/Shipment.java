package edu.du.sb1024.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private String status;
    private String des;
    private String type;
    private Long pid;
    private Long oid;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
