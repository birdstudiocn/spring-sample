package cn.birdstudio.user.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByNameAllIgnoringCase(String name);

	//@Transactional("userTransactionManager")
	@Modifying
	@Query("update #{#entityName} set amt_sold=amt_sold+?2 where id=?1")
	void updateAmtSold(int id, int amount);

	//@Transactional("userTransactionManager")
	@Modifying
	@Query("update #{#entityName} set amt_bought=amt_bought+?2 where id=?1")
	void updateAmtBought(int id, int amount);
}
