package in.thansoft.k8s.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.thansoft.k8s.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	List<Transaction> findByAccountId(String accountId);

}
