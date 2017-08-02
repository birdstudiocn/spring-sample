package cn.birdstudio.transaction.domain;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface TransactionRepository extends Repository<Transaction, Long> {
	Optional<Transaction> findById(Long id);
}
