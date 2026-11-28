package br.com.zenon.repository;

import br.com.zenon.config.ConnectionFactory;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class TransactionRepositoryImpl implements TransactionRepository{
    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>(7_000_000);
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(selectBuilder())
        ){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    transactions.add(Transaction.creatTransaction(rs));
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public Optional<Transaction> findByName(String name) {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(selectBuilder("name_origin"))
        ){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                   return Optional.of(Transaction.creatTransaction(rs));
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void saveAll(String fileName, Integer limit) {
        List<Transaction> transactions = read(fileName, limit);
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(insertBuilder())
        ){
           transactions.forEach(transaction -> {
               try {
                   Transaction.prepareteTransaction(ps,transaction);
                   ps.execute();
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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


    private String selectBuilder(String... attributes){
        StringBuilder sb = new StringBuilder();
        sb.append("""
                 SELECT id, step, type, amount, name_origin, old_balance_origin,
                        new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient,
                        is_fraud, is_flagged_fraud
                        FROM zenon.transactions
                """);
        if(nonNull(attributes) && attributes.length > 0){
            sb.append(
                    Arrays.stream(attributes)
                            .map(a->a + " = ?")
                            .collect(Collectors.joining(" AND ", " WHERE ", ""))
            );
        }

        return sb.toString();
    }
    private String insertBuilder(){
        return """
                 INSERT INTO zenon.transactions
                  (step, type, amount, name_origin, old_balance_origin,
                        new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient,
                        is_fraud, is_flagged_fraud)
                  VALUES (?,?,?,?,?,?,?,?,?,?,?)
                """;
    }
}
