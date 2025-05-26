package com.example.demo.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Address;
import com.example.demo.entity.CreditCard;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.PaymentType;
import com.example.demo.entity.Prefectures;
import com.example.demo.entity.Stocks;
import com.example.demo.form.CreditForm;
import com.example.demo.form.OrderForm;
import com.example.demo.model.Cart;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentTypeRepository;
import com.example.demo.repository.PrefecturesRepository;
import com.example.demo.repository.StocksRepository;

@Controller
public class OrderController {

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

	@Autowired
	Cart cart;

	@GetMapping("/order")
	public String index(Model model) {

		Integer accountId = (Integer) session.getAttribute("accountId");

		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		model.addAttribute("prefecturesList", prefecturesList);

		if (accountId != null) {

			Optional<Account> loggedInAccountOpt = accountRepository.findById(accountId);
			if (loggedInAccountOpt.isPresent()) {
				Account loggedInAccount = loggedInAccountOpt.get();
				model.addAttribute("loggedInAccount", loggedInAccount);

				List<Address> registeredAddresses = addressRepository.findByAccountId(accountId);
				model.addAttribute("registeredAddresses", registeredAddresses);

				List<CreditCard> registeredCreditCards = creditCardRepository.findByAccountId(accountId);
				model.addAttribute("registeredCreditCards", registeredCreditCards);

				System.out.println("アカウント名: accountId=" + accountId);
				System.out.println("住所の数: " + registeredAddresses.size());
				System.out.println("カードの数: " + registeredCreditCards.size());

			} else {
				session.removeAttribute("accountId");
			}
		} else {
			System.out.println("ゲストです");
		}

		return "guestInput";
	}

	@PostMapping("/order/confirm")
	public String confirmOrder(
			@ModelAttribute OrderForm form,
			@RequestParam(name = "isLoggedIn", defaultValue = "false") boolean isLoggedIn,
			@RequestParam(name = "accountId", required = false) Integer accountId,
			@RequestParam(name = "selectedAddressId", required = false) Integer selectedAddressId,
			@RequestParam(name = "selectedCreditCardId", required = false) Integer selectedCreditCardId,
			Model model) {

		LocalDateTime now = LocalDateTime.now();

		Account currentAccount = null;
		Address currentAddress = null;

		if (isLoggedIn && accountId != null) {
			Optional<Account> accountOpt = accountRepository.findById(accountId);
			if (accountOpt.isPresent()) {
				currentAccount = accountOpt.get();

				if (selectedAddressId != null) {
					Optional<Address> addressOpt = addressRepository.findById(selectedAddressId);
					if (addressOpt.isPresent()) {
						currentAddress = addressOpt.get();
						if (currentAccount != null) {
							form.setName(currentAccount.getName());
							form.setTel(currentAccount.getTel());
							form.setEmail(currentAccount.getEmail());
						}
						form.setPostalCode(currentAddress.getPostalCode());
						form.setPrefectureId(currentAddress.getPrefectureId());
						form.setStreet(currentAddress.getStreet());
						form.setBuilding(currentAddress.getBuilding());

					} else {
						selectedAddressId = null;
					}
				}

				if (currentAddress == null) {
					Address newAddress = new Address();
					newAddress.setType("registered");
					newAddress.setAccountId(currentAccount.getId());
					newAddress.setName(form.getName());
					newAddress.setPostalCode(form.getPostalCode());
					newAddress.setPrefectureId(form.getPrefectureId());
					newAddress.setStreet(form.getStreet());
					newAddress.setBuilding(form.getBuilding());
					newAddress.setCreateDate(now);
					newAddress.setUpdateDate(now);
					addressRepository.save(newAddress);
					currentAddress = newAddress;
				}

				model.addAttribute("selectedCreditCardId", selectedCreditCardId);

			} else {
				isLoggedIn = false;
			}
		}

		if (!isLoggedIn || currentAccount == null) {
			Account guestAccount = new Account();
			guestAccount.setName(form.getName());
			guestAccount.setTel(form.getTel());
			guestAccount.setEmail(form.getEmail());
			guestAccount.setCreateDate(now);
			guestAccount.setUpdateDate(now);
			accountRepository.save(guestAccount);
			currentAccount = guestAccount;

			Address guestAddress = new Address();
			guestAddress.setType("guest");
			guestAddress.setAccountId(currentAccount.getId());
			guestAddress.setName(form.getName());
			guestAddress.setPostalCode(form.getPostalCode());
			guestAddress.setPrefectureId(form.getPrefectureId());
			guestAddress.setStreet(form.getStreet());
			guestAddress.setBuilding(form.getBuilding());
			guestAddress.setCreateDate(now);
			guestAddress.setUpdateDate(now);
			addressRepository.save(guestAddress);
			currentAddress = guestAddress;

			model.addAttribute("isLoggedIn", false);
		} else {
			model.addAttribute("isLoggedIn", true);
			model.addAttribute("accountId", currentAccount.getId());
			model.addAttribute("loggedInAccount", currentAccount);
		}

		model.addAttribute("accountId", currentAccount.getId());
		model.addAttribute("addressId", currentAddress.getId());

		model.addAttribute("form", form);

		List<CreditCard> registeredCreditCards = new ArrayList<>();
		if (isLoggedIn && currentAccount != null) {
			registeredCreditCards = creditCardRepository.findByAccountId(currentAccount.getId());
		}
		model.addAttribute("registeredCreditCards", registeredCreditCards);

		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		model.addAttribute("prefecturesList", prefecturesList);

		return "addCredit";
	}

	@GetMapping("/order/info")
	public String orderInfo() {
		return "addCredit";
	}

	@PostMapping("/order/info")
	public String orderInfo(
			@ModelAttribute OrderForm form,
			@ModelAttribute CreditForm creditForm,
			@RequestParam(name = "accountId") Integer accountId,
			@RequestParam(name = "addressId") Integer addressId,
			@RequestParam(name = "isLoggedIn") boolean isLoggedIn,
			@RequestParam(name = "selectedAddressId", required = false) Integer selectedAddressId,
			@RequestParam(name = "selectedCreditCardId", required = false) Integer selectedCreditCardId,
			Model model) {

		if (accountId == null) {

			model.addAttribute("error", "アカウント情報が見つかりません");
			return "redirect:/order";
		}

		if (addressId == null) {

			model.addAttribute("error", "住所の情報が見つかりません");
			return "redirect:/order";
		}

		System.out.println("Prefecture ID from form: " + form.getPrefectureId());
		System.out.println("Button value: " + creditForm.getButton());
		System.out.println("Payment Type: " + creditForm.getType());
		System.out.println("Selected Credit Card ID: " + selectedCreditCardId);

		Integer paymentType = creditForm.getType();
		System.out.println("paymentType: " + paymentType);

		CreditCard usedCreditCard = null;

		if (creditForm.getButton() == null || creditForm.getButton() != 1) {

			model.addAttribute("form", form);
			model.addAttribute("creditform", creditForm);
			model.addAttribute("accountId", accountId);
			model.addAttribute("addressId", addressId);
			model.addAttribute("isLoggedIn", isLoggedIn);

			List<CreditCard> registeredCreditCards = new ArrayList<>();
			if (isLoggedIn && accountId != null) {
				registeredCreditCards = creditCardRepository.findByAccountId(accountId);
			}
			model.addAttribute("registeredCreditCards", registeredCreditCards);

			return "addCredit";
		}

		if (paymentType == null) {

			model.addAttribute("error", "決算方法を選択してください");

			model.addAttribute("form", form);
			model.addAttribute("creditform", creditForm);
			model.addAttribute("accountId", accountId);
			model.addAttribute("addressId", addressId);
			model.addAttribute("isLoggedIn", isLoggedIn);

			List<CreditCard> registeredCreditCards = new ArrayList<>();
			if (isLoggedIn && accountId != null) {
				registeredCreditCards = creditCardRepository.findByAccountId(accountId);
			}
			model.addAttribute("registeredCreditCards", registeredCreditCards);

			return "addCredit";
		}

		if (paymentType == 2) {

			if (isLoggedIn && selectedCreditCardId != null) {

				Optional<CreditCard> registeredCardOpt = creditCardRepository.findById(selectedCreditCardId);

				if (registeredCardOpt.isPresent()) {
					CreditCard registeredCard = registeredCardOpt.get();

					if (registeredCard.getAccountId().equals(accountId)) {
						usedCreditCard = registeredCard;

					} else {

						model.addAttribute("error", "カードの選択をやり直してください。");

						model.addAttribute("form", form);
						model.addAttribute("creditform", creditForm);
						model.addAttribute("accountId", accountId);
						model.addAttribute("addressId", addressId);
						model.addAttribute("isLoggedIn", isLoggedIn);

						List<CreditCard> registeredCreditCards = creditCardRepository.findByAccountId(accountId);
						model.addAttribute("registeredCreditCards", registeredCreditCards);

						return "addCredit";
					}
				} else {

					model.addAttribute("error", "カード情報が見つかりません");

					// 에러 시 데이터 재설정
					model.addAttribute("form", form);
					model.addAttribute("creditform", creditForm);
					model.addAttribute("accountId", accountId);
					model.addAttribute("addressId", addressId);
					model.addAttribute("isLoggedIn", isLoggedIn);

					List<CreditCard> registeredCreditCards = creditCardRepository.findByAccountId(accountId);
					model.addAttribute("registeredCreditCards", registeredCreditCards);

					return "addCredit";
				}
			}

			else if (selectedCreditCardId == null && creditForm.getNumber() != null
					&& !creditForm.getNumber().trim().isEmpty()) {

				CreditCard newCreditCard = new CreditCard();
				newCreditCard.setAccountId(accountId);
				newCreditCard.setNumber(creditForm.getNumber().trim());
				newCreditCard.setExpirationYy(creditForm.getYy());
				newCreditCard.setExpirationMm(creditForm.getMm());
				newCreditCard.setName(creditForm.getCardName());
				newCreditCard.setCvc(creditForm.getCvc());
				Timestamp now = new Timestamp(System.currentTimeMillis());
				newCreditCard.setCreateDate(now);
				newCreditCard.setUpdateDate(now);

				creditCardRepository.save(newCreditCard);
				usedCreditCard = newCreditCard;

			}

			else {

				model.addAttribute("error", "登録済みのカードを選択して下さい");

				model.addAttribute("form", form);
				model.addAttribute("creditform", creditForm);
				model.addAttribute("accountId", accountId);
				model.addAttribute("addressId", addressId);
				model.addAttribute("isLoggedIn", isLoggedIn);

				List<CreditCard> registeredCreditCards = new ArrayList<>();
				if (isLoggedIn && accountId != null) {
					registeredCreditCards = creditCardRepository.findByAccountId(accountId);
				}
				model.addAttribute("registeredCreditCards", registeredCreditCards);

				return "addCredit";
			}
		} else if (paymentType == 1) {

		}

		model.addAttribute("accountId", accountId);
		model.addAttribute("addressId", addressId);
		model.addAttribute("isLoggedIn", isLoggedIn);

		List<CreditCard> registeredCreditCards = new ArrayList<>();
		if (isLoggedIn && accountId != null) {
			registeredCreditCards = creditCardRepository.findByAccountId(accountId);
		}
		model.addAttribute("registeredCreditCards", registeredCreditCards);

		Optional<PaymentType> payType = paymentTypeRepository.findById(paymentType);
		model.addAttribute("payType", payType.orElse(null));

		if (form.getPrefectureId() != null) {
			Optional<Prefectures> preName = prefecturesRepository.findById(form.getPrefectureId());
			model.addAttribute("preName", preName.orElse(null));
		} else {
			model.addAttribute("preName", null);
		}

		model.addAttribute("usedCreditCard", usedCreditCard);
		model.addAttribute("form", form);
		model.addAttribute("creditform", creditForm);
		model.addAttribute("cart", cart);

		return "payment";
	}

	@Transactional
	@PostMapping("/order")
	public String completeOrder(
			@ModelAttribute OrderForm form,
			@ModelAttribute CreditForm creditForm,
			@RequestParam(name = "accountId") Integer accountId,
			@RequestParam(name = "addressId") Integer addressId,
			Model model) {

		LocalDateTime now = LocalDateTime.now();

		try {

			Order order = new Order();
			order.setAccountsId(accountId);

			order.setOrderedOn(new Date(System.currentTimeMillis()));
			order.setTotalPrice(cart.getTotalPrice());
			order.setAddressId(String.valueOf(addressId));
			order.setCreditCardId(creditForm.getType());

			order.setCreateDate(now);
			order.setUpdateDate(now);
			order.setDeleteFlg("0");

			orderRepository.save(order);

			Integer orderId = order.getId();

			if (cart.getItems() != null && !cart.getItems().isEmpty()) {
				System.out.println("카트 아이템 수: " + cart.getItems().size());
				for (Item item : cart.getItems()) {
					System.out.println("処理中인 아이템 ID: " + item.getId());

					OrderDetails orderDetails = new OrderDetails();
					orderDetails.setOrdersId(orderId);
					// 수정된 부분: items_id를 올바르게 설정
					orderDetails.setItemsId(item.getId());
					orderDetails.setQuantity(item.getQuantity());
					orderDetails.setCreateDate(now);
					orderDetails.setUpdateDate(now);
					orderDetails.setDeleteFlg("0");

					orderDetailsRepository.save(orderDetails);

					Stocks stock = stocksRepository.findByItemsId(item.getId())
							.orElseThrow(() -> new RuntimeException("在庫が見つかりません. Item ID: " + item.getId()));

					int currentStock = stock.getQuantity();
					int orderedQuantity = item.getQuantity();

					if (currentStock >= orderedQuantity) {
						stock.setQuantity(currentStock - orderedQuantity);
						stock.setUpdateDate(now);
						stocksRepository.save(stock);

					} else {

						throw new RuntimeException(
								"在庫不足 " + item.getId() + ",注文 " + orderedQuantity + ", 現在庫: " + currentStock);
					}
				}
			} else {

				throw new RuntimeException("カートが空きです");
			}

			if (cart != null) {
				cart.clear();
			}

			model.addAttribute("orderId", orderId);
			model.addAttribute("message", "注文が完了しました！");

			return "orderComplete";

		} catch (RuntimeException e) {

			e.printStackTrace();
			model.addAttribute("error", "注文処理中にエラーが発生しました: " + e.getMessage());

			return "orderComplete";

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("error", "注文処理中にエラーが発生しました: " + e.getMessage());
			return "orderComplete";
		}
	}

	@GetMapping("/order/confirm")
	public String showOrderConfirm(Model model) {

		model.addAttribute("cart", cart);
		model.addAttribute("totalPrice", cart.getTotalPrice());
		return "orderConfirm";
	}

	@PostMapping("/order/complete")
	public String completeOrderFromConfirm(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "postal_code") String postalCode,
			@RequestParam(name = "prefecture_id") Integer prefectureId,
			@RequestParam(name = "street") String street,
			@RequestParam(name = "building") String building,
			@RequestParam(name = "paymentMethod") String paymentMethod,
			@RequestParam(name = "creditCardId", required = false) Integer creditCardId,
			Model model) {
		LocalDateTime now = LocalDateTime.now();

		try {

			Account account = new Account();
			account.setName(name);
			account.setTel(tel);
			account.setEmail(email);

			account.setCreateDate(now);
			account.setUpdateDate(now);

			accountRepository.save(account);

			
			Address address = new Address();
			address.setType("guest");
			address.setAccountId(account.getId());
			address.setName(name);
			address.setPostalCode(postalCode);
			address.setPrefectureId(prefectureId);
			address.setStreet(street);
			address.setBuilding(building);

			address.setCreateDate(now);
			address.setUpdateDate(now);

			addressRepository.save(address);

			Order order = new Order();
			order.setAccountsId(account.getId());
			order.setOrderedOn(new Date(System.currentTimeMillis()));
			order.setTotalPrice(cart.getTotalPrice());
			order.setAddressId(String.valueOf(address.getId()));
			if (paymentMethod.equals("credit") && creditCardId != null) {
				order.setCreditCardId(creditCardId);
			}

			order.setCreateDate(now);
			order.setUpdateDate(now);
			order.setDeleteFlg("0");
			orderRepository.save(order);

			Integer orderId = order.getId();

			if (cart.getItems() != null && !cart.getItems().isEmpty()) {

				for (Item item : cart.getItems()) {

					OrderDetails orderDetails = new OrderDetails();
					orderDetails.setOrdersId(orderId);
					// 수정된 부분: items_id를 올바르게 설정
					orderDetails.setItemsId(item.getId());
					orderDetails.setQuantity(item.getQuantity());
					orderDetails.setCreateDate(now);
					orderDetails.setUpdateDate(now);
					orderDetails.setDeleteFlg("0");

					orderDetailsRepository.save(orderDetails);

				}
			}

			if (cart != null) {
				cart.clear();
			}

			model.addAttribute("orderId", orderId);
			model.addAttribute("message", "注文が完了しました！");

			return "orderComplete";

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("error", "注文処理中にエラーが発生しました: " + e.getMessage());
			return "error";
		}
	}
}