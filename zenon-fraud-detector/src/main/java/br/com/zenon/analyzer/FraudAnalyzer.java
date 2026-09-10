package br.com.zenon.analyzer;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.model.enumerate.Type;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FraudAnalyzer {

    private final List<Transaction> transactionsIsFraud;

    public FraudAnalyzer(List<Transaction> transactions) {
        this.transactionsIsFraud = getTransactionIsFraud(transactions);
    }


    private List<Transaction> getTransactionIsFraud(List<Transaction> transactions){
        return transactions.stream().filter(Transaction::isFraud).toList();
    }

    public int countTransactionsIsFraudSize(){
        return transactionsIsFraud.size();
    }

    public void printTop3Frauds(){
        transactionsIsFraud.stream()
                .map(Transaction::amount)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .forEach(v-> IO.println(v.toPlainString()));
    }

    public Set<String> getTop5Suspects(){
        return transactionsIsFraud.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(transaction ->
                                transaction.origin().nameOrig(),
                                Collectors.reducing(BigDecimal.ZERO,Transaction::amount,BigDecimal:: add)
                 ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    public BigDecimal totalLoss(){
        return transactionsIsFraud.stream()
                .filter(Transaction::isFraud)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Map<Type,Integer> countByType(){
        return transactionsIsFraud.stream()
                .filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::type,
                        Collectors.summingInt(transaction -> 1)));

    }




}
