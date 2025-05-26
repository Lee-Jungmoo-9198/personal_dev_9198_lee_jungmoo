package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AccountS {
    
    private String name;
    private Integer id; 
    private Integer type;
    private String email;  
    private boolean loggedIn;  
    
    
    public AccountS() {
        this.loggedIn = false;
    }
    
    
    public AccountS(String name, Integer id, Integer type) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.loggedIn = true;
    }
    
    
    public AccountS(String name, Integer id, Integer type, String email) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.email = email;
        this.loggedIn = true;
    }

    
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    
    public void login(String name, Integer id, Integer type, String email) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.email = email;
        this.loggedIn = true;
    }
    
    
    public void logout() {
        this.name = null;
        this.id = null;
        this.type = null;
        this.email = null;
        this.loggedIn = false;
    }
    
    
    public boolean hasValidSession() {
        return this.loggedIn && this.id != null;
    }
    
    
    @Override
    public String toString() {
        return "AccountS{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", email='" + email + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountS accountS = (AccountS) obj;
        return id != null && id.equals(accountS.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}