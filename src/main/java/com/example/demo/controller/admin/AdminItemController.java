package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.Stocks;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StocksRepository;

@Controller
public class AdminItemController {

	@Autowired
	StocksRepository stockRepository;

	@Autowired 
	ItemRepository itemRepository;

	@Autowired 
	CategoryRepository categoryRepository;

	@GetMapping("/admin/adminItems")
	public String index(Model model) {

		List<Item> itemList = itemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("itemList", itemList);

		List<Stocks> stockList = stockRepository.findAll();
		Map<Integer, Integer> stockMap = new HashMap<>();
		for (Stocks stock : stockList) {

			stockMap.put(stock.getItemsId(), stock.getQuantity());
		}
		model.addAttribute("stockMap", stockMap);

		List<Category> categoryList = categoryRepository.findAll();
		Map<Integer, String> categoryNameMap = new HashMap<>();
		for (Category category : categoryList) {
			categoryNameMap.put(category.getId(), category.getName());
		}
		model.addAttribute("categoryNameMap", categoryNameMap);
		return "admin/adminItems";
	}

	@GetMapping("/admin/items/add")
	public String create(Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		
		return "/admin/adminAddItem";
	}

	@PostMapping("/admin/items/add")
	public String store(
	        @RequestParam(name = "categoryId") Integer categoryId, 
	        @RequestParam(name = "name") String name,             
	        @RequestParam(name = "price") Integer price,           
	        @RequestParam(name = "initialStock") Integer initialStock, 
	        @RequestParam(name = "detail", required = false) String detail,
	        @RequestParam(name = "img", required = false) String img      
	) {
	    
	    Item item = new Item();
	    item.setCategoryId(categoryId);
	    item.setName(name);
	    item.setPrice(price);
	    item.setDetail(detail); 
	    item.setImg(img);       
	    
	    Item savedItem = itemRepository.save(item); 

	    
	    Stocks stock = new Stocks();
	    
	    stock.setItemsId(savedItem.getId()); 
	    stock.setQuantity(initialStock);   

	   
	    stockRepository.save(stock);

	   
	    return "redirect:/admin/adminItems";
	}

	@GetMapping("/admin/items/{id}/edit")
	public String update(
			@PathVariable(name = "id") Integer id,
			Model model) {
		Optional<Item> dbData = itemRepository.findById(id);
		if (dbData.isEmpty()) {
			return "redirect:/admin/items";
		}

		Item item = dbData.get();
		model.addAttribute("item", item);

		return "/admin/adminEditItem";
	}

	@PostMapping("/admin/items/{id}/edit")
	public String update(
			@PathVariable(name = "id") Integer id,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "img", defaultValue = "") String img,
			@RequestParam(name = "detail", defaultValue = "") String detail) {
		Optional<Item> dbData = itemRepository.findById(id);

		if (!dbData.isEmpty()) {
			Item item = dbData.get();

			item.setCategoryId(categoryId);
			item.setName(name);
			item.setPrice(price);
			item.setImg(img);
			item.setDetail(detail);

			itemRepository.save(item);
		}

		return "redirect:/admin/adminItems";
	}

	//削除処理
	@PostMapping("/admin/items/{id}/delete")
	public String delete(
			@PathVariable(name = "id") Integer id) {
		//データの存在チェック
		Optional<Item> dbData = itemRepository.findById(id);
		//データが取得できたら削除を実施
		if (!dbData.isEmpty()) {
			//リポジトリ.deleteById(id);
			itemRepository.deleteById(id);
		}

		 return "redirect:/admin/adminItems";
	}
}
