package com.example.demo.app.shopping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.shopping.Products;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.shopping.ShoppingService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing")
public class ShoppingController {
	
	private final LoginService loginService;
	private final ShoppingService shoppingService;

	private static List<CartForm> cartProducts = new ArrayList<>();
	
	@Autowired
	HttpSession session;

	@Autowired
	public ShoppingController(LoginService loginService, ShoppingService shoppingService) {
		this.loginService = loginService;
		this.shoppingService = shoppingService;
	}
	
	@GetMapping("shopping")
	public String shopping(Model model) {
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "shopping_select";
	}

	@GetMapping("product_list/{id}")
	@PostMapping("product_list/{id}")
	public String product_list(Model model, @PathVariable("id") int genreId) {
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}

		List<Products> list = shoppingService.displayProducts(genreId);
		
		model.addAttribute("list", list);
		model.addAttribute("id", genreId);
		return "product_list";
	}

	@GetMapping("product_list/product_detail/{pro_id}")
	@PostMapping("product_list/product_detail/{pro_id}")
	public String pro_detail(Model model, @PathVariable("pro_id") int proId) {
		try {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
	} catch (Exception e) {}
	    Products list = shoppingService.displayDetailProduct(proId);
		model.addAttribute("list", list);
		return "pro_details";
	}

	@GetMapping("transaction")
	@PostMapping("transaction")
	public String transaction(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);

		@SuppressWarnings("unchecked")
		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		if (org.springframework.util.CollectionUtils.isEmpty(list)) {
			model.addAttribute("error", "カートの中に商品が１つも存在しません");
			return "shopping_cart";
		}

		return "transaction";
	}

	@PostMapping("transaction_confirm")
	public String transactionConfirm(Model model, @ModelAttribute @Validated TransactionForm transactionForm,
			BindingResult res, RedirectAttributes redirectAttributes) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		if (res.hasErrors()) {
			model.addAttribute("confirmForm", transactionForm);
			model.addAttribute("error", "必要な値を入力してください");
			return "transaction";
		} else {
			if (transactionForm.getPayment().equals("credit") && (transactionForm.getCreditNumber().equals("")
					|| transactionForm.getCreditPassword().equals(""))) {
				model.addAttribute("confirmForm", transactionForm);
				model.addAttribute("error", "クレジット情報を入力してください");
				return "transaction";
			}
			model.addAttribute("confirmForm", transactionForm);
			session.setAttribute("confirmForm", transactionForm);
			@SuppressWarnings("unchecked")
			List<CartForm> list = (List<CartForm>) session.getAttribute("items");
			model.addAttribute("list", list);
			return "transaction_confirm";
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("transaction_complete")
	public String transactionComplete(Model model, @ModelAttribute @Validated TransactionForm transactionForm,
			BindingResult res, RedirectAttributes redirectAttributes) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);

		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		
		TransactionForm tf = (TransactionForm) session.getAttribute("confirmForm");
		
		boolean isComplete = true;
		for (CartForm l : list) {   
			isComplete = shoppingService.orderComplete(
					l.getPro_id(), tf.getPayment(), LocalDateTime.now(),l.getSize_l(), l.getSize_xl(), l.getSize_xxl(), tf.getDates(), (int) session.getAttribute("id"));
		}
		if (isComplete) {
			session.removeAttribute("items");
			cartProducts.clear();
			return "transaction_complete";
		} else {
			model.addAttribute("error", "申し訳ございませんもう一度最初からやり直してください");
			return "shopping_cart";
		}
	}

	@GetMapping("product_list/cart/{pro_id}")
	@PostMapping("product_list/cart/{pro_id}")
	public String cart(Model model, @ModelAttribute @Validated CartForm cartForm, BindingResult res,
			RedirectAttributes redirectAttributes, @PathVariable("pro_id") int proId, SignInForm signInForm) {
		
		try {
			if((Integer) session.getAttribute("id") == null) {
				System.out.println("ggg");
				model.addAttribute("notlogin", "購入をするには、ログインをおこなってください");
				model.addAttribute("signInForm", signInForm);
				return "signIn";
			}
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		if (!res.hasErrors()) {
			if (cartForm.getSize_l() == 0 && cartForm.getSize_xl() == 0 && cartForm.getSize_xxl() == 0) {
				model.addAttribute("error", "カートに１つ以上の商品を入れてください");
				Products list = shoppingService.displayDetailProduct(proId);
				model.addAttribute("list", list);
				return "pro_details";
			}
			Products proDetail = shoppingService.displayDetailProduct(proId);
			cartForm.setPro_name(proDetail.getPro_name());
			cartForm.setPro_img(proDetail.getPro_img());
			cartForm.setPro_id(proId);
			cartProducts.add(cartForm);
			System.out.println(cartProducts);

			if (cartProducts.size() > 3) {
				model.addAttribute("error", "カートの中身をこれ以上は追加することは出来ません");
				Products list = shoppingService.displayDetailProduct(proId);
				model.addAttribute("list", list);
				return "pro_details";
			}
			session.setAttribute("items", cartProducts);
			shoppingService.deleteProducts(
					cartForm.getSize_l(), cartForm.getSize_xl(), cartForm.getSize_xxl(), cartForm.getPro_id());
			@SuppressWarnings("unchecked")
			List<CartForm> list = (List<CartForm>) session.getAttribute("items");
			model.addAttribute("list", list);
		}
		return "redirect:/mystyle/landing/product_list/cart";
	}

	@GetMapping("product_list/cart")
	public String cartget(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		@SuppressWarnings("unchecked")
		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		model.addAttribute("list", list);
		return "shopping_cart";
	}
	@GetMapping("cart/delete/{proId}") 
	public String cartDelete(Model model,@PathVariable("proId") int proId){
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		@SuppressWarnings("unchecked")
		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		shoppingService.incrementProducts(list.get(proId).getSize_l(), list.get(proId).getSize_xl(), list.get(proId).getSize_xxl(), list.get(proId).getPro_id());
		list.remove(proId);
		model.addAttribute("list", list);
		return "shopping_cart";
	}

	@PostMapping("product_list/cart")
	public String cartpost(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		@SuppressWarnings("unchecked")
		List<CartForm> list = (List<CartForm>) session.getAttribute("items");
		model.addAttribute("list", list);
		return "shopping_cart";
	}


}
