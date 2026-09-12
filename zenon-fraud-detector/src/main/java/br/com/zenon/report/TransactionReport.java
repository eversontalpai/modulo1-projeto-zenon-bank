package br.com.zenon.report;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReaderWithStream;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionReport {
    private final Statistics statistics;

    private record Statistics(Long totalLines, Long totalFrauds, BigDecimal totalAmount){
        private final static Statistics ZERO = new Statistics(0L,0L,BigDecimal.ZERO);
        private  Statistics add(Transaction transaction){
            return new Statistics(totalLines()+1L,
                    totalFrauds()+(transaction.isFraud()?1L:0L),
                    totalAmount.add(transaction.amount()) );

        }
        private Statistics sum(Statistics other){
            return new Statistics(totalLines +other.totalLines(),
                    totalFrauds +other.totalFrauds(),
                    other.totalAmount.add(other.totalAmount) );

        }
    }

    public TransactionReport(String fileName) {

        try(Stream<String> transactions = CsvReaderWithStream.readCsv(fileName)){
           this.statistics = transactions
                    .map(Transaction::creatTransaction)
                    .filter(Objects::nonNull)
                    .reduce(Statistics.ZERO, Statistics::add, Statistics::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void printReport(){
        IO.println("Total de linhas: "+this.statistics.totalLines());
        IO.println("Total de fraudes: "+this.statistics.totalFrauds());
        IO.println("Valor total transacionado: %.2f".formatted(this.statistics.totalAmount));
    }


}
