package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AccountS {

    private Integer accountId;
    private String name;
    private Integer accountType; 
    private String tel;
    private String email;
    private String password;
    
    private boolean isLoggedIn ;

    public AccountS() {
    }

    public AccountS(Integer accountId, String name, Integer accountType, boolean isLoggedIn) {
        this.accountId = accountId;
        this.name = name;
        this.accountType = accountType; 
        this.isLoggedIn = isLoggedIn;
    }

    // Getter and Setter for accountType
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

   
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
}