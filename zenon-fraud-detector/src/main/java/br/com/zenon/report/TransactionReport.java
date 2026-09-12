package br.com.zenon.report;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReaderWithStream;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionReport {
    private Long totalLines = 0L;
    private Long totalFrauds = 0L;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public TransactionReport(String fileName) {

        try(Stream<String> transactions =CsvReaderWithStream.readCsv(fileName)){

            transactions
                    .map(Transaction::creatTransaction)
                    .filter(Objects::nonNull)
                    .forEach(transaction -> {

                this.totalLines ++;
                if(transaction.isFraud()){
                    this.totalFrauds ++;

                }
                this.totalAmount = this.totalAmount.add(transaction.amount());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void printReport(){
        IO.println("Total de linhas: "+totalLines);
        IO.println("Total de fraudes: "+totalFrauds);
        IO.println("Valor total transacionado: %.2f".formatted(totalAmount));
    }


}
