package in.thansoft.k8s.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import in.thansoft.k8s.entity.Account;
import in.thansoft.k8s.entity.Transaction;
import in.thansoft.k8s.repository.AccountRepository;
import in.thansoft.k8s.repository.TransactionRepository;
import in.thansoft.k8s.service.TransactionService;
import in.thansoft.k8s.util.Type;
import in.thansoft.k8s.util.Utils;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public String withdraw(String accountNumber, long amount, MultipartFile fingerPrint) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findById(accountNumber).orElse(null);
		if (account != null) {
			try {
				byte[] probeImage = fingerPrint.getBytes();
				byte[] candidateImage = Files.readAllBytes(Paths.get(account.getFingerPrintUrl()));
				FingerprintTemplate probe = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(probeImage));
				FingerprintTemplate candidate = new FingerprintTemplate(
						new FingerprintImage().dpi(500).decode(candidateImage));
				double score = new FingerprintMatcher().index(probe).match(candidate);
				double threshold = 40;
				if (score >= threshold) {
					if (amount <= account.getBalance()) {
						account.setBalance(account.getBalance() - amount);
						accountRepository.save(account);
						Transaction transaction = new Transaction();
						transaction.setAccount(account);
						transaction.setAmount(amount);
						transaction.setDate(Utils.getCurrentDateTime());
						transaction.setType(Type.DR);
						transactionRepository.save(transaction);
						return "Transaction completed successfully,your account current balace : "
								+ account.getBalance();
					} else {
						return "Insufficient balance,your account current balance : " + account.getBalance();
					}
				} else {
					return "Fingerprint mismatch";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Error : " + e.getMessage();
			}
		} else
			return "Account not available";
	}

	@Override
	public List<Transaction> getTransactions(String accountNumber) {
		// TODO Auto-generated method stub
		return transactionRepository.findByAccountId(accountNumber);
	}

}