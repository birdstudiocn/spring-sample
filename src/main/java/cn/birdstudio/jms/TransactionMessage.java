package cn.birdstudio.jms;

import java.io.Serializable;

public class TransactionMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int amount;
	private Type type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(id).append(",amount:").append(amount).append(",type:").append(type);
		return sb.toString();
	}
}
