package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping({"/","items"})
	public String index(
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			Model model
			) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		List<Item> itemList;

		if (categoryId == null || categoryId == 0) {
			itemList = itemRepository.findAll();
		} else {
			List<Item> allItems = itemRepository.findAll();
			itemList = new ArrayList<>();
			for (Item item : allItems) {
				if (item.getCategoryId() != null && item.getCategoryId().equals(categoryId)) {
					itemList.add(item);
				}
			}
		}

		model.addAttribute("itemList", itemList);
		model.addAttribute("categoryId", categoryId);

		return "items";
	}
}
