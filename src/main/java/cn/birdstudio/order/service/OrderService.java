package cn.birdstudio.order.service;

public interface OrderService {
	void sold(int seller_id, int buyer_id, int amount);
}
