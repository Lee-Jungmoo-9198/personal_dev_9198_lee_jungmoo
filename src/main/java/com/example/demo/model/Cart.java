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

	public void add(Item newItem) {
	    Item existsItem = null;

	    for (Item item : items) {
	        if (item.getId() != null && newItem.getId() != null && item.getId().equals(newItem.getId())) {
	            existsItem = item;
	            break;
	        }
	    }

	    if (existsItem == null) {
	    	if (newItem.getQuantity() <= 0) {
	             newItem.setQuantity(1);
	        }
	        items.add(newItem);
	    } else {
	        existsItem.setQuantity(existsItem.getQuantity() + newItem.getQuantity());
	    }
	}
	
	public void delete(int itemId) {
		//現在のカートの商品から同じIDを探す
		for (Item item : items) {
			//対象の商品が見つかったら削除
			if (item.getId() == itemId) {
				items.remove(item);
				break;
			}
		}
	}
	//カートをクリア
		public void clear() {
			//itemsを空にする
			items = new ArrayList<>();
		}

		public int getTotalPrice() {
			int total = 0;
			for (Item item : items) {
				total += item.getPrice() * item.getQuantity();
			}
			return total;
		}
}
