package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession; // HttpSession 사용 시 필요

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account; // Account 엔티티 import 필요
import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.Stocks;
import com.example.demo.repository.AccountRepository; // AccountRepository 주입 필요
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StocksRepository;

@Controller
public class ItemController {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

    @Autowired
    StocksRepository stockRepository;

    @Autowired // <-- HttpSession 주입
    HttpSession session;

    @Autowired // <-- AccountRepository 주입
    AccountRepository accountRepository;


	// --- 기존 상품 목록 조회 메서드 (@GetMapping({"/","items"})) ---


    @GetMapping("/items/{itemId}")
    public String showItemDetail(@PathVariable("itemId") Integer itemId,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        Integer loggedInAccountId = (Integer) session.getAttribute("accountId");
        Account loggedInAccount = null;
        if (loggedInAccountId != null) {
            Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);
            if (accountOpt.isPresent()) {
                loggedInAccount = accountOpt.get();
            } else {
                System.err.println("Error: Account ID from session not found in DB: " + loggedInAccountId);
                 session.invalidate();
            }
        }
        model.addAttribute("account", loggedInAccount);


        Optional<Item> itemOpt = itemRepository.findById(itemId);

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            model.addAttribute("item", item);

            Optional<Stocks> stockOpt = stockRepository.findByItemsId(item.getId());

            Integer stockQuantity = 0;
            if (stockOpt.isPresent()) {
                Stocks stock = stockOpt.get();
                stockQuantity = stock.getQuantity();
            }
            model.addAttribute("stockQuantity", stockQuantity);


            return "itemDetail";
        } else {
            System.err.println("Error: Item with ID " + itemId + " not found.");
            redirectAttributes.addFlashAttribute("error", "指定された商品が見つかりませんでした。");
            return "redirect:/items";
        }
    }


    @GetMapping({"/","items"})
    public String index(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            Model model
            ) {
        Integer loggedInAccountId = (Integer) session.getAttribute("accountId");
        Account loggedInAccount = null;
        if (loggedInAccountId != null) {
            Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);
            if (accountOpt.isPresent()) {
                loggedInAccount = accountOpt.get();
            } else {
                 System.err.println("Error: Account ID from session not found in DB: " + loggedInAccountId);
                 session.invalidate();
            }
        }
        model.addAttribute("account", loggedInAccount);

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
