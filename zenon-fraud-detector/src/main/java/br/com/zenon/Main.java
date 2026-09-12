package br.com.zenon;

import br.com.zenon.analyzer.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.report.TransactionReport;
import br.com.zenon.repository.TransactionRepository;
import br.com.zenon.repository.TransactionalRepositoryImpl;
import br.com.zenon.repository.TransactionalRepositoryMapImpl;

import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

//        fixErrors(repository);
//        analyzerFraud();
//        calculateBenchmark();
        printReport();


    }

    private static void printReport() {
        TransactionReport report = new TransactionReport("PS_20174392719_1491204439457_log.csv");
        report.printReport();
    }

    private static void calculateBenchmark() {
        var startList = System.nanoTime();
        benchmark();
        var endList = System.nanoTime();
        var startMap = System.nanoTime();
        benchmarkWithMap();
        var endMap = System.nanoTime();

        IO.println("List: "+ (endList-startList));
        IO.println("Map: "+ (endMap-startMap));
    }

    private static void fixErrors() {
        TransactionRepository repository = new TransactionalRepositoryImpl("paysim_with_bad_data.csv", 1_000);
        List<Transaction> transactions = repository.findAll();
        transactions.forEach(IO::println);
    }

    private static void analyzerFraud() {
        TransactionRepository repository = new TransactionalRepositoryImpl("PS_20174392719_1491204439457_log.csv", 50_000);
        IO.println("começou: "+ LocalDateTime.now());
        List<Transaction> all = repository.findAll();
        IO.println("terminou: "+ LocalDateTime.now());
        FraudAnalyzer analyzer = new FraudAnalyzer(all);

        IO.println("1. Total de Fraudes: "+ analyzer.countTransactionsIsFraudSize());
        IO.println("2. Top 3 Fraudes de Maior valor: ");
        analyzer.printTop3Frauds();
        IO.println("3. Clientes Suspeitos: ");
        analyzer.getTop5Suspects().forEach(IO::println);
        IO.println("4. Prejuízo Total: "+analyzer.totalLoss());
        IO.println("5. Fraudes por Tipo: ");
        analyzer.countByType().forEach((key, value) -> IO.println("   - " + key + ": " + value));
    }
    private static void benchmark() {
        TransactionRepository repository = new TransactionalRepositoryImpl("PS_20174392719_1491204439457_log.csv", 10_000_000);
        findName(repository);

    }

    private static void benchmarkWithMap() {
        TransactionRepository repository = new TransactionalRepositoryMapImpl("PS_20174392719_1491204439457_log.csv", 10_000_000);
        findName(repository);
    }

    private static void findName(TransactionRepository repository) {
        List<String> names = List.of("C1231006815","C12345");
        names.forEach(name->{
                repository.findByName(name)
                        .ifPresentOrElse(IO::println,()-> IO.println("Transação não encontrada para o cliente "+name));
        });
    }


}
