package br.com.zenon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
public class Customer {
    String nameOrig;
    BigDecimal oldbalanceOrg;
    BigDecimal newbalanceOrig;

    public Customer(String nameOrig, BigDecimal oldbalanceOrg, BigDecimal newbalanceOrig) {
        this.nameOrig = nameOrig;
        this.oldbalanceOrg = oldbalanceOrg;
        this.newbalanceOrig = newbalanceOrig;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(nameOrig, customer.nameOrig) && Objects.equals(oldbalanceOrg, customer.oldbalanceOrg) && Objects.equals(newbalanceOrig, customer.newbalanceOrig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOrig, oldbalanceOrg, newbalanceOrig);
    }
}
