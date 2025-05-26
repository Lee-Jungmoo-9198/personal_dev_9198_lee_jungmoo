package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Order;
import com.example.demo.model.AccountS;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PrefecturesRepository;

@Controller
public class DeleteAccountController {

	@Autowired
	AccountS accountS;

	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PrefecturesRepository prefecturesRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	CreditCardRepository creditCardRepository;

	// アカウント削除確認画面を表示
	@GetMapping("/mypage/deleteAccount")
	public String deleteAccount(Model model) {
        System.out.println("--- /mypage/deleteAccount (GET) 메서드 시작 ---");
		// ログインチェック
		if (!isLoggedIn()) {
             System.out.println("ログインされていません。/login へリダイレクト。");
			return "redirect:/login";
		}
         System.out.println("ログイン確認完了。 AccountS ID: " + accountS.getId());

		// 
		Optional<Account> accountOpt = accountRepository.findById(accountS.getId());
		if (accountOpt.isPresent()) {
             Account loggedInAccount = accountOpt.get(); 
             System.out.println("Account ID " + accountS.getId() + " を DB から 조회 성공. 名前: " + loggedInAccount.getName());
			model.addAttribute("account", loggedInAccount); 
             

		} else {
             System.err.println("ログイン中の Account ID (" + accountS.getId() + ") に該当する Account が DB に見つかりませんでした。");
            
        }
         System.out.println("--- /mypage/deleteAccount (GET) 메서드 완료. deleteAccount 반환 ---");
		return "deleteAccount"; 
	}


	@PostMapping("/mypage/deleteAccount")
	public String deleteAccount(
			@RequestParam(name = "confirmWithdrawal", required = false) String confirmWithdrawal,
			Model model) {

		
		if (!isLoggedIn()) {
			return "redirect:/login";
		}

	
		if ("on".equals(confirmWithdrawal)) {
			Integer accountId = accountS.getId();

		
			List<Order> orders = orderRepository.findByAccountsId(accountId);
			for (Order order : orders) {
				orderDetailRepository.deleteByOrdersId(order.getId()); // ordersId로 수정
			}

	
			orderRepository.deleteByAccountsId(accountId);

		
			creditCardRepository.deleteByAccountId(accountId);

	
			addressRepository.deleteByAccountId(accountId);

		
			accountRepository.deleteById(accountId);

		
			session.invalidate();

			return "redirect:/"; 
		}

		return "redirect:/mypage/deleteAccount"; 
	}

	private boolean isLoggedIn() {

		if (accountS != null && accountS.hasValidSession()) {
			return true;
		}

		
		Object sessionAccountId = session.getAttribute("accountId");
		Object sessionAccountS = session.getAttribute("accountS");

		if (sessionAccountId != null && sessionAccountS instanceof AccountS) {
		
			accountS = (AccountS) sessionAccountS;
			return accountS.hasValidSession();
		}

		return false;
	}
}