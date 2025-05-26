package com.example.demo.controller.admin;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.Stocks;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StocksRepository;

@Controller
public class AdminStockController {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	StocksRepository stockRepository;

	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/admin/items/{id}/stock")
	public String showStockDetail(
			@PathVariable(name = "id") Integer id,
			Model model) {

		Optional<Item> dbData = itemRepository.findById(id);
		if (dbData.isEmpty()) {
			return "redirect:/admin/items";
		}
		Item item = dbData.get();
		model.addAttribute("item", item);

		Category itemCategory = null;
		Integer categoryId = item.getCategoryId();

		if (categoryId != null) {
			Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
			if (categoryOpt.isPresent()) {
				itemCategory = categoryOpt.get();
			}
		}
		model.addAttribute("itemCategory", itemCategory);

		Optional<Stocks> stockOpt = stockRepository.findByItemsId(item.getId());
		Integer stockQuantity = 0;
		if (stockOpt.isPresent()) {
			Stocks stock = stockOpt.get();
			stockQuantity = stock.getQuantity();
		}
		model.addAttribute("stockQuantity", stockQuantity);

		return "/admin/adminStock";
	}

	@PostMapping("/items/stock/update")
	public String updateStock(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("additionalQuantity") Integer additionalQuantity,
			RedirectAttributes redirectAttributes,
			Model model) {

		Optional<Stocks> stockOpt = stockRepository.findByItemsId(itemId);

		if (stockOpt.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "指定された商品の在庫情報が見つかりませんでした。");
			return "redirect:/admin/items";
		}

		Stocks stock = stockOpt.get();
		Integer currentQuantity = stock.getQuantity();

		Integer newQuantity = currentQuantity + additionalQuantity;

		if (newQuantity < 0) {
			redirectAttributes.addFlashAttribute("warning", "在庫は0未満にはできません。現在の在庫は" + currentQuantity + "個です。");
			return "redirect:/admin/items/" + itemId + "/stock";
		}

		stock.setQuantity(newQuantity);

		try {

			stockRepository.save(stock);
			
			redirectAttributes.addFlashAttribute("success", "在庫が正常に更新されました。");
		} catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("error", "在庫の更新中にエラーが発生しました。");
			
			return "redirect:/admin/items/" + itemId + "/stock";
		}

		
		return "redirect:/admin/adminItems";
	}

}
