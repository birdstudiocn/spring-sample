package cn.birdstudio.transaction.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import cn.birdstudio.transaction.domain.Transaction;
import cn.birdstudio.transaction.domain.TransactionRepository;

@Component("transactionService")
public class TransactionServiceImpl implements TransactionService {
	private TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction getTransaction(long id) {
		Transaction transaction = null;
		Optional<Transaction> optional = transactionRepository.findById(id);
		if (optional.isPresent())
			transaction = transactionRepository.findById(id).get();
		return transaction;
	}
}
