package com.example.demo.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditForm {
	
	private String number;
	private String yy;
	private String mm;
	private String cardName;
	private String cvc;
	private Integer type;
	private Integer button;

}
