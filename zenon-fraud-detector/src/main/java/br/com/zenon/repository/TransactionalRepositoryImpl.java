package br.com.zenon.repository;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.model.Customer;
import br.com.zenon.model.enumerate.Type;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionalRepositoryImpl implements TransactionRepository{
    @Override
    public List<Transaction> findAll(String fileName, int limit) {
        try {
            List<String> lines = CsvReader.readCsv(fileName, limit);
            List<Transaction> transactions = new ArrayList<>();
            lines
                    .forEach(line ->{
                        try {
                            String[] coluns = line.split(",");
                            transactions.add( new Transaction(
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
                                    Boolean.parseBoolean(coluns[9]),
                                    Boolean.parseBoolean(coluns[10])


                            ));
                        }catch (IllegalArgumentException e){
                           IO.println( "Erro: "+line+ e.getClass().getName()+": "+e.getMessage());
                        }
                    });
            return transactions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
