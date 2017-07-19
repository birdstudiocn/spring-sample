package cn.birdstudio.service;

import org.springframework.stereotype.Component;

import cn.birdstudio.domain.User;

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
	public User getCity(String name) {
		return userRepository.findByNameAllIgnoringCase(name);
	}

}
