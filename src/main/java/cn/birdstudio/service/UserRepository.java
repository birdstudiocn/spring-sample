package cn.birdstudio.service;

import org.springframework.data.repository.Repository;

import cn.birdstudio.domain.User;

public interface UserRepository extends Repository<User, Long> {
	User save(User entity);

	User findByNameAllIgnoringCase(String name);
}
