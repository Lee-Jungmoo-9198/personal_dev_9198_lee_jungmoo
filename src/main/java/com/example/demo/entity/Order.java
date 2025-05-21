package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "account_id")
    private Integer accountId;
    
    @Column(name = "order_date")
    private LocalDate orderDate;
    
    @Column(name = "total_price")
    private int totalPrice;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @Column(name = "credit_card_id")
    private Integer creditCardId;
    
    public Order() {
    }
    
    public Order(Integer accountId, LocalDate orderDate, int totalPrice) {
        this.accountId = accountId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public int getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public Integer getCreditCardId() {
        return creditCardId;
    }
    
    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }
}