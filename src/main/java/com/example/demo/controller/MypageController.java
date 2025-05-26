package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Address;
import com.example.demo.entity.CreditCard;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.PaymentType;
import com.example.demo.entity.Prefectures;
import com.example.demo.model.AccountS;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentTypeRepository;
import com.example.demo.repository.PrefecturesRepository;

@Controller
public class MypageController {

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

	@Autowired
	PaymentTypeRepository paymentTypeRepository;
	
	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/mypage")
	public String index(Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (mypage index).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);

		List<Address> addressList = addressRepository.findByAccountId(loggedInAccountId);

		List<Order> orderHistory = orderRepository.findByAccountsIdOrderByOrderedOnDesc(loggedInAccountId);

		List<CreditCard> creditCards = creditCardRepository.findByAccountId(loggedInAccountId);

		if (accountOpt.isPresent()) {
			Account acc = accountOpt.get();
			model.addAttribute("account", acc);

			model.addAttribute("addressList", addressList);

			model.addAttribute("orderHistory", orderHistory);

			model.addAttribute("creditCards", creditCards);

			return "mypage";
		} else {

			this.session.invalidate();
			return "redirect:/login";
		}
	}

	@GetMapping("/mypage/editEmail")
	public String editEmail(Model model) {

		if (accountS == null || !accountS.hasValidSession()) {

			Object sessionAccountId = session.getAttribute("accountId");
			if (sessionAccountId == null) {
				return "redirect:/login";
			}

			if (accountS == null) {
				accountS = new AccountS();
			}
			accountS.setId((Integer) sessionAccountId);
			accountS.setLoggedIn(true);

			Object sessionAccountS = session.getAttribute("accountS");
			if (sessionAccountS instanceof AccountS) {
				AccountS savedAccountS = (AccountS) sessionAccountS;
				accountS.setName(savedAccountS.getName());
				accountS.setEmail(savedAccountS.getEmail());
				accountS.setType(savedAccountS.getType());
			}
		}

		Optional<Account> accountOpt = accountRepository.findById(accountS.getId());
		if (accountOpt.isPresent()) {
			Account acc = accountOpt.get();
			model.addAttribute("account", acc);
			return "editEmail";
		} else {
			return "redirect:/mypage";
		}
	}

	@PostMapping("/mypage/editEmail")
	public String editEmail(
			@RequestParam(name = "newEmail") String newEmail,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (editEmail).");
			this.session.invalidate();
			return "redirect:/login";
		}

		if (newEmail == null || newEmail.trim().isEmpty()) {
			System.err.println("Error: New email address is empty.");
			redirectAttributes.addFlashAttribute("error", "新しいメールアドレスを入力してください。");
			return "redirect:/mypage/editEmail";
		}

		Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);

		if (accountOpt.isPresent()) {
			Account accToUpdate = accountOpt.get();
			String currentEmail = accToUpdate.getEmail();

			if (newEmail.equals(currentEmail)) {
				System.out.println("New email is the same as the current email. No update needed.");
				redirectAttributes.addFlashAttribute("message", "メールアドレスが変更されていません（現在と同じです）。");
				return "redirect:/mypage";
			}

			Optional<Account> existingAccountWithNewEmailOpt = accountRepository.findByEmail(newEmail);

			if (existingAccountWithNewEmailOpt.isPresent()) {
				Account existingAccount = existingAccountWithNewEmailOpt.get();

				if (!existingAccount.getId().equals(loggedInAccountId)) {
					System.err.println("Error: New email address '" + newEmail
							+ "' is already used by another account (ID: " + existingAccount.getId() + ").");
					redirectAttributes.addFlashAttribute("error", "そのメールアドレスは既に使用されています。");
					return "redirect:/mypage/editEmail";
				}

			}

			accToUpdate.setEmail(newEmail);
			accountRepository.save(accToUpdate);
			System.out.println("Account " + loggedInAccountId + " email updated to '" + newEmail + "' successfully.");
			redirectAttributes.addFlashAttribute("message", "メールアドレスが更新されました。");

		} else {

			System.err.println(
					"Error: Account not found in DB for ID from session: " + loggedInAccountId + " during editEmail.");
			this.session.invalidate();
			return "redirect:/login";
		}

		return "redirect:/mypage";
	}

	@GetMapping("/mypage/editPassword")
	public String editPassword() {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}
		return "editPassword";
	}

	@PostMapping("/mypage/editPassword")
	public String editPassword(
			@RequestParam(name = "password") String password,
			@RequestParam(name = "newPassword") String newPassword,
			Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Optional<Account> dbData = accountRepository.findById(accountS.getId());
		if (dbData.isPresent()) {
			Account acc = dbData.get();

			acc.setPassword(newPassword);
			accountRepository.save(acc);

		}
		return "redirect:/mypage";
	}

	@GetMapping("/mypage/editAddress/{addressId}")
	public String editAddressForm(@PathVariable("addressId") Integer addressId,
			Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (editAddressForm).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<Address> addressOpt = addressRepository.findById(addressId);

		if (addressOpt.isPresent()) {
			Address addressToEdit = addressOpt.get();
			if (addressToEdit.getAccountId() != null && addressToEdit.getAccountId().equals(loggedInAccountId)) {

				model.addAttribute("address", addressToEdit);

				List<Prefectures> prefecturesList = prefecturesRepository.findAll();
				model.addAttribute("prefecturesList", prefecturesList);

				return "editAddress";

			} else {

				System.err.println("Error: Account " + loggedInAccountId + " tried to access edit form for address "
						+ addressId + " which belongs to account " + addressToEdit.getAccountId());

				return "redirect:/mypage";
			}
		} else {

			System.err.println("Error: Address with ID " + addressId + " not found for edit.");

			return "redirect:/mypage";
		}
	}

	@PostMapping("/mypage/updateAddress/{addressId}")
	public String updateAddress(
			@PathVariable("addressId") Integer addressId,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") Integer prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building") String building,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (updateAddress).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<Address> addressOpt = addressRepository.findById(addressId);

		if (addressOpt.isPresent()) {
			Address addrToUpdate = addressOpt.get();
			if (addrToUpdate.getAccountId() != null && addrToUpdate.getAccountId().equals(loggedInAccountId)) {

				addrToUpdate.setName(name);
				addrToUpdate.setPostalCode(postalCode);
				addrToUpdate.setPrefectureId(prefectureId);
				addrToUpdate.setStreet(street);
				addrToUpdate.setBuilding(building);

				addressRepository.save(addrToUpdate);
				System.out.println(
						"Address with ID " + addressId + " updated successfully for account ID " + loggedInAccountId);

			} else {

				System.err.println("Error: Account " + loggedInAccountId + " tried to update address " + addressId
						+ " which belongs to account " + addrToUpdate.getAccountId());

			}
		} else {

			System.err.println("Error: Address with ID " + addressId + " not found for update.");

		}

		return "redirect:/mypage";
	}

	@GetMapping("/mypage/editAccount")
	public String editAccountForm(Model model) {
		if (!isLoggedIn()) {
			return "redirect:/login";
		}
		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (editAccountForm).");
			this.session.invalidate();
			return "redirect:/login";
		}
		Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);
		if (accountOpt.isPresent()) {
			model.addAttribute("account", accountOpt.get());
			return "editAccount";
		} else {
			System.err.println("Error: Account not found in DB for ID from session: " + loggedInAccountId
					+ " during editAccountForm.");
			this.session.invalidate();
			return "redirect:/login";
		}
	}

	@PostMapping("/mypage/updateAccount")
	public String updateAccount(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "tel") String tel,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (updateAccount Post).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<Account> accountOpt = accountRepository.findById(loggedInAccountId);

		if (accountOpt.isPresent()) {
			Account accToUpdate = accountOpt.get();
			accToUpdate.setName(name);
			accToUpdate.setTel(tel);

			accountRepository.save(accToUpdate);
			System.out.println("Account " + loggedInAccountId + " (Name/Tel) updated successfully.");

		} else {

			System.err.println("Error: Account not found in DB for ID from session: " + loggedInAccountId
					+ " during updateAccount Post.");
			this.session.invalidate();
			return "redirect:/login";
		}

		return "redirect:/mypage";
	}

	@GetMapping("/mypage/addAddress")
	public String addAddress(Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Account loggedInAccount = getCurrentLoggedInAccount();
		model.addAttribute("loggedInAccount", loggedInAccount);

		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		model.addAttribute("prefecturesList", prefecturesList);

		return "addAddress";
	}

	@PostMapping("/mypage/addAddress")
	public String addAddress(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") Integer prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building") String building,
			Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");

		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (after isLoggedIn check).");

			this.session.invalidate();
			return "redirect:/login";
		}

		Address newAddress = new Address();

		newAddress.setAccountId(loggedInAccountId);

		newAddress.setName(name);
		newAddress.setPostalCode(postalCode);
		newAddress.setPrefectureId(prefectureId);
		newAddress.setStreet(street);
		newAddress.setBuilding(building);

		addressRepository.save(newAddress);

		return "redirect:/mypage";
	}

	@GetMapping("/mypage/addCreditCard")
	public String addCreditCard(Model model, HttpServletRequest request) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		return "addPaymentType";
	}

	@PostMapping("/mypage/addCreditCard")
	public String addCreditCard(
			@RequestParam(name = "cardNumber") String cardNumber,
			@RequestParam(name = "expiryMonth") String expiryMonth,
			@RequestParam(name = "expiryYear") String expiryYear,
			@RequestParam(name = "securityCode") String securityCode,
			@RequestParam(name = "cardName") String cardName,
			Model model) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		CreditCard creditCard = new CreditCard();
		creditCard.setAccountId(accountS.getId());
		creditCard.setNumber(cardNumber);
		creditCard.setExpirationMm(expiryMonth);
		creditCard.setExpirationYy(expiryYear);
		creditCard.setCvc(securityCode);
		creditCard.setName(cardName);
		creditCardRepository.save(creditCard);

		return "redirect:/mypage";
	}

	// 個人注文詳細ページを表示
		@GetMapping("/mypage/orderDetail/{orderId}")
		public String orderDetail(@PathVariable Integer orderId, Model model) {
			// ログインチェック
			if (!isLoggedIn()) {
				return "redirect:/login";
			}

			// 注文が現在のユーザーのものか確認
			Optional<Order> orderOpt = orderRepository.findById(orderId);
			if (orderOpt.isPresent() && orderOpt.get().getAccountsId().equals(accountS.getId())) {
				Order order = orderOpt.get();
				List<OrderDetails> orderDetailsList = orderDetailRepository.findByOrdersId(orderId); // 변수 이름 변경 (혼동 방지)

	            // 각 OrderDetails에 Item 정보를 수동으로 설정
	            List<OrderDetails> populatedOrderDetailsList = new ArrayList<>();
	            for (OrderDetails detail : orderDetailsList) {
	                // detail.getItemsId()가 null이 아니고, ItemRepository가 주입된 경우
	                // OrderDetails 엔티티에 itemsId 필드와 getter가 있어야 합니다.
	                if (detail.getItemsId() != null) {
	                    Optional<Item> itemOpt = itemRepository.findById(detail.getItemsId());
	                    if (itemOpt.isPresent()) {
	                        detail.setItem(itemOpt.get()); // OrderDetails의 setItem() 메소드 사용
	                    }
	                }
	                populatedOrderDetailsList.add(detail);
	            }


				// **決済情報の取得とモデルへの追加**
				CreditCard orderCreditCard = null;
				PaymentType orderPaymentType = null;

				// creditCardId가 있으면 신용카드 결제
				if (order.getCreditCardId() != null) {
					Optional<CreditCard> creditCardOpt = creditCardRepository.findById(order.getCreditCardId());
					if (creditCardOpt.isPresent()) {
						orderCreditCard = creditCardOpt.get();
						// 신용카드의 경우 PaymentType "クレジットカード" (id=2)를 가정
						Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(2); // ID 2는 'クレジットカード'
						if (paymentTypeOpt.isPresent()) {
							orderPaymentType = paymentTypeOpt.get();
						}
					}
				} else {
					// creditCardId가 없으면 현금 결제 (id=1)를 가정
					Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(1); // ID 1은 '代金引換'
					if (paymentTypeOpt.isPresent()) {
						orderPaymentType = paymentTypeOpt.get();
					}
				}

				model.addAttribute("order", order);
				model.addAttribute("orderDetails", populatedOrderDetailsList); // Item 정보가 채워진 리스트 사용
				model.addAttribute("orderCreditCard", orderCreditCard);
				model.addAttribute("orderPaymentType", orderPaymentType);

				model.addAttribute("loggedInAccount", getCurrentLoggedInAccount());

				return "orderDetail";
			} else {
				return "redirect:/mypage";
			}
		}

	@PostMapping("/mypage/deleteAddress/{addressId}")
	public String deleteAddress(@PathVariable("addressId") Integer addressId,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (deleteAddress).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<Address> addressOpt = addressRepository.findById(addressId);

		if (addressOpt.isPresent()) {
			Address addressToDelete = addressOpt.get();
			if (addressToDelete.getAccountId() != null && addressToDelete.getAccountId().equals(loggedInAccountId)) {

				addressRepository.delete(addressToDelete);

				System.out.println(
						"Address with ID " + addressId + " deleted successfully for account ID " + loggedInAccountId);

			} else {

				System.err.println("Error: Account " + loggedInAccountId + " tried to delete address " + addressId
						+ " which belongs to account " + addressToDelete.getAccountId());

			}
		} else {

			System.err.println("Error: Address with ID " + addressId + " not found.");

		}

		return "redirect:/mypage";
	}

	@PostMapping("/mypage/deleteCreditCard/{cardId}")
	public String deleteCreditCard(@PathVariable("cardId") Integer cardId,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (!isLoggedIn()) {
			return "redirect:/login";
		}

		Integer loggedInAccountId = (Integer) this.session.getAttribute("accountId");
		if (loggedInAccountId == null) {
			System.err.println("Error: Logged-in user ID not found in session (deleteCreditCard).");
			this.session.invalidate();
			return "redirect:/login";
		}

		Optional<CreditCard> creditCardOpt = creditCardRepository.findById(cardId);

		if (creditCardOpt.isPresent()) {
			CreditCard creditCardToDelete = creditCardOpt.get();
			if (creditCardToDelete.getAccountId() != null
					&& creditCardToDelete.getAccountId().equals(loggedInAccountId)) {

				creditCardRepository.delete(creditCardToDelete);

				System.out.println(
						"Credit card with ID " + cardId + " deleted successfully for account ID " + loggedInAccountId);

			} else {

				System.err.println("Error: Account " + loggedInAccountId + " tried to delete credit card " + cardId
						+ " which belongs to account " + creditCardToDelete.getAccountId());

			}
		} else {

			System.err.println("Error: Credit card with ID " + cardId + " not found.");

		}

		return "redirect:/mypage";
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

	private Account getCurrentLoggedInAccount() {

		Account dummyAccount = new Account();

		dummyAccount.setName("test");
		return dummyAccount;
	}
}