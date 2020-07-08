package in.thansoft.k8s.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import in.thansoft.k8s.util.StringIDGenerator;
import in.thansoft.k8s.util.Type;
import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue(generator = "transaction_seq",strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "transaction_seq", strategy = "in.thansoft.k8s.util.StringIDGenerator", parameters = {
			@Parameter(name = StringIDGenerator.PREFIX_PARAM, value = "TXN"),
			@Parameter(name = StringIDGenerator.FORMAT_PARAM, value = "%09d") })
	private String id;
	private String date;
	private long amount;
	@Enumerated(EnumType.STRING)
	private Type type;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "accountId")
	private Account account;
}
