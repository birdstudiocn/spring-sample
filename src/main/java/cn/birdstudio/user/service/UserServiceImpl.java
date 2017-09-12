package cn.birdstudio.user.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.jms.TransactionMessage;
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
	private void sold(TransactionMessage msg) {
		Type type = msg.getType();
		int id = msg.getId();
		int amount = msg.getAmount();
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
		//sold(msg);
	}

	@Override
	@KafkaListener(groupId = "group1", topics = "transaction")
	//@KafkaListener(groupId = "group1", topicPartitions = @TopicPartition(topic = "", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "5")))
	public void receivekafka(TransactionMessage msg) {
		logger.info("receive kafka message {}", msg);
		sold(msg);
	}
}
