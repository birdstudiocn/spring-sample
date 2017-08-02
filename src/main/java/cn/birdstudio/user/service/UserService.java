package cn.birdstudio.user.service;

import cn.birdstudio.user.domain.User;

public interface UserService {
	User addUser(User entity);

	User getUser(String name);
}
