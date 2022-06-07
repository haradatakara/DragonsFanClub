package com.example.demo.app.logined;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.example.demo.app.players.PartSearchForm;
import com.example.demo.app.shopping.CartForm;
import com.example.demo.app.shopping.TransactionForm;
import com.example.demo.app.update.UpdateForm;
import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.players.PlayersInfo;
import com.example.demo.entity.search.Shop;
import com.example.demo.entity.shopping.Products;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.players.PlayerService;
import com.example.demo.service.search.SearchService;
import com.example.demo.service.shopping.ShoppingService;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing")
public class LoginedController {

	private final LoginService loginService;
	private final PlayerService playerService;
	private final SearchService searchService;
	private final ShoppingService shoppingService;

	private static List<CartForm> cartProducts = new ArrayList<>();
	@Autowired
	HttpSession session;

	@Autowired
	public LoginedController(LoginService loginService, PlayerService playerService, SearchService searchService,
			ShoppingService shoppingService) {
		this.loginService = loginService;
		this.playerService = playerService;
		this.searchService = searchService;
		this.shoppingService = shoppingService;
	}

	@PostMapping
	public String isSignIn(@Valid @ModelAttribute("signInForm") SignInForm signInForm,
			@ModelAttribute("usersInfo") UserInfo usersInfo, Model model, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			UserInfo userInfo = new UserInfo();
			userInfo.setMailaddress(signInForm.getMailaddress());
			userInfo.setPassword(signInForm.getPassword());

			boolean isLogin = loginService.signIn(userInfo);
			if (isLogin) {
				UserInfo ui = loginService.fetchUserInfoMail(signInForm.getMailaddress());
				session.setAttribute("userlist", ui);
				session.setAttribute("username", ui.getUser_name());
				session.setAttribute("mailaddress", ui.getMailaddress());
				session.setAttribute("password", ui.getPassword());
				session.setAttribute("id", ui.getUser_id());
				UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
				session.setMaxInactiveInterval(1800);
				model.addAttribute("form", user);
				return "landing";
			} else {
				model.addAttribute("title", "ログインページ");
				model.addAttribute("loginError", "ログインに失敗しました");
				return "signIn";
			}
		} else {
			model.addAttribute("title", "ログインページ");
			model.addAttribute("loginError", "ログインに失敗しました");
			return "signIn";
		}
	}

	@GetMapping
	public String landing(Model model) {
//		model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			if((Integer) session.getAttribute("id") == null) {
				model.addAttribute("notlogin", "ログインしていません");
				return "landing";
			}
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "landing";
	}

	@GetMapping("all_shop")
	public String SearcShops(Model model) {
		List<Shop> list = searchService.serachAllShop();
		model.addAttribute("resultList", list);
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "all_shop";
	}

	@GetMapping("part_shop/{shop_id}")
	public String SearcPartShops(Model model, @PathVariable("shop_id") int shopId) {
		Shop s = searchService.searchPartShop(shopId);
		model.addAttribute("resultList", s);
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "part_shop";
	}

	@GetMapping("update")
	public String update(Model model, SignInForm signInForm) {
		try {
			if((Integer) session.getAttribute("id") == null) {
				System.out.println("ggg");
				model.addAttribute("notlogin", "ログインしていません");
				model.addAttribute("signInForm", signInForm);
				return "signIn";
			}
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
		} catch (Exception e) {}
		return "update";
	}

	@GetMapping("update_check_name")
	public String updateCheckName(Model model) {
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
			model.addAttribute("secTitle", "Name");
			model.addAttribute("inputValue", user.getUser_name());
		} catch (Exception e) {}
		return "update_check";
	}

	@GetMapping("update_check_mail")
	public String updateCheckMail(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		model.addAttribute("secTitle", "MailAddress");
		model.addAttribute("inputValue", user.getMailaddress());
		return "update_check";
	}

	@GetMapping("update_check_pass")
	public String updateCheckPass(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		model.addAttribute("secTitle", "Password");
		model.addAttribute("inputValue", user.getPassword());
		return "update_check";
	}

	@PostMapping("update")
	public String updateResName(Model model, @Valid @ModelAttribute UpdateForm updateForm, BindingResult result,
			RedirectAttributes redirectAttributes) {
		System.out.println(updateForm.getSecTitle());
		if (result.hasErrors()) {
			model.addAttribute("updateError", "更新失敗です。");
			return "redirect:update_check";
		} else {
			int sessionId = (int) session.getAttribute("id");
			boolean isUpdate = false;

			if (updateForm.getSecTitle().equals("Name")) {
				if (!updateForm.getName().equals("")) {
					isUpdate = loginService.updateResultName(updateForm.getName(), sessionId);
					session.setAttribute("username", updateForm.getName());
				} else {
					model.addAttribute("error", "値を入力してください");
					model.addAttribute("secTitle", "Name");
					model.addAttribute("inputValue", updateForm.getName());
					return "update_check";
				}
			} else if (updateForm.getSecTitle().equals("MailAddress")) {
				// メールアドレスがユニークかどうか判定
				if (loginService.checkUnique(updateForm.getMail_address())) {
					isUpdate = loginService.updateResultMail(updateForm.getMail_address(), sessionId);
					session.setAttribute("mailaddress", updateForm.getMail_address());
				} else {
					model.addAttribute("error", "そのメールアドレスは、すでに登録済みです。");
					model.addAttribute("secTitle", "MailAddress");
					model.addAttribute("inputValue", updateForm.getMail_address());
					return "update_check";
				}
			} else {
				isUpdate = loginService.updateResultPass(updateForm.getPassword(), sessionId);
				if (!updateForm.getPassword().equals("")) {
					isUpdate = loginService.updateResultPass(updateForm.getPassword(), sessionId);
					session.setAttribute("password", updateForm.getPassword());
				} else {
					model.addAttribute("error", "値を入力してください");
					model.addAttribute("secTitle", "Password");
					model.addAttribute("inputValue", updateForm.getPassword());
					return "update_check";
				}
			}

			if (isUpdate) {
				model.addAttribute("isUpdate", isUpdate);
				UserInfo user = loginService.fetchUserInfoId(sessionId);
				session.setAttribute("userlist", user);
				model.addAttribute("form", (UserInfo) session.getAttribute("userlist"));
				return "update";
			} else {
				model.addAttribute("updateError", "更新失敗です。");
				return "update_check";
			}
		}
	}

	@GetMapping("delete")
	public String delete(Model model) {
		List<PlayersInfo> list = playerService.allSearch();
		model.addAttribute("resultList", list);
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		return "delete";
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
		session.removeAttribute("items");
		cartProducts.clear();
		for (CartForm l : list) {   
			isComplete = shoppingService.orderComplete(l.getPro_id(), tf.getPayment(), LocalDateTime.now(), (int) session.getAttribute("id"));
		}
		if (isComplete) {
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

	
	
	
	@GetMapping("part_search_result_name")
	public String nameSearchResult(Model model, @Valid @ModelAttribute PartSearchForm partSearchForm,
			BindingResult result, RedirectAttributes redirectAttributes) {
		List<PlayersInfo> batterList = null;
		List<PlayersInfo> peatcherList = null;

		if (result.hasErrors()) {
			model.addAttribute("searchError", "検索に失敗しました");
			return "part_search";
		} else {
			String inputName = partSearchForm.getName();
			batterList = playerService.batterNameSearch(inputName);
			if (batterList.size() == 0) {
				peatcherList = playerService.peatcherNameSearch(inputName);
				if (peatcherList.size() == 0) {
					model.addAttribute("searchError", "検索した名前に該当する選手は見つかりませんでした");
					return "part_search";
				} else {
					model.addAttribute("list", peatcherList);
					model.addAttribute("isRecord", "peatcher");
					UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
					model.addAttribute("form", user);
					return "part_search_result";
				}
			} else {
				model.addAttribute("list", batterList);
				model.addAttribute("isRecord", "batter");
				UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
				model.addAttribute("form", user);
				return "part_search_result";
			}
		}
	}

	@GetMapping("part_search_result_num")
	public String uniSearchResult(Model model, @Valid @ModelAttribute PartSearchForm partSearchForm,
			BindingResult result, RedirectAttributes redirectAttributes) {
		List<PlayersInfo> list = null;

		if (result.hasErrors()) {
			model.addAttribute("searchError", "検索に失敗しました");
			return "part_search";
		} else {
			int inputUni = partSearchForm.getUniformNum();
			list = playerService.batterUniSearch(inputUni);
			if (list.size() == 0) {
				model.addAttribute("searchError", "検索した名前に該当する選手は見つかりませんでした");
				return "part_search";
			} else {
				model.addAttribute("list", list);
				UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
				model.addAttribute("form", user);
				return "part_search_result";
			}
		}
	}
}
