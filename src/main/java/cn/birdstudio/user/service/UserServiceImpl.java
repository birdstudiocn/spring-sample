package cn.birdstudio.user.service;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.jms.Type;
import cn.birdstudio.user.domain.User;
import cn.birdstudio.user.domain.UserRepository;

@Component("userService")
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User addUser(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User getUser(String name) {
		return userRepository.findByNameAllIgnoringCase(name);
	}

	@Transactional("userTransactionManager")
	private void sold(Map<String, Object> msg) {
		Type type = Type.valueOf(msg.get("type").toString());
		int id = Integer.valueOf(msg.get("id").toString());
		int amount = Integer.valueOf(msg.get("amount").toString());
		switch (type) {
		case SELLER:
			userRepository.updateAmtSold(id, amount);
			break;
		case BUYER:
			userRepository.updateAmtBought(id, amount);
			break;
		}
	}

	@Override
	@JmsListener(destination = "transaction")
	public void receiveQueue(Map<String, Object> msg) {
		sold(msg);
	}
}
