package com.example.test.multiple_db.mysql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class User {


    public User() {
    }

    public User(Long userId, String userName, String password, String department) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.department = department;
    }

    @Id
    private Long userId;
    private String userName;
    private String password;
    private String department;


}
