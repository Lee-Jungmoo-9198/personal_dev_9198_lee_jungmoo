package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Address;
import com.example.demo.entity.CreditCard;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.PaymentType;
import com.example.demo.entity.Prefectures;
import com.example.demo.model.Cart;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentTypeRepository;
import com.example.demo.repository.PrefecturesRepository;

@Controller
public class OrderController {

	@Autowired
	Cart cart;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	PrefecturesRepository prefecturesRepository;
	
	@Autowired
	CreditCardRepository creditCardRepository;
	
	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	@GetMapping("/order")
	public String order(Model model, HttpSession session) {
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		model.addAttribute("prefecturesList", prefecturesList);
		model.addAttribute("cart", cart);
		if (cart.getItems().isEmpty()) {
			return "redirect:/cart";
		}

		Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");

		if (loggedInAccount != null) {
			Optional<Account> accountOpt = accountRepository.findById(loggedInAccount.getId());

			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				model.addAttribute("account", account);

				if (account.getAddressId() != null) {
					return "redirect:/order/info";
				}
			}
		}

		return "guestInput";
	}
	
	@PostMapping("/order")
	public String processOrder(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") String prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building", required = false) String building,
			HttpSession session) {
		
		// 顧客情報をセッションに保存
		session.setAttribute("customerName", name);
		session.setAttribute("customerTel", tel);
		session.setAttribute("customerEmail", email);
		session.setAttribute("customerPostalCode", postalCode);
		session.setAttribute("customerPrefectureId", prefectureId);
		session.setAttribute("customerStreet", street);
		session.setAttribute("customerBuilding", building != null ? building : "");
		
		return "redirect:/order/info";
	}
	
	@GetMapping("/order/info")
	public String payInfo(Model model, HttpSession session) {
	    // 支払い方法の選択肢を設定
	    List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
	    
	    if (paymentTypes.isEmpty()) {
	        // デフォルトの支払い方法を設定（支払い方法がテーブルに登録されていない場合）
	        paymentTypes = new ArrayList<>();
	        paymentTypes.add(new PaymentType(1, "代金引換", "cod"));
	        paymentTypes.add(new PaymentType(2, "クレジットカード", "credit"));
	        paymentTypes.add(new PaymentType(3, "新しいカードで支払う", "new"));
	    }
	    
	    // セッションからユーザー情報を取得
	    Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
	    
	    // ログインユーザーの場合、保存済みカード情報を取得
	    if (loggedInAccount != null) {
	        List<CreditCard> savedCards = creditCardRepository.findByAccountId(loggedInAccount.getId());
	        if (!savedCards.isEmpty()) {
	            // 最初のカードを表示用に選択
	            model.addAttribute("savedCard", savedCards.get(0));
	        }
	    }
	    
	    model.addAttribute("paymentTypes", paymentTypes);
	    model.addAttribute("cart", cart);
	    model.addAttribute("totalPrice", cart.getTotalPrice());
	    
	    return "paymentSelect";
	}

	@PostMapping("/order/info")
	public String orderInfo(HttpServletRequest request, Model model, HttpSession session) {
        // Get all parameters from the request for debugging
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder debugInfo = new StringBuilder("Received parameters:\n");
        
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            debugInfo.append(key).append(": ");
            
            if (values != null && values.length > 0) {
                for (String value : values) {
                    debugInfo.append(value).append(", ");
                }
            } else {
                debugInfo.append("null");
            }
            debugInfo.append("\n");
        }
        
        // Check for specific parameters
        String paymentMethod = request.getParameter("paymentMethod");
        String cardNumber = request.getParameter("cardNumber");
        String expirationMm = request.getParameter("expirationMm");
        String expirationYy = request.getParameter("expirationYy");
        String cvc = request.getParameter("cvc");
        String name = request.getParameter("name");
        String savedCreditCardIdStr = request.getParameter("savedCreditCardId");
        Integer savedCreditCardId = null;
        
        if (savedCreditCardIdStr != null && !savedCreditCardIdStr.isEmpty()) {
            try {
                savedCreditCardId = Integer.parseInt(savedCreditCardIdStr);
            } catch (NumberFormatException e) {
                // Handle parsing error
            }
        }
        
        // Fall back to paymentMethodChoice if paymentMethod is not found
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            paymentMethod = request.getParameter("paymentMethodChoice");
            
            if (paymentMethod == null || paymentMethod.isEmpty()) {
                // Default to COD if no payment method found
                paymentMethod = "代金引換";
            }
        }
        
        // カート内容をモデルに追加
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getTotalPrice());
        
        // 支払い情報をセッションに保存
        session.setAttribute("paymentMethod", paymentMethod);
        model.addAttribute("paymentMethod", paymentMethod);
        
        // Add debug info to model
        model.addAttribute("debugInfo", debugInfo.toString());
        
        // 新しいカードの場合はカード情報を保存
        if ("クレジットカード".equals(paymentMethod) && cardNumber != null && !cardNumber.isEmpty()) {
            Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
            Integer accountId = loggedInAccount != null ? loggedInAccount.getId() : null;
            
            CreditCard creditCard = new CreditCard(accountId, cardNumber, expirationYy, expirationMm, name, cvc);
            creditCardRepository.save(creditCard);
            session.setAttribute("creditCardId", creditCard.getId());
            model.addAttribute("creditCardId", creditCard.getId());
        } else if ("クレジットカード".equals(paymentMethod) && savedCreditCardId != null) {
            // 保存済みカードを使う場合
            session.setAttribute("creditCardId", savedCreditCardId);
            model.addAttribute("creditCardId", savedCreditCardId);
        } else if ("新しいカードで支払う".equals(paymentMethod) && cardNumber != null && !cardNumber.isEmpty()) {
            Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
            Integer accountId = loggedInAccount != null ? loggedInAccount.getId() : null;
            
            CreditCard creditCard = new CreditCard(accountId, cardNumber, expirationYy, expirationMm, name, cvc);
            creditCardRepository.save(creditCard);
            session.setAttribute("creditCardId", creditCard.getId());
            model.addAttribute("creditCardId", creditCard.getId());
        }
        
        // ログインアカウント情報
        Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
        model.addAttribute("loggedInAccount", loggedInAccount);
        
        // ゲスト用の顧客情報をセッションから取得
        String customerName = (String) session.getAttribute("customerName");
        String customerTel = (String) session.getAttribute("customerTel");
        String customerEmail = (String) session.getAttribute("customerEmail");
        String customerPostalCode = (String) session.getAttribute("customerPostalCode");
        String customerPrefectureId = (String) session.getAttribute("customerPrefectureId");
        String customerStreet = (String) session.getAttribute("customerStreet");
        String customerBuilding = (String) session.getAttribute("customerBuilding");
        
        // 顧客情報をモデルに追加
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerTel", customerTel);
        model.addAttribute("customerEmail", customerEmail);
        model.addAttribute("customerPostalCode", customerPostalCode);
        model.addAttribute("customerPrefectureId", customerPrefectureId);
        model.addAttribute("customerStreet", customerStreet);
        model.addAttribute("customerBuilding", customerBuilding);
        
        return "orderConfirm";
	}

	@PostMapping("/order/complete")
	public String orderComplete(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") String prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building", required = false) String building,
			@RequestParam(name = "paymentMethod", required = false) String paymentMethod,
			@RequestParam(name = "creditCardId", required = false) Integer creditCardId,
			Model model,
			HttpSession session) {

		//お客様情報をDBに格納
		Account tempAccountInfo = new Account(name, tel, email);
		
		// prefectureIdがStringで渡されるため、適切に変換する必要がある
		Integer prefectureIdInt;
		try {
		    prefectureIdInt = Integer.parseInt(prefectureId);
		} catch (NumberFormatException e) {
		    // 数値に変換できない場合は適当なデフォルト値を設定（例：1）
		    prefectureIdInt = 1;
		}
		
		Address tempAddressInfo = new Address(postalCode, prefectureIdInt, street, building);
		accountRepository.save(tempAccountInfo);
		addressRepository.save(tempAddressInfo);

		//注文をDBに格納する
		Order order = new Order(
				tempAccountInfo.getId(),
				LocalDate.now(),
				cart.getTotalPrice());
		
		// 支払い方法を設定
		if (paymentMethod == null || paymentMethod.isEmpty()) {
		    // Fallback to session if parameter is not provided
		    paymentMethod = (String) session.getAttribute("paymentMethod");
		    if (paymentMethod == null || paymentMethod.isEmpty()) {
		        // Default if all else fails
		        paymentMethod = "代金引換";
		    }
		}
		
		order.setPaymentMethod(paymentMethod);
		
		// クレジットカードIDがあれば設定
		if (creditCardId != null) {
		    order.setCreditCardId(creditCardId);
		} else {
		    // Try to get from session
		    Integer sessionCreditCardId = (Integer) session.getAttribute("creditCardId");
		    if (sessionCreditCardId != null) {
		        order.setCreditCardId(sessionCreditCardId);
		    }
		}
		
		orderRepository.save(order);

		//注文詳細をDBに格納
		List<Item> itemList = cart.getItems();
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Item item : itemList) {
			orderDetails.add(
					new OrderDetail(
							order.getId(),
							item.getId(),
							item.getQuantity()));

		}
		orderDetailRepository.saveAll(orderDetails);

		//カートをクリアする
		cart.clear();
		
		//支払い情報をセッションからクリア
        session.removeAttribute("paymentMethod");
        session.removeAttribute("creditCardId");
        
        // ゲスト情報をセッションからクリア
        session.removeAttribute("customerName");
        session.removeAttribute("customerTel");
        session.removeAttribute("customerEmail");
        session.removeAttribute("customerPostalCode");
        session.removeAttribute("customerPrefectureId");
        session.removeAttribute("customerStreet");
        session.removeAttribute("customerBuilding");

		//画面返却用注文番号を設定
		model.addAttribute("orderNumber", order.getId());

		return "orderComplete";
	}
}