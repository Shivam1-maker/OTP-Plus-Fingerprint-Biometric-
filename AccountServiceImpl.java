package in.thansoft.k8s.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.thansoft.k8s.entity.Account;
import in.thansoft.k8s.entity.Transaction;
import in.thansoft.k8s.repository.AccountRepository;
import in.thansoft.k8s.repository.TransactionRepository;
import in.thansoft.k8s.service.AccountService;
import in.thansoft.k8s.util.Type;
import in.thansoft.k8s.util.Utils;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	AccountRepository customerRepository;

	@Override
	public String entroll(String name, String password, long initialAmount, MultipartFile fingerPrint) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
			String fingerPrintUrl = Utils.getPath();
			fingerPrint.transferTo(new File(fingerPrintUrl));
			Transaction transaction = new Transaction();
			transaction.setAmount(initialAmount);
			transaction.setDate(Utils.getCurrentDateTime());
			transaction.setType(Type.CR);
			Account account = new Account();
			account.setCustomer(name);
			account.setPassword(password);
			account.setFingerPrintUrl(fingerPrintUrl);
			account.setBalance(initialAmount);
			transaction.setAccount(account);
			return transactionRepository.save(transaction).getAccount().getId();
	}

	@Override
	public boolean login(String accountNumber, String password) {
		return customerRepository.countByIdAndPassword(accountNumber, password) > 0;
	}
}
