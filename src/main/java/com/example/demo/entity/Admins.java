package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admins {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String email;
	private String password;

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

	public Integer getCreateId() {
		return createId;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public Character getDeleteFlg() {
		return deleteFlg;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public void setDeleteFlg(Character deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Admins(Integer id, String email, String password, Character deleteFlg) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.deleteFlg = deleteFlg;
	}

	public Admins() {

	}

}
