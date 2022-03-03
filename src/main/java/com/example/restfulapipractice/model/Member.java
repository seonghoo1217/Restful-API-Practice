package com.example.restfulapipractice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String name;
    private int age;
    private String address;
    @CreationTimestamp
    private Date createdAt;

    public Member(Long id,String name, int age, String address,Date date){
        this.id=id;
        this.createdAt=date;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}