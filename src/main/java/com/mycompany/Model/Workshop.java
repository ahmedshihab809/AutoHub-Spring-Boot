package com.mycompany.Model;

import jakarta.persistence.*;
import org.json.simple.JSONObject;

import java.util.List;

@Entity
@Table(name="workshops")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(nullable = false,unique = true,length = 45)
    public String email;
    @Column(length = 100,nullable = false)
    public String password;
    @Column(length = 45,nullable = false,name="name")
    public String name;

    public boolean verified;


    @Column(length = 100,nullable = true)
    public String address;
    @Column(length = 45,nullable = false)
    public String mobile;
    @Column(length = 100,nullable = true)
    public Double latitude;

    @Column(length = 100,nullable = true)
    public Double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }


}
