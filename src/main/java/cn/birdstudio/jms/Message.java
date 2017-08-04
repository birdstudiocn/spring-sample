package cn.birdstudio.jms;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int id;

	private final int amount;

	public Message(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}
}
