package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "perfecture_id")
	private Integer prefectureId;

	private String street;
	private String building;
	private String type;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "create_id")
	private Integer createId;

	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "delete_flg")
	private Character deleteFlg;

	public Address() {
	}

	public Address(String postalCode, Integer prefectureId, String street, String building) {
		this.postalCode = postalCode;
		this.prefectureId = prefectureId;
		this.street = street;
		this.building = building;
	}

	public Address(String name, String postalCode, Integer prefectureId, String street, String building) {
		this.name = name;
		this.postalCode = postalCode;
		this.prefectureId = prefectureId;
		this.street = street;
		this.building = building;
	}

	public Address(String type, Integer accountId, String postalCode, Integer prefectureId, String street,
			String building) {
		this.type = type;
		this.accountId = accountId;
		this.postalCode = postalCode;
		this.prefectureId = prefectureId;
		this.street = street;
		this.building = building;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Integer getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(Integer prefectureId) {
		this.prefectureId = prefectureId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Character getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Character deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}