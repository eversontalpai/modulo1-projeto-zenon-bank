package br.com.zenon.report;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.reader.CsvReaderWithStream;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
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
    private record FormattedData(ResourceBundle report, String formattedTotalLines, String formattedTotalFruads, String formattedTotalAmount) {
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


    public void printReport(String language){
        FormattedData formatted = getFormattedData(language);

        IO.println(formatted.report().getString("total.lines")+": "+ formatted.formattedTotalLines());
        IO.println(formatted.report().getString("total.frauds")+": "+ formatted.formattedTotalFruads());
        IO.println(formatted.report().getString("total.amount")+": "+ formatted.formattedTotalAmount());
    }

    private FormattedData getFormattedData(String language) {
        Locale locale = Locale.of(language);
        NumberFormat integerInstance = NumberFormat.getIntegerInstance(locale);
        NumberFormat currencyInstance = DecimalFormat.getCurrencyInstance(locale);
        currencyInstance.setCurrency(Currency.getInstance("USD"));

        ResourceBundle report = ResourceBundle.getBundle("report", locale);

        return new FormattedData(report,
                integerInstance.format(this.statistics.totalLines),
                integerInstance.format(this.statistics.totalFrauds),
                currencyInstance.format(this.statistics.totalAmount));
    }



}
