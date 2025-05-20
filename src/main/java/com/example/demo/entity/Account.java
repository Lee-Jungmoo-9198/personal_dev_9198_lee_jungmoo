package com.example.demo.entity;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@Column(name ="account_id")
		private Integer accountId;
		private String name;
		private String tel;
		private String email;
		private String password;
		private Integer type;
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
		
		
		public Account() {
		}

		public Account(Integer accountId,String name, String tel, String email, String password, Integer type, Character deleteFlg) {
			this.accountId= accountId;
			this.name = name;
			this.tel = tel;
			this.email = email;
			this.password = password;
			this.type = type;
			this.deleteFlg = deleteFlg;
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

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
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

		public Integer getAccountId() {
			return accountId;
		}

		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
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

