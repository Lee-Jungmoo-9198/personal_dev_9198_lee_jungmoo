package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(name="postal_code")
	private String postalCode;
	@Column(name="perfecture_id")
	private Integer perfectureId;
	private String street;
	private String building;
	@Column(name="create_date")
	private Timestamp createDate;
	@Column(name="update_date")
	private Timestamp updateDate;
	@Column(name="create_id")
	private Integer createId;
	@Column(name="update_id")
	private Integer updateId;
	@Column(name="delete_flg")
	private Character deleteFlg;
	
	public Address() {
	}

	public Address(String name, String postalCode, Integer perfectureId, String street, String building) {
		this.name = name;
		this.postalCode = postalCode;
		this.perfectureId = perfectureId;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Integer getPerfectureId() {
		return perfectureId;
	}

	public void setPerfectureId(Integer perfectureId) {
		this.perfectureId = perfectureId;
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

	
}
