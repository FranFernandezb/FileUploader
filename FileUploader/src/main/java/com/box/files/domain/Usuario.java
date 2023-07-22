package com.box.files.domain;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private String userName;

    public Usuario() { }

    public Usuario(String userName) {
        this.userName = userName;
    }

    public Long getAccountId() { return accountId; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

}
