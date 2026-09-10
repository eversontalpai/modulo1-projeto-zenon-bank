package br.com.zenon;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.repository.TransactionRepository;
import br.com.zenon.repository.TransactionalRepositoryImpl;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        TransactionRepository repository = new TransactionalRepositoryImpl();
        List<Transaction> transactions = repository.findAll("paysim_with_bad_data.csv", 1000);
        transactions.forEach(IO::println);


    }
}
