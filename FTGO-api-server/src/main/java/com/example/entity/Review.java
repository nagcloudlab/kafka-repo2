package com.example.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int stars;
    private String body;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
