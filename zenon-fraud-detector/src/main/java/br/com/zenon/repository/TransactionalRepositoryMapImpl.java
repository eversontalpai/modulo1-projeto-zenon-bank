package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionalRepositoryMapImpl implements TransactionRepository{

    private final Map<String,Transaction> transactions;

    public TransactionalRepositoryMapImpl(String fileName, int limit) {
        this.transactions = read(fileName,limit);
    }

    private Map<String,Transaction> read(String fileName, int limit) {
        try{
            List<String> lines = CsvReader.readCsv(fileName, limit);
            return lines
                    .parallelStream()
                    .map(Transaction::creatTransaction)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(transaction ->
                                    transaction.origin().name(),
                            Function.identity(),
                            (existing, replacement)-> existing
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findAll() {
            return transactions
                    .values().stream().toList();
    }


    @Override
    public Optional<Transaction> findByName(String name) {
            return Optional.ofNullable(transactions
                    .get(name));

    }

}
