package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class Stocks {
	//	create table public.stocks (
	//			  id serial not null
	//			  , items_id integer
	//			  , quantity integer not null
	//			  , create_date timestamp(6) without time zone
	//			  , update_date timestamp(6) without time zone
	//			  , create_id integer
	//			  , update_id integer
	//			  , delete_flg character(1)
	//			  , primary key (id)
	//			);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "items_id")
	private Integer itemsId;

	private Integer quantity;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "update_date")
	private LocalDate updateDate;

	@Column(name = "create_id")
	private LocalDate createId;

	@Column(name = "update_id")
	private LocalDate updateId;

	@Column(name = "delete_flg")
	private char deleteFlg;

	//デフォルトコンストラクタ
	public Stocks() {
	}

	//コンストラクタ(主キー除く)
	public Stocks(Integer itemsId, Integer quantity, LocalDate createDate, LocalDate updateDate, LocalDate createId,
			LocalDate updateId, char deleteFlg) {
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

	public LocalDate getCreateId() {
		return createId;
	}

	public void setCreateId(LocalDate createId) {
		this.createId = createId;
	}

	public LocalDate getUpdateId() {
		return updateId;
	}

	public void setUpdateId(LocalDate updateId) {
		this.updateId = updateId;
	}

	public char getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(char deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
