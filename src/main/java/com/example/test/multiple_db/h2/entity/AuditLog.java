package com.example.test.multiple_db.h2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AuditLog {

    public AuditLog() {
    }

    public AuditLog(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    @Id
    private Long id;
    private String message;


}
