package in.thansoft.k8s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.thansoft.k8s.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/entroll")
	public String entroll(Model model, @RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("amount") long amount, @RequestParam("finger") MultipartFile fingerPrint) {
		try {
			model.addAttribute("accountId", accountService.entroll(name, password, amount, fingerPrint));
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("hasError", true);
			model.addAttribute("error", e.getMessage());
			return "entroll";
		}
	}

	@PostMapping("/login")
	public String login(@RequestParam("accountNumber") String accountNumber, @RequestParam("password") String password,
			Model model) {
		if (!accountService.login(accountNumber, password)) {
			model.addAttribute("hasError", true);
			model.addAttribute("error", "No accounts Available with given Account Number and Password");
			return "index";
		} else
			return "redirect:withdraw?accountNumber=" + accountNumber;
	}

	@GetMapping("/entroll")
	public String entroll() {
		return "entroll";
	}

}
