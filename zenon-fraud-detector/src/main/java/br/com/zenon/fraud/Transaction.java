package br.com.zenon.fraud;

import br.com.zenon.model.enumerate.Type;

import java.math.BigDecimal;

public record Transaction(
        String step,
        Type type,
        BigDecimal amount,
        String nameOrig,
        BigDecimal oldbalanceOrg,
        BigDecimal newbalanceOrig,
        String nameDest,
        BigDecimal oldbalanceDest,
        BigDecimal newbalanceDest,
        boolean isFraud,
        boolean isFlaggedFraud) {
}
