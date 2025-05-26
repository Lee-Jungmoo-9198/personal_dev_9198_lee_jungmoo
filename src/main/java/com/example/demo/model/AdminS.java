package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AdminS implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;

    private LocalDateTime loginTime;


    public AdminS() {
    }

    public void login(Integer id, String email) {
        this.id = id;
        this.email = email;
        this.loginTime = LocalDateTime.now();
    }

    public void logout() {
        this.id = null;
        this.email = null;
        this.loginTime = null;
    }

    public boolean isLoggedIn() {
        return this.id != null;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }
}
