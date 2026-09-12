package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.model.Customer;
import br.com.zenon.model.enumerate.Type;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionalRepositoryMapImpl implements TransactionRepository{

    private final Map<String,Transaction> transactions;

    public TransactionalRepositoryMapImpl(String fileName, int limit) {
        this.transactions = process(fileName,limit);
    }

    private Map<String, Transaction> process(String fileName, int limit) {
        try {
            List<String> lines = CsvReader.readCsv(fileName, limit);
            return lines.parallelStream()
                        .map(this::creatTransaction)
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
        return transactions.values().stream().toList();
    }

    @Override
    public Optional<Transaction> findByName(String name) {
        return Optional.ofNullable(transactions.get(name));
    }

    private Transaction creatTransaction(String line) {
        try {
            String[] coluns = line.split(",");
            return new Transaction(
                    Integer.parseInt(coluns[0]),
                    Type.valueOf(coluns[1]),
                    new BigDecimal(coluns[2]),
                    new Customer(
                            coluns[3],
                            new BigDecimal(coluns[4]),
                            new BigDecimal(coluns[5])
                    ),
                    new Customer(
                            coluns[6],
                            new BigDecimal(coluns[7]),
                            new BigDecimal(coluns[8])
                    ),
                    coluns[9].equals("1"),
                    coluns[10].equals("1")


            );
        }catch (IllegalArgumentException e){
            IO.println( "Erro: "+ line + e.getClass().getName()+": "+e.getMessage());
        }
        return null;
    }
}
