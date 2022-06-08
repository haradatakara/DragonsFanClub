package com.example.demo.app.update;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.user.SignInForm;
import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.entity.user.UserInfo;
import com.example.demo.service.user.LoginService;

@Controller
@RequestMapping("/mystyle/landing/update")
public class UpdateController {

	private final LoginService loginService;

	@Autowired
	HttpSession session;

	@Autowired
	public UpdateController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping
	public String update(Model model, SignInForm signInForm) {
		try {
			if ((Integer) session.getAttribute("id") == null) {
				model.addAttribute("signInForm", signInForm);
				return "signIn";
			}
			model.addAttribute("form", loginService.fetchUserInfoId((int) session.getAttribute("id")));
		} catch (Exception e) {
		}
		return "update";
	}

	@PostMapping
	public String updateResName(Model model, @Valid @ModelAttribute UpdateForm updateForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

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
			if (updateForm.getMail_address().contains("@") && loginService.checkUnique(updateForm.getMail_address())) {
				isUpdate = loginService.updateResultMail(updateForm.getMail_address(), sessionId);
				session.setAttribute("mailaddress", updateForm.getMail_address());
			} else {
				model.addAttribute("error", true);
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
			return "redirect:/mystyle/landing/update";
		} else {
			model.addAttribute("updateError", "更新失敗です。");
			return "update_check";
		}
	}

	@GetMapping("update_check_name")
	public String updateCheckName(Model model) {
		try {
			UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
			model.addAttribute("form", user);
			model.addAttribute("secTitle", "Name");
			model.addAttribute("inputValue", user.getUser_name());
		} catch (Exception e) {
		}
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
	
	@GetMapping("update_check_order")
	public String updateCheckOrder(Model model) {
		UserInfo user = loginService.fetchUserInfoId((int) session.getAttribute("id"));
		model.addAttribute("form", user);
		List<UpdateOrder> uo = loginService.confirmOrder((int) session.getAttribute("id"));
		model.addAttribute("list", uo);
		return "update_order";
	}
	
	@GetMapping("delete/{orderId}/{proId}")
	@PostMapping("delete/{orderId}/{proId}")
	public String orderDelete(
			Model model, 
			@PathVariable("orderId") int orderId,
		    @PathVariable("proId") int proId
		    ) {
		UpdateOrder uo = loginService.detailOrder(orderId);	
		loginService.cancelOrder(orderId, uo.getX_size(), uo.getXl_size(), uo.getXxl_size(), proId);
		List<UpdateOrder> list = loginService.confirmOrder((int) session.getAttribute("id"));
		model.addAttribute("list", list);
		model.addAttribute("isDelete", true);
		return "redirect:/mystyle/landing/update/update_check_order";
	}
	
	@GetMapping("order_id={orderId}")
	public String updateForm(
			Model model, 
			@PathVariable("orderId") int orderId
			) {
		UpdateOrder uo = loginService.detailOrder(orderId);	
		model.addAttribute("list", uo);
		
		return "update_order_form";
	}

}
