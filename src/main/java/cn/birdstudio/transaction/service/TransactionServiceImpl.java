package cn.birdstudio.transaction.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.birdstudio.Application;
import cn.birdstudio.jms.Producer;
import cn.birdstudio.jms.Type;
import cn.birdstudio.transaction.domain.Transaction;
import cn.birdstudio.transaction.domain.TransactionRepository;

@Component("transactionService")
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private TransactionRepository transactionRepository;

	@Resource
	private Producer producer;

	@Resource
	private Queue queue;

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
		Map<String, Object> sellerMsg = new HashMap<>();
		sellerMsg.put("id", seller_id);
		sellerMsg.put("amount", amount);
		sellerMsg.put("type", Type.SELLER.toString());
		producer.send(queue, sellerMsg);
		Map<String, Object> buyerMsg = new HashMap<>();
		buyerMsg.put("id", buyer_id);
		buyerMsg.put("amount", amount);
		buyerMsg.put("type", Type.BUYER.toString());
		producer.send(queue, buyerMsg);
		logger.info("Send transaction queue");
	}
}
