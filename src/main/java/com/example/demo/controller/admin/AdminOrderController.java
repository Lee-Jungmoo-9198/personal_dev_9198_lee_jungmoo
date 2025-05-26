package com.example.demo.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Address;
import com.example.demo.entity.CreditCard;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.PaymentType;
import com.example.demo.entity.Prefectures;
import com.example.demo.model.CustomerInfo;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentTypeRepository;
import com.example.demo.repository.PrefecturesRepository;
import com.example.demo.repository.StocksRepository;

@Controller
public class AdminOrderController {

	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	CreditCardRepository creditCardRepository;

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	@Autowired
	PrefecturesRepository prefecturesRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailsRepository;

	@Autowired
	StocksRepository stocksRepository;

	@GetMapping("/admin/orders")
    public String index(Model model) {
        
        List<Order> orderHistory = orderRepository.findAllByOrderByOrderedOnDesc();
        Map<Integer, String> accountEmails = new HashMap<>();

        for (Order order : orderHistory) {
            Integer accountId = order.getAccountsId(); 
            if (accountId != null) {
                Optional<Account> accountOpt = accountRepository.findById(accountId);
                if (accountOpt.isPresent()) {
                    Account account = accountOpt.get();
                    accountEmails.put(accountId, account.getEmail()); 
                } else {
                    accountEmails.put(accountId, "顧客情報無し"); 
                }
            }
        }

        model.addAttribute("orderHistory", orderHistory); 
        model.addAttribute("accountEmails", accountEmails); 

        return "admin/adminOrder";
    }
	
	@GetMapping("/admin/orders/{orderId}")
	public String orderInfo(@PathVariable("orderId") Integer orderId, Model model, 
	                       RedirectAttributes redirectAttributes, CustomerInfo customerInfo) {

	    try {
	        System.out.println("注文情報の取得を開始します。");
	        Optional<Order> orderOpt = orderRepository.findById(orderId);
	        if (orderOpt.isEmpty()) {
	            redirectAttributes.addFlashAttribute("error", "指定された注文が見つかりませんでした。");
	            return "redirect:/admin/orders";
	        }
	        Order order = orderOpt.get();
	        model.addAttribute("order", order);
	        System.out.println("注文が見つかりました: " + order.getId());

	        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrdersId(order.getId());
	        model.addAttribute("orderDetails", orderDetails);
	        System.out.println("注文詳細の件数: " + orderDetails.size());

	        Account account = null;
	        if (order.getAccountsId() != null) {
	            Optional<Account> accountOpt = accountRepository.findById(order.getAccountsId());
	            if (accountOpt.isPresent()) {
	                account = accountOpt.get();
	                
	                if (customerInfo == null) {
	                    customerInfo = new CustomerInfo();
	                }
	                customerInfo.setName(account.getName());
	                customerInfo.setTel(account.getTel());
	                customerInfo.setEmail(account.getEmail());
	                
	                System.out.println("アカウントが見つかりました: " + account.getName());
	            } else {
	                System.err.println("警告: IDが見つかりません: " + order.getAccountsId());
	                customerInfo = new CustomerInfo(); 
	            }
	        } else {
	            customerInfo = new CustomerInfo(); 
	        }
	        
	        model.addAttribute("customerInfo", customerInfo);
	        model.addAttribute("account", account);

	        Address shippingAddress = null;
	        Prefectures prefecture = null;
	        
	        if (order.getAddressId() != null && !order.getAddressId().trim().isEmpty()) {
	            try {
	                Integer addressIntegerId = Integer.valueOf(order.getAddressId().trim());
	                Optional<Address> addressOpt = addressRepository.findById(addressIntegerId);
	                if (addressOpt.isPresent()) {
	                    shippingAddress = addressOpt.get();
	                    
	                    if (shippingAddress.getPrefectureId() != null) {
	                        Optional<Prefectures> prefectureOpt = prefecturesRepository.findById(shippingAddress.getPrefectureId());
	                        if (prefectureOpt.isPresent()) {
	                            prefecture = prefectureOpt.get();
	                        }
	                    }
	                    
	                    customerInfo.setPostalCode(shippingAddress.getPostalCode());
	                    customerInfo.setPrefectureId(shippingAddress.getPrefectureId());
	                    if (prefecture != null) {
	                        customerInfo.setPrefectureName(prefecture.getName());
	                    }
	                    customerInfo.setStreet(shippingAddress.getStreet());
	                    customerInfo.setBuilding(shippingAddress.getBuilding());
	                    
	                    System.out.println("住所が見つかりました: " + shippingAddress.getId());
	                } else {
	                    System.err.println("警告: IDが見つかりません: " + order.getAddressId());
	                }
	            } catch (NumberFormatException e) {
	                System.err.println("addressIdをIntegerに変換する際にエラーが発生しました: " + order.getAddressId() + " - " + e.getMessage());
	            }
	        }
	        
	        model.addAttribute("shippingAddress", shippingAddress);
	        model.addAttribute("prefecture", prefecture);

	        PaymentType payType = null;
	        CreditCard usedCreditCard = null;

	        if (order.getCreditCardId() != null) {
	            Optional<CreditCard> creditCardOpt = creditCardRepository.findById(order.getCreditCardId());
	            if (creditCardOpt.isPresent()) {
	                usedCreditCard = creditCardOpt.get();
	                Optional<PaymentType> payTypeOpt = paymentTypeRepository.findById(2); 
	                if (payTypeOpt.isPresent()) {
	                    payType = payTypeOpt.get();
	                }
	                System.out.println("クレジットカードが見つかりました: " + usedCreditCard.getId());
	            }
	        } else {
	            Optional<PaymentType> payTypeOpt = paymentTypeRepository.findById(1); 
	            if (payTypeOpt.isPresent()) {
	                payType = payTypeOpt.get();
	            }
	        }

	        model.addAttribute("payType", payType);
	        model.addAttribute("usedCreditCard", usedCreditCard);

	        Account loggedInAccount = (Account) session.getAttribute("loggedInAccount");
	        model.addAttribute("loggedInAccount", loggedInAccount);

	        System.out.println("注文ID " + orderId + " の全データ準備が完了しました。");
	        return "admin/adminOrderDetail";
	        
	    } catch (Exception e) {
	        System.err.println("orderInfoメソッドでエラーが発生しました: " + e.getMessage());
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "注文詳細の取得中にエラーが発生しました: " + e.getMessage());
	        return "redirect:/admin/orders";
	    }
	}
}