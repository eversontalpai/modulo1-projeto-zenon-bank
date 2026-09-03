package br.com.zenon;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.model.enumerate.Type;
import br.com.zenon.reader.CsvReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        CsvReader csvReader = new CsvReader();
        try {
            List<String> lines = csvReader.readCsv("/Users/eversontalpai/projetos/pessoal/modulo1-projeto-zenon-bank/data");
            List<Transaction> transactions = lines.stream()
                    .map(line ->{
                        String[] coluns = line.split(",");
                        return new Transaction(
                                coluns[0],
                                Type.valueOf(coluns[1]),
                                new BigDecimal(coluns[2]),
                                coluns[3],
                                new BigDecimal(coluns[4]),
                                new BigDecimal(coluns[5]),
                                coluns[6],
                                new BigDecimal(coluns[7]),
                                new BigDecimal(coluns[8]),
                                Boolean.parseBoolean(coluns[9]),
                                Boolean.parseBoolean(coluns[10])


                        );
                    })
                    .toList();
            IO.println("Quantidade: "+transactions.size());
            transactions.forEach(IO::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
