package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Item;

@Component
@SessionScope
public class Cart {

	//商品リスト
	private List<Item> items = new ArrayList<>();

	public Cart() {
	}

	//Getter
	public List<Item> getItems() {
		return items;
	}
}
