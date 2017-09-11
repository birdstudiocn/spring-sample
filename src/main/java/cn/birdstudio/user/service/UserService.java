package cn.birdstudio.user.service;

import java.util.Map;

import cn.birdstudio.user.domain.User;

public interface UserService {
	User addUser(User entity);

	User getUser(String name);

	void receiveQueue(Map<String, Object> msg);

	void receivekafka(Map<String, Object> msg);
}
