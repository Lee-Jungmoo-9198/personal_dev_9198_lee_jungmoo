package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//create table public.categories (
//		  id serial not null
//		  , name text not null
//		  , create_date timestamp(6) without time zone
//		  , update_date timestamp(6) without time zone
//		  , create_id integer
//		  , update_id integer
//		  , delete_flg character(1)
//		  , primary key (id)
//		);

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
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
	
	
	public Category() {
	}


	public Category(String name) {
		this.name = name;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
