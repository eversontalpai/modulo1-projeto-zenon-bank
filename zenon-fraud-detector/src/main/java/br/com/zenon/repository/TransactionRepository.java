package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionRepository {
    List<Transaction> findAll();
    Optional<Transaction> findByName(String name);
}
