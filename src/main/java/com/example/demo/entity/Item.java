package com.example.demo.entity;

//create table public.items (
//		  id serial not null
//		  , category_id integer
//		  , name text not null
//		  , price integer not null
//		  , detail text
//		  , img text not null
//		  , create_date timestamp(6) without time zone
//		  , update_date timestamp(6) without time zone
//		  , create_id integer
//		  , update_id integer
//		  , delete_flg character(1)
//		  , primary key (id)
//		);


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "category_id")
	private Integer categoryId;
	
	private String name;
	private Integer price;
	private String detail;
	private String img;

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
	
	@Transient
	private Integer quantity;
	
	public Item() {
	}

	public Item(Integer categoryId, String name, Integer price, String detail, String img) {
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.detail = detail;
		this.img = img;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

	
	
}
