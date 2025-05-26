package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_types")
public class PaymentType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "create_date")

	private LocalDate createDate;
	@Column(name = "update_date")

	private LocalDate updateDate;

	@Column(name = "create_id")
	private Integer createId;

	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "delete_flg")
	private Character deleteFlg;

	public PaymentType() {
	}

	public PaymentType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public PaymentType(String name) {
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

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

}
