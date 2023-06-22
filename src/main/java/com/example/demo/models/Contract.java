package com.example.demo.models;

import com.example.demo.enums.EStateContract;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date starting_date;
    private Date ending_date;
    private EStateContract state;
    @ManyToOne
    private Property property;

}
