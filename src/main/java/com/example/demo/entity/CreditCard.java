package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account_id")
	private Integer accountId;
	private String number;
	@Column(name = "expiration_yy")
	private String expirationYy;
	@Column(name = "expiration_mm")
	private String expirationMm;
	private String name;
	private String cvc;

	@Column(name = "create_date")
	private Timestamp createDate;
	@Column(name = "update_date")
	private Timestamp updateDate;
	@Column(name = "create_id")
	private Integer createId;
	@Column(name = "update_id")
	private Integer updateId;
	@Column(name = "delete_flg")
	private Character deleteFlg;

	public CreditCard() {

	}

	public CreditCard(Integer accountId, String number, String expirationYy, String expirationMm, String name,
			String cvc) {
		this.accountId = accountId;
		this.number = number;
		this.expirationYy = expirationYy;
		this.expirationMm = expirationMm;
		this.name = name;
		this.cvc = cvc;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpirationYy() {
		return expirationYy;
	}

	public void setExpirationYy(String expirationYy) {
		this.expirationYy = expirationYy;
	}

	public String getExpirationMm() {
		return expirationMm;
	}

	public void setExpirationMm(String expirationMm) {
		this.expirationMm = expirationMm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
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

	public Character getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Character deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Integer getId() {
		return id;
	}

}
