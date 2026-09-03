package br.com.zenon;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.model.Customer;
import br.com.zenon.model.enumerate.Type;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        CsvReader csvReader = new CsvReader();
        try {
            List<String> lines = csvReader.readCsv("/Users/eversontalpai/projetos/pessoal/modulo1-projeto-zenon-bank/data");
            List<String> errors = new ArrayList<>();
            List<Transaction> transactions = new ArrayList<>();
                    lines.stream()
                    .forEach(line ->{
                        try {
                            String[] coluns = line.split(",");
                            int step = Integer.parseInt(coluns[0]);
                            if(step <= 0){
                                throw new IllegalArgumentException("step should be positive: "+step);
                            }

                            BigDecimal amount = new BigDecimal(coluns[2]);
                            if(BigDecimal.ZERO.compareTo(amount) > 0){
                                throw new IllegalArgumentException("amount should be positive: "+amount);
                            }
                            transactions.add( new Transaction(
                                    step,
                                    Type.valueOf(coluns[1]),
                                    amount,
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
                            errors.add( "Erro: "+line+ e.getClass().getName()+": "+e.getMessage());
                        }
                    });
            errors.forEach(IO::println);
            IO.println(errors.size());
            transactions.forEach(IO::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
