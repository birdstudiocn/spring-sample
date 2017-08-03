package cn.birdstudio.transaction.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.transaction.domain.Transaction;
import cn.birdstudio.transaction.domain.TransactionRepository;

@Component("transactionService")
public class TransactionServiceImpl implements TransactionService {
	private TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction getTransaction(int id) {
		Transaction transaction = null;
		Optional<Transaction> optional = transactionRepository.findById(id);
		if (optional.isPresent())
			transaction = transactionRepository.findById(id).get();
		return transaction;
	}

	@Override
	@Transactional("transactionManager")
	public void sold(int seller_id, int buyer_id, int amount) {
		Transaction transaction = new Transaction();
		transaction.setSeller_id(seller_id);
		transaction.setBuyer_id(buyer_id);
		transaction.setAmount(amount);
		transactionRepository.save(transaction);
	}
}
