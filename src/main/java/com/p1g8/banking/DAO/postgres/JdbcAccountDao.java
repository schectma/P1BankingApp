package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.dao.AccountDao;
import com.p1g8.banking.exception.DataAccessException;
import com.p1g8.banking.exception.DuplicateDataException;
import com.p1g8.banking.model.AccountStatus;
import com.p1g8.banking.model.AccountType;
import com.p1g8.banking.model.BankAccount;
import org.postgresql.util.PSQLState;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class JdbcAccountDao implements AccountDao {

    private final ConnectionProvider connectionProvider;  //datasource is the standard interface for obtaining database connections -- replacement for DriverManager

    public JdbcAccountDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    //Let's create a bank account first
    @Override
    public BankAccount create(BankAccount account) {
        String sql = """
            INSERT INTO bank_accounts
            (customer_id, account_number, account_type, balance, status)
            VALUES (?, ?, ?, ?, ?)
            RETURNING *\s  
           \s""";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setLong(1, account.customerId());
            ps.setString(2, account.accountNumber());
            ps.setString(3, String.valueOf(account.type()));
            ps.setBigDecimal(4, account.balance());
            ps.setString(5, String.valueOf(account.status()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            throw new DataAccessException("Failed to create bank account", null);
        } catch (SQLException e) {
            if (PSQLState.UNIQUE_VIOLATION.getState().equals(e.getSQLState())) {
                throw new DuplicateDataException("Account Number already exists", e);
            }
            throw new DuplicateDataException("Failed to create bank account", e);
        }
    }
     // Find account by ID
     @Override
        public Optional<BankAccount> findById ( long accountId) {

         String sql = """
                
                 SELECT * FROM bank_accounts
                WHERE id = ?
                """;

        try(Connection conn = connectionProvider.
              getConnection();
            PreparedStatement ps = conn.prepareStatement(

             sql)){

            ps.setLong(1, accountId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapRow(rs)) ;
                }
            }
            return Optional.empty();
        } catch (
         SQLException e){
            throw new DataAccessException("Failed to find account by Id", e);
        }
}


// Let's find by account number
    @Override
    public Optional<BankAccount> findByAccountNumber (String accountNumber){
        String sql = """
                SELECT * FROM bank_accounts
                WHERE account_number = ?
                """;

        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        }catch (SQLException e) {
            throw new DataAccessException("Failed to find account by account number", e);
        }
    }
//Let's find all accounts belonging to a customer
        @Override
        public List<BankAccount> findByCustomerId ( long customerId) {

            String sql = """
                    SELECT * FROM bank_accounts
                    WHERE customer_id = ?
                    """;

            List<BankAccount> accounts = new ArrayList<>();
            try (Connection conn = connectionProvider.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, customerId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        accounts.add(mapRow(rs));
                    }
                }
                return accounts;
            } catch (SQLException e) {
                throw new DataAccessException("Failed to find customer accounts", e);
            }
        }

// Let's update account status
    @Override
    public boolean updateStatus ( long accountId, AccountStatus status) {
        String sql = """
                UPDATE bank_accounts
                SET status = ?,
                    status_changed_at = CURRENT_TIMESTAMP
                WHERE id = ?   \s
               \s""";
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setLong(2, accountId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update account status", e);
        }
    }
// Let's update account balance
    @Override
    public boolean updateBalance ( long accountId, BigDecimal balance){
        String sql = """
                UPDATE bank_accounts
                SET balance = ?
                WHERE id = ?
                """;
        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, balance);
            ps.setLong(2, accountId);

            return ps.executeUpdate() > 0;

        } catch(SQLException e) {
            throw new DataAccessException("Failed to update account balance", e);
        }
    }
// Let's Delete account by ID
    @Override
    public boolean deleteById ( long accountId){
        String sql = """
                DELETE FROM bank_accounts
                WHERE id = ?
                """;
        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1, accountId);

            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            throw new DataAccessException("Failed to delete bank account", e);
        }
    }


    //Convert ResultSet into bankAccount
  BankAccount mapRow(ResultSet rs) throws SQLException{
        return new BankAccount(rs.getLong("id"),
                                rs.getLong("customer_id"),
                                rs.getString("account_number"),
                                AccountType.valueOf(rs.getString("account_type")),
                                rs.getBigDecimal("balance"),
                                AccountStatus.valueOf(rs.getString("status")),
                                rs.getTimestamp("created_at").toInstant()
                );
    }

}