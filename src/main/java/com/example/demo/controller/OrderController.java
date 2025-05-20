package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Account;
import com.example.demo.entity.Prefectures;
import com.example.demo.model.AccountS;
import com.example.demo.model.Cart;
import com.example.demo.repository.PrefecturesRepository;


@Controller
public class OrderController {

	@Autowired(required = false)
	AccountS accountS;

    @Autowired
    Cart cart;

    @Autowired
    PrefecturesRepository prefecturesRepository;


	@GetMapping("/order")
	public String index(Account loggedInAccount, Model model) {

		if(loggedInAccount == null) {
            List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		    model.addAttribute("prefecturesList", prefecturesList);

			return "guestInput";
		} else {
            model.addAttribute("cart", cart);
            model.addAttribute("loggedInAccount", loggedInAccount);

			return "orderConfirm";
		}
	}
	
	@GetMapping("/order/info")
	public String orderInfo() {
		return "addPayment";
	}
}
