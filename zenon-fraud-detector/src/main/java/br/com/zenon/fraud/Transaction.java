package br.com.zenon.fraud;

import br.com.zenon.model.Customer;
import br.com.zenon.model.enumerate.Type;

import java.math.BigDecimal;

public record Transaction(
        Integer step,
        Type type,
        BigDecimal amount,
        Customer origin,
        Customer recipient,
        boolean isFraud,
        boolean isFlaggedFraud) {

}
