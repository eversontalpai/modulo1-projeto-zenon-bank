package br.com.zenon.fraud;

import br.com.zenon.model.Customer;
import br.com.zenon.model.enumerate.Type;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Transaction(
        Integer step,
        Type type,
        BigDecimal amount,
        Customer origin,
        Customer recipient,
        boolean isFraud,
        boolean isFlaggedFraud) {

    public Transaction {
        if (step <= 0) {
            throw new IllegalArgumentException("step should be positive: " + step);
        }
        if (BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalArgumentException("amount should be positive: " + amount);
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
        } catch (IllegalArgumentException e) {
            IO.println("Erro: " + line + e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public static Transaction creatTransaction(ResultSet rs) {
        try {
            return new Transaction(
                    rs.getInt("step"),
                    Type.valueOf(rs.getString("type")),
                    rs.getBigDecimal("amount"),
                    new Customer(
                            rs.getString("name_origin"),
                            rs.getBigDecimal("old_balance_origin"),
                            rs.getBigDecimal("new_balance_origin")
                    ),
                    new Customer(
                            rs.getString("name_recipient"),
                            rs.getBigDecimal("old_balance_recipient"),
                            rs.getBigDecimal("new_balance_recipient")
                    ),
                    rs.getBoolean("is_fraud"),
                    rs.getBoolean("is_flagged_fraud")


            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void prepareteTransaction(PreparedStatement ps, Transaction transaction) {
        try {
            ps.setInt(1, transaction.step);
            ps.setString(2, transaction.type.name());
            ps.setBigDecimal(3, transaction.amount);
            ps.setString(4, transaction.origin.name());
            ps.setBigDecimal(5, transaction.origin.oldbalance());
            ps.setBigDecimal(6, transaction.origin.newbalance());
            ps.setString(7, transaction.recipient.name());
            ps.setBigDecimal(8, transaction.recipient.oldbalance());
            ps.setBigDecimal(9, transaction.recipient.newbalance());
            ps.setBoolean(10, transaction.isFraud());
            ps.setBoolean(11, transaction.isFlaggedFraud());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
