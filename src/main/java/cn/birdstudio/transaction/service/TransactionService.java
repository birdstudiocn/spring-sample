package cn.birdstudio.transaction.service;

import cn.birdstudio.transaction.domain.Transaction;

public interface TransactionService {
	Transaction getTransaction(long name);
}
