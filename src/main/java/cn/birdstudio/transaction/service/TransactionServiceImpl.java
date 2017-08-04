package cn.birdstudio.transaction.service;

import java.util.Optional;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.Application;
import cn.birdstudio.jms.Message;
import cn.birdstudio.jms.Producer;
import cn.birdstudio.transaction.domain.Transaction;
import cn.birdstudio.transaction.domain.TransactionRepository;

@Component("transactionService")
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private TransactionRepository transactionRepository;

	@Resource
	private Producer producer;

	@Resource(name = "sellerQueue")
	private Queue sellerQueue;

	@Resource(name = "buyerQueue")
	private Queue buyerQueue;

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
		logger.info("Add transaction records");
		Message sellerMsg = new Message(seller_id, amount);
		producer.send(sellerQueue, sellerMsg);
		Message buyerMsg = new Message(buyer_id, amount);
		producer.send(buyerQueue, buyerMsg);
		logger.info("Send transaction queue");
	}
}
