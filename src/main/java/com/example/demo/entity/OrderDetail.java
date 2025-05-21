package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {
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
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "create_id")
	private Integer createId;

	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "delete_flg")
	private char deleteFlg;

	//デフォルトコンストラクタ
	public OrderDetail() {
		
	}
	public OrderDetail(Integer ordersId, Integer itemsId, Integer quantity) {
		
		this.ordersId = ordersId;
		this.itemsId = itemsId;
		this.quantity = quantity;
	}

	//コンストラクタ(主キー除く)
	public OrderDetail(Integer ordersId, Integer itemsId, Integer quantity, Timestamp createDate,
			Timestamp updateDate, Integer createId, Integer updateId, char deleteFlg) {
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

	public char getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(char deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
