package br.com.zenon.model;

import java.math.BigDecimal;

public record Customer(String name, BigDecimal oldbalance, BigDecimal newbalance) {
    public Customer {
        validateObject(name, oldbalance, newbalance);
    }

    private void validateObject(String name, BigDecimal oldbalanceOrg, BigDecimal newbalanceOrig) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        if (BigDecimal.ZERO.compareTo(oldbalanceOrg) > 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldbalanceOrg);
        }
        if (BigDecimal.ZERO.compareTo(newbalanceOrig) > 0) {
            throw new IllegalArgumentException("newbalance should be positive: " + newbalanceOrig);
        }


    }

}
