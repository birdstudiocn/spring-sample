package cn.birdstudio.user.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	@Transactional("userTransactionManager")
	public void sold(int seller_id, int buyer_id, int amount) {
		userRepository.updateAmtSold(seller_id, amount);
		userRepository.updateAmtBought(buyer_id, amount);
	}
}
