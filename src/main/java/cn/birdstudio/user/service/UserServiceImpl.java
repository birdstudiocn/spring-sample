package cn.birdstudio.user.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.jms.Type;
import cn.birdstudio.user.domain.User;
import cn.birdstudio.user.domain.UserRepository;

@Component("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
	//@JmsListener(destination = "transaction")
	public void receiveQueue(Map<String, Object> msg) {
		sold(msg);
	}

	@Override
	@KafkaListener(topics = "transaction")
	public void receivekafka(Map<String, Object> msg) {
		logger.info("receive kafka message {}" + msg);
		sold(msg);
	}
}
