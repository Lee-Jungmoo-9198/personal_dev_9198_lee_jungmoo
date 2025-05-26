package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "orders_id")
    private Integer ordersId;

    
    @ManyToOne 
    @JoinColumn(name = "items_id", insertable = false, updatable = false) 
    private Item item; 

   
    @Column(name = "items_id")
    private Integer itemsId;


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "create_id")
    private Integer createId;

    @Column(name = "update_id")
    private Integer updateId;

    @Column(name = "delete_flg")
    private String deleteFlg;


    
    public OrderDetails() {}

    
    public OrderDetails(Integer ordersId, Item item, Integer quantity, LocalDateTime createDate, LocalDateTime updateDate, Integer createId, Integer updateId, String deleteFlg) {
        this.ordersId = ordersId;
        this.item = item;
        this.quantity = quantity;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.createId = createId;
        this.updateId = updateId;
        this.deleteFlg = deleteFlg;
    }

   
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrdersId() { return ordersId; }
    public void setOrdersId(Integer ordersId) { this.ordersId = ordersId; }

    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

  
    public Integer getItemsId() { return itemsId; }
    public void setItemsId(Integer itemsId) { this.itemsId = itemsId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreateDate() { return createDate; } 
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; } 

    public Integer getCreateId() { return createId; }
    public void setCreateId(Integer createId) { this.createId = createId; }

    public Integer getUpdateId() { return updateId; }
    public void setUpdateId(Integer updateId) { this.updateId = updateId; }

    public String getDeleteFlg() { return deleteFlg; }
    public void setDeleteFlg(String deleteFlg) { this.deleteFlg = deleteFlg; }
}