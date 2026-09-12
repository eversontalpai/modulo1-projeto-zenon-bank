package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionalRepositoryImpl implements TransactionRepository{
    private final List<Transaction> transactions;

    public TransactionalRepositoryImpl(String fileName, int limit) {
        this.transactions = read(fileName,limit);
    }

    private List<Transaction> read(String fileName, int limit) {
        try {
            return CsvReader.readCsv(fileName,limit)
                    .parallelStream()
                    .map(Transaction::creatTransaction)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findAll() {
            return transactions;
    }


    @Override
    public Optional<Transaction> findByName(String name) {
            return transactions
                    .stream()
                    .filter(transaction -> transaction.origin().name().equals(name))
                    .findAny();
    }


}
