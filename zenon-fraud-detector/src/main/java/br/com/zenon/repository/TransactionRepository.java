package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll(String fileName, int limit);
}
