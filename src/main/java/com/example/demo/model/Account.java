package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Account {

    private Integer accountId;
    private String name;
    private Integer accountType; 
    private boolean isLoggedIn = false;

    public Account() {
	}

	public Account(Integer accountId, String name, Integer accountType, boolean isLoggedIn) {
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

    // 기존 Getter and Setter ...
    public Integer getaccountId() {
        return accountId;
    }

    public void setaccountId(Integer accountId) {
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
}
