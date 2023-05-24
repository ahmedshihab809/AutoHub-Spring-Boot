package com.mycompany.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wservices")
public class wService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45,nullable = false)
    private Integer price;

    @Column(length = 20,nullable = false)
    private String details;

    @ManyToOne
    @JoinColumn(name="service_id")
    private Servicing service;
    @ManyToOne
    @JoinColumn(name="workshop_id")
    private Workshop workshop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Servicing getService() {
        return service;
    }

    public void setService(Servicing service) {
        this.service = service;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
