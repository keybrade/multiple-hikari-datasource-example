package com.example.multiplehikaridatasourcedemo.entity.foo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class FooEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String whatIsGoingOn;

}
