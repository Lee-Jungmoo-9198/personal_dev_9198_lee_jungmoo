package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Item;

@Component
@SessionScope
public class Cart {

	private List<Item> items = new ArrayList<>();

	public Cart() {
	}

	@Transient
	private Integer stockQuantity;

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public List<Item> getItems() {
		return items;
	}

	public void add(Item item) {

		Integer quantityToAdd = item.getQuantity();
		if (quantityToAdd == null || quantityToAdd < 1) {
			quantityToAdd = 1;
		}

		Optional<Item> existingItemOpt = this.items.stream()
				.filter(cartItem -> cartItem.getId().equals(item.getId()))
				.findFirst();

		if (existingItemOpt.isPresent()) {

			Item existingItem = existingItemOpt.get();
			existingItem.setQuantity(existingItem.getQuantity() + quantityToAdd);
			System.out.println("Cart model: Updated quantity for item " + item.getId() + ". New total: "
					+ existingItem.getQuantity());
		} else {

			this.items.add(item);
			System.out.println("Cart model: Added new item " + item.getId() + " with quantity " + quantityToAdd);
		}
	}

	public void delete(int itemId) {

		for (Item item : items) {

			if (item.getId() == itemId) {
				items.remove(item);
				break;
			}
		}
	}

	public void clear() {

		items = new ArrayList<>();
	}

	public int getTotalPrice() {
		int total = 0;
		for (Item item : items) {
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}

	public void decrementItem(Integer itemId) {
		Item itemToUpdate = null;
		for (Item item : items) {
			if (item.getId() != null && item.getId().equals(itemId)) {
				itemToUpdate = item;
				break;
			}
		}

		if (itemToUpdate != null) {
			if (itemToUpdate.getQuantity() != null && itemToUpdate.getQuantity() > 1) {
				itemToUpdate.setQuantity(itemToUpdate.getQuantity() - 1);
			} else if (itemToUpdate.getQuantity() != null && itemToUpdate.getQuantity() == 1) {

				items.remove(itemToUpdate);
			}

		}

	}

	public void incrementItem(Integer itemId) {

		for (Item item : items) {
			if (item.getId() != null && item.getId().equals(itemId)) {
				if (item.getQuantity() != null) {
					item.setQuantity(item.getQuantity() + 1);
				} else {
					item.setQuantity(1);
				}
				return;
			}
		}
	}
}
