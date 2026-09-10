package br.com.zenon;

import br.com.zenon.analyzer.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.repository.TransactionRepository;
import br.com.zenon.repository.TransactionalRepositoryImpl;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        TransactionRepository repository = new TransactionalRepositoryImpl();
//        List<Transaction> transactions = repository.findAll("paysim_with_bad_data.csv", 1000);
//        transactions.forEach(IO::println);

        FraudAnalyzer analyzer = new FraudAnalyzer(repository.findAll("PS_20174392719_1491204439457_log.csv", 50_000));

        IO.println("1. Total de Fraudes: "+ analyzer.countTransactionsIsFraudSize());
        IO.println("2. Top 3 Fraudes de Maior valor: ");
        analyzer.printTop3Frauds();
        IO.println("3. Clientes Suspeitos: ");
        analyzer.getTop5Suspects().forEach(IO::println);
        IO.println("4. Prejuízo Total: "+analyzer.totalLoss());
        IO.println("5. Fraudes por Tipo: ");
        analyzer.countByType().forEach((key, value) -> IO.println("   - " + key + ": " + value));





    }
}
