package cn.birdstudio.user.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UpdatesAppliedRepository extends CrudRepository<UpdatesApplied, Integer> {
	@Query("select count(*) from UpdatesApplied t where t.trans_id = ?1 and t.user_id=?2 and t.balance=?3")
	int find(int trans_id, int user_id, String balance);
}
