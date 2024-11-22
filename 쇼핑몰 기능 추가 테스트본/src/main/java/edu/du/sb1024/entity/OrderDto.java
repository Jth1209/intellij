package edu.du.sb1024.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
//@ToString
public class OrderDto {

    private int id;
    private String name;
    private int quantity;
    private String price;
    private int cnt;
    private String des;
    private String status;
    private Long memberId;
    private String type;
    private List<OrderFileDto> fileList;
}
