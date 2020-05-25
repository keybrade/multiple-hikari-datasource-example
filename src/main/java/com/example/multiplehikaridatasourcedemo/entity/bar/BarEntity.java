package com.example.multiplehikaridatasourcedemo.entity.bar;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class BarEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String whatIsGoingOn;
}
