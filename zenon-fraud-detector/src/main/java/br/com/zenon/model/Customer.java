package br.com.zenon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@ToString
@NoArgsConstructor
public class Customer {
    String nameOrig;
    BigDecimal oldbalanceOrg;
    BigDecimal newbalanceOrig;

    public Customer(String nameOrig, BigDecimal oldbalanceOrg, BigDecimal newbalanceOrig) {
        validateObject(nameOrig,oldbalanceOrg,newbalanceOrig);
        this.nameOrig = nameOrig;
        this.oldbalanceOrg = oldbalanceOrg;
        this.newbalanceOrig = newbalanceOrig;
    }

    private void validateObject(String name, BigDecimal oldbalanceOrg, BigDecimal newbalanceOrig){
        if(name.isEmpty()){
            throw new IllegalArgumentException("name should not be empty");
        }
        if(BigDecimal.ZERO.compareTo(oldbalanceOrg) > 0){
            throw new IllegalArgumentException("oldBalance should be positive: "+oldbalanceOrg);
        }
        if(BigDecimal.ZERO.compareTo(newbalanceOrig) > 0){
            throw new IllegalArgumentException("newbalanceOrig should be positive: "+newbalanceOrig);
        }


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
