package cn.birdstudio.transaction.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "updates_applied", schema = "transaction")
@Entity
public class UpdatesApplied {
	@Column(nullable = false)
	private int trans_id;

	@Column(nullable = false)
	private String balance;

	@Column(nullable = false)
	private int user_id;

	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
