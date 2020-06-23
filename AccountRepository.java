package in.thansoft.k8s.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.thansoft.k8s.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	long countByIdAndPassword(String accountNumber, String password);

}
