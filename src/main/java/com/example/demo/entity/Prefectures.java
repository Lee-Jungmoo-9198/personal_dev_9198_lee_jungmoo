package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//create table public.prefectures (
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
@Table(name = "prefectures")
public class Prefectures {
	
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

	

	public Prefectures() {
	}

	

	public Prefectures(String name) {
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

}
