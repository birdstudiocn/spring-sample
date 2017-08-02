package cn.birdstudio.transaction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "transaction", schema = "transaction")
@Entity
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "transaction_generator", sequenceName = "transaction_sequence", initialValue = 1)
	@GeneratedValue(generator = "transaction_generator")
	private Long xid;

	@Column(nullable = false)
	private int seller_id;

	@Column(nullable = false)
	private int buyer_id;

	@Column(nullable = false)
	private int amount;

	public Long getXid() {
		return xid;
	}

	public void setXid(Long xid) {
		this.xid = xid;
	}

	public int getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}

	public int getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
