package in.thansoft.k8s.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import in.thansoft.k8s.util.StringIDGenerator;
import lombok.Data;

@Data
@Entity
public class Account {
	@Id
	@GeneratedValue(generator = "account_seq",strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "account_seq", strategy = "in.thansoft.k8s.util.StringIDGenerator", parameters = {
			@Parameter(name = StringIDGenerator.PREFIX_PARAM, value = "ACC"),
			@Parameter(name = StringIDGenerator.FORMAT_PARAM, value = "%05d") })
	private String id;
	private String customer;
	private String password;
	private String fingerPrintUrl;
	private long balance;
	@OneToMany(mappedBy = "account")
	private Set<Transaction> transactions;
}
