package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	Cart cart;

	@Autowired
	ItemRepository itemRepository;
	
	@GetMapping("/cart")
	public String index(Model model) {
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam(name = "itemId") int itemId) {
		
		Item item = itemRepository.findById(itemId).orElseThrow(
	            () -> new IllegalArgumentException("Invalid item Id:" + itemId));
		
		item.setQuantity(1);
		
		cart.add(item);
		
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/delete")
	public String deleteCart(
			@RequestParam(name="itemId")int itemId
	) {
		//削除処理
		cart.delete(itemId);
		
		//「/cart」にリダイレクト
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/increment")
	public String incrementQuantity(@RequestParam(name="itemId") int itemId) {
		// カートにある商品の数量を1増やす
		for (Item item : cart.getItems()) {
			if (item.getId() == itemId) {
				item.setQuantity(item.getQuantity() + 1);
				break;
			}
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/decrement")
	public String decrementQuantity(@RequestParam(name="itemId") int itemId) {
		// カートにある商品の数量を1減らす
		for (Item item : cart.getItems()) {
			if (item.getId() == itemId && item.getQuantity() > 1) {
				item.setQuantity(item.getQuantity() - 1);
				break;
			}
		}
		return "redirect:/cart";
	}
}