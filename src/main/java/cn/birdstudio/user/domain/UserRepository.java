package cn.birdstudio.user.domain;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
	User save(User entity);

	User findByNameAllIgnoringCase(String name);
}
