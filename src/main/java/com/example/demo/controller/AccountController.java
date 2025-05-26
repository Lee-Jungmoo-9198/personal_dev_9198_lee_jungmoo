package com.example.demo.controller;

import java.util.ArrayList;
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
import com.example.demo.entity.Prefectures;
import com.example.demo.model.AccountS;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PrefecturesRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	
    private AccountS accountS;

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;

	@GetMapping({ "/login", "/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {

		// 기존 세션 정리
		if (accountS != null) {
			accountS.logout();
		}
		session.invalidate();
		
		return "login";
	}

	@PostMapping("login")
	public String login(
	        @RequestParam(name = "email") String email,
	        @RequestParam(name = "password") String password,
	        Model model) {

	    List<String> errors = new ArrayList<>();

	    Optional<Account> accountOpt = accountRepository.findByEmail(email);
	    if (accountOpt.isPresent()) {
	        Account customer = accountOpt.get();

	        if (customer.getPassword().equals(password)) {
	            
	            
	            accountS = new AccountS();
	            Integer customerId = customer.getId();
	            accountS.setId(customerId);
	            accountS.setName(customer.getName());
	            accountS.setType(customer.getType());
	            accountS.setEmail(customer.getEmail());
	            accountS.setLoggedIn(true);
	            
	            
	            session.setAttribute("accountS", accountS);
	            session.setAttribute("accountId", customerId);
	            
	            System.out.println("로그인 성공: " + accountS.toString());
	            
	            return "redirect:/items";
	        } else {
	            errors.add("e-mailアドレスとパスワードが一致しませんでした");
	        }
	    } else {
	        errors.add("e-mailアドレスとパスワードが一致しません");
	    }

	    model.addAttribute("errors", errors);
	    return "login";
	}

	@GetMapping("/account/add")
	public String createAccount(Model model) {
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		model.addAttribute("prefecturesList", prefecturesList);
		return "addAccount";
	}

	@PostMapping("/account/add")
	public String addAccount(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "ckEmail") String ckEmail,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "ckPassword") String ckPassword,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") Integer prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building", defaultValue = "") String building,
			Model model) {

		List<String> errors = new ArrayList<>();

		if (name == null || name.trim().isEmpty())
			errors.add("お名前は必須です。");
		if (tel == null || tel.trim().isEmpty())
			errors.add("電話番号は必須です。");
		if (email == null || email.trim().isEmpty()) {
			errors.add("emailは必須です。");
		} else {
			if (ckEmail == null || !email.equals(ckEmail))
				errors.add("確認用emailが一致しません。");
			if (accountRepository.existsByEmail(email.trim()))
				errors.add("このメールアドレスは既に使用されています。");
		}
		if (password == null || password.trim().isEmpty()) {
			errors.add("パスワードは必須です。");
		} else {
			if (ckPassword == null || !password.equals(ckPassword))
				errors.add("確認用パスワードが一致しません。");
		}

		if (postalCode == null || postalCode.trim().isEmpty())
			errors.add("郵便番号は必須です。");
		if (prefectureId == null)
			errors.add("都道府県は必須です。");
		if (street == null || street.trim().isEmpty())
			errors.add("市区町村以降の住所は必須です。");

		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			model.addAttribute("name", name);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("ckEmail", ckEmail);
			model.addAttribute("postalCode", postalCode);
			model.addAttribute("prefectureId", prefectureId);
			model.addAttribute("street", street);
			model.addAttribute("building", building);
			
			
			List<Prefectures> prefecturesList = prefecturesRepository.findAll();
			model.addAttribute("prefecturesList", prefecturesList);

			return "addAccount";
		}

		Account newAccountEntity = new Account();
		newAccountEntity.setName(name.trim());
		newAccountEntity.setTel(tel.trim());
		newAccountEntity.setEmail(email.trim());
		newAccountEntity.setPassword(password.trim());
		newAccountEntity.setType(1);

		accountRepository.save(newAccountEntity);

		return "redirect:/login?registerSuccess";
	}
	
	
}