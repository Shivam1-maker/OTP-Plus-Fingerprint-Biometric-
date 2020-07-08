package in.thansoft.k8s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.thansoft.k8s.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("/withdraw")
	public String withdraw(Model model, @RequestParam("accountNumber") String accountNumber,
			@RequestParam("amount") long amount, @RequestParam("finger") MultipartFile fingerPrint) {
		model.addAttribute("accountNumber", accountNumber);
		model.addAttribute("message", transactionService.withdraw(accountNumber, amount, fingerPrint));
		model.addAttribute("transactions", transactionService.getTransactions(accountNumber));
		return "withdraw";
	}

	@GetMapping("/withdraw")
	public String withdraw(Model model, @RequestParam("accountNumber") String accountNumber) {
		model.addAttribute("accountNumber", accountNumber);
		model.addAttribute("transactions", transactionService.getTransactions(accountNumber));
		return "withdraw";
	}

}
