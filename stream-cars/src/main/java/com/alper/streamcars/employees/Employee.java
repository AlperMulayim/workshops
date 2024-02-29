package com.alper.streamcars.employees;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employees")
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    String name;
    @Column
    String surname;
    @Column
    String gender;
    @Column
    Integer age;
    @Column
    String department;
    @Column
    Integer salary;
    @Column
    String city;
    @Column
    String country;

}
