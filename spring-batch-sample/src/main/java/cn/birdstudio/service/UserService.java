package cn.birdstudio.service;

import cn.birdstudio.domain.User;

public interface UserService {
	User addUser(User entity);

	User getUser(String name);
}
