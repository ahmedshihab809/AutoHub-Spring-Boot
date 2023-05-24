package com.mycompany.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45,nullable = false)
    private String name;

//    @OneToMany(targetEntity =wPart.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="fk_part_id",referencedColumnName = "id")
//    private List<wPart> wpart;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<wPart> getWpart() {
//        return wpart;
//    }
//
//    public void setWpart(List<wPart> wpart) {
//        this.wpart = wpart;
//    }
}
