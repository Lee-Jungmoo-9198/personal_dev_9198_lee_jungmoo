package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Item;
import com.example.demo.entity.Stocks; // Stocks 엔티티 사용 시
import com.example.demo.model.Cart; // <-- 사용자 정의 Cart 모델
import com.example.demo.repository.ItemRepository; // Item 조회용
import com.example.demo.repository.StocksRepository; // <-- Stock 조회용 (재고 체크)

@Controller
public class CartController {

	@Autowired
	Cart cart; 

	@Autowired
	ItemRepository itemRepository; 

    @Autowired 
    StocksRepository stockRepository; 

  
	@GetMapping("/cart")
	public String index(Model model) {
		model.addAttribute("cart", cart);
		return "cart";
	}

	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam(name = "itemId") int itemId,
            @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity, // quantity 파라미터 받음 (기본값 1)
            RedirectAttributes redirectAttributes) { 

      

        
        if (quantity < 1) {
             System.err.println("Error: Invalid quantity specified (less than 1): " + quantity);
             redirectAttributes.addFlashAttribute("error", "追加する数量は1以上である必要があります。");
             
             return "redirect:/cart"; 
        }

		
		Optional<Item> itemOpt = itemRepository.findById(itemId);
		if (!itemOpt.isPresent()) {
	        System.err.println("Error: Item ID " + itemId + " not found for adding to cart.");
            redirectAttributes.addFlashAttribute("error", "指定された商品が見つかりませんでした。");
            return "redirect:/cart"; 
		}
		Item itemToAdd = itemOpt.get(); 

        
        Optional<Stocks> stockOpt = stockRepository.findByItemsId(itemId); 
        Integer stockQuantity = stockOpt.map(Stocks::getQuantity).orElse(0);

        
        Integer currentQuantityInCart = 0;
        
        Optional<Item> existingCartItemOpt = cart.getItems().stream()
                                              .filter(item -> item.getId().equals(itemId))
                                              .findFirst();
        if(existingCartItemOpt.isPresent()){
            currentQuantityInCart = existingCartItemOpt.get().getQuantity();
        }


       
        Integer totalQuantityAfterAdd = currentQuantityInCart + quantity;

       
        if (totalQuantityAfterAdd > stockQuantity) {
             System.out.println("Attempted to add " + quantity + " of item " + itemId + ". Total quantity " + totalQuantityAfterAdd + " exceeds stock limit " + stockQuantity);
             redirectAttributes.addFlashAttribute("error", "在庫数以上の商品をカートに追加することはできません (現在のカート数 " + currentQuantityInCart + "個)。"); // 재고 초과 메시지
             // return "redirect:/items/" + itemId; // 상품 상세 페이지에서 왔다면 여기로
             return "redirect:/cart"; // 장바구니 페이지로 돌아가기
        }


   
        Item itemForCart = new Item(); 
        itemForCart.setId(itemToAdd.getId()); 
        itemForCart.setName(itemToAdd.getName()); 
        itemForCart.setPrice(itemToAdd.getPrice());
        itemForCart.setImg(itemToAdd.getImg());  
        itemForCart.setQuantity(quantity); 

        cart.add(itemForCart); 

        
        System.out.println("Added item " + itemId + " to cart with quantity " + quantity + ". Total quantity in cart: " + totalQuantityAfterAdd);
        redirectAttributes.addFlashAttribute("message", "商品をカートに追加しました。");


        
		return "redirect:/cart";
        
	}

   
	@PostMapping("/cart/delete")
	public String deleteCart(
			@RequestParam(name="itemId")int itemId,
            RedirectAttributes redirectAttributes
	) {
		cart.delete(itemId); 
        System.out.println("Removed item " + itemId + " from cart.");
        redirectAttributes.addFlashAttribute("message", "商品をカートから削除しました。");

		return "redirect:/cart";
	}

    
	@PostMapping("/cart/increment")
	public String incrementCartItem(@RequestParam("itemId") Integer itemId) {
	    
	    if (cart != null) {
	        cart.incrementItem(itemId); 
	    }
	   
	    return "redirect:/cart";
	}

	
	@PostMapping("/cart/decrement")
	public String decrementCartItem(@RequestParam("itemId") Integer itemId) {
	    
	    if (cart != null) {
	        cart.decrementItem(itemId); 
	    }
	    
	    return "redirect:/cart";
	}

    
}
