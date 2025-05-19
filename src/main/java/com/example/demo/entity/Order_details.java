package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class Order_details {
	//	create table public.order_details (
	//			  id serial not null
	//			  , orders_id integer
	//			  , items_id integer
	//			  , quantity integer not null
	//			  , create_date timestamp(6) without time zone not null
	//			  , update_date timestamp(6) without time zone not null
	//			  , create_id integer
	//			  , update_id integer
	//			  , delete_flg character(1)
	//			  , primary key (id)
	//			);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "orders_id")
	private Integer ordersId;

	@Column(name = "items_id")
	private Integer itemsId;

	private Integer quantity;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "update_date")
	private LocalDate updateDate;

	@Column(name = "create_id")
	private Integer createId;

	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "delete_flg")
	private char deleteFlg;

	//デフォルトコンストラクタ
	public Order_details() {
	}

	//コンストラクタ(主キー除く)
	public Order_details(Integer ordersId, Integer itemsId, Integer quantity, LocalDate createDate,
			LocalDate updateDate, Integer createId, Integer updateId, char deleteFlg) {
		this.ordersId = ordersId;
		this.itemsId = itemsId;
		this.quantity = quantity;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.createId = createId;
		this.updateId = updateId;
		this.deleteFlg = deleteFlg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}

	public Integer getItemsId() {
		return itemsId;
	}

	public void setItemsId(Integer itemsId) {
		this.itemsId = itemsId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
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

	public char getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(char deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
