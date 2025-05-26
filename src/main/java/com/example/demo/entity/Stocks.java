package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class Stocks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "items_id")
	private Integer itemsId;

	private Integer quantity;

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

	public Stocks() {
	}

	public Stocks(Integer itemsId, Integer quantity, LocalDateTime createDate, LocalDateTime updateDate,
			Integer createId,
			Integer updateId, Character deleteFlg) {
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
