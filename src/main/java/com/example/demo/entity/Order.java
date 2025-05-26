package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "accounts_id")
	private Integer accountsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ordered_on")

	private Date orderedOn;

	@Column(name = "total_price")
	private Integer totalPrice;

	@Column(name = "address_id")
	private String addressId;

	@Column(name = "credit_card_id")
	private Integer creditCardId;

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

	public Order() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountsId() {
		return accountsId;
	}

	public void setAccountsId(Integer accountsId) {
		this.accountsId = accountsId;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public void setOrderedOn(Date orderedOn) {
		this.orderedOn = orderedOn;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Integer getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(Integer creditCardId) {
		this.creditCardId = creditCardId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}