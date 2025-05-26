package com.example.demo.controller.admin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Admins;
import com.example.demo.model.AdminS;
import com.example.demo.repository.AdminsRepository;


@Controller
public class AdminAccountController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	AdminsRepository adminsRepository;

	@Autowired
	AdminS adminS;

	@GetMapping("/admin/login")
	public String index() {

		if (adminS.isLoggedIn()) {
			System.out.println("Admin is already logged in. Redirecting to /admin/adminItems.");
			return "redirect:/admin/adminItems";
		}

		System.out.println("Admin is not logged in. Showing admin login page.");
		return "admin/adminLogin";
	}

	@PostMapping("/admin/login")
	public String login(
	        @RequestParam(name = "email") String email,
	        @RequestParam(name = "password") String password,
	        Model model,
	        RedirectAttributes redirectAttributes) {
		
	    System.out.println("Received email: " + email);

	    List<String> errors = new ArrayList<>();

	    Optional<Admins> adminOpt = adminsRepository.findByEmail(email);

	    if (adminOpt.isPresent()) {
	        Admins admin = adminOpt.get();

	        if (admin.getPassword() != null && admin.getPassword().equals(password)) {
	            System.out.println("Password matches for admin: " + email);

	            adminS.login(admin.getId(), admin.getEmail());


	            System.out.println("Admin login successful. Admin ID: " + adminS.getId());

	            return "redirect:/admin/adminItems";
	        } else {
	            System.out.println("Login failed: Incorrect password for email: " + email);
	            errors.add("e-mailアドレスまたはパスワードが間違っています。");
	        }
	    } else {
	        System.out.println("Login failed: Admin not found with email: " + email);
	        errors.add("e-mailアドレスまたはパスワードが間違っています。");
	    }

	    model.addAttribute("errors", errors);
	    return "admin/adminLogin";
	}

    @GetMapping("/admin/logout")
    public String adminLogout() {
        adminS.logout();

        System.out.println("Admin logged out. Redirecting to /admin/login.");
        return "redirect:/admin/login";
    }
}
