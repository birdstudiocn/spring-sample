package cn.birdstudio.user.service;

import java.util.Map;

import cn.birdstudio.jms.TransactionMessage;
import cn.birdstudio.user.domain.User;

public interface UserService {
	User addUser(User entity);

	User getUser(String name);

	void receiveQueue(Map<String, Object> msg);

	void receivekafka(TransactionMessage msg);
}
