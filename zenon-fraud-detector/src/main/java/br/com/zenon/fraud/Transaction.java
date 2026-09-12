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

    public Transaction {
        if(step <= 0){
            throw new IllegalArgumentException("step should be positive: "+step);
        }
        if(BigDecimal.ZERO.compareTo(amount) > 0){
            throw new IllegalArgumentException("amount should be positive: "+amount);
        }
    }
    public static Transaction creatTransaction(String line) {
        try {
            String[] coluns = line.split(",");
            return new Transaction(
                    Integer.parseInt(coluns[0]),
                    Type.valueOf(coluns[1]),
                    new BigDecimal(coluns[2]),
                    new Customer(
                            coluns[3],
                            new BigDecimal(coluns[4]),
                            new BigDecimal(coluns[5])
                    ),
                    new Customer(
                            coluns[6],
                            new BigDecimal(coluns[7]),
                            new BigDecimal(coluns[8])
                    ),
                    coluns[9].equals("1"),
                    coluns[10].equals("1")


            );
        }catch (IllegalArgumentException e){
            IO.println( "Erro: "+ line + e.getClass().getName()+": "+e.getMessage());
        }
        return null;
    }
}
