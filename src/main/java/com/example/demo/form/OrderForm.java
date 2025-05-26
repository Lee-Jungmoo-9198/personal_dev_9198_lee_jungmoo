package com.example.demo.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
public class OrderForm {
	
	private String name;
	private String postalCode;
	private Integer prefectureId;
	private String street;
	private String building;
	private String tel;
	private String email;
	private String email2;
	
	
	

}
