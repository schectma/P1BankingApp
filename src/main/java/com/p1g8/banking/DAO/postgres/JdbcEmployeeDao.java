package com.p1g8.banking.dao.postgres;

import com.p1g8.banking.config.ConnectionProvider;
import com.p1g8.banking.dao.EmployeeDao;
import com.p1g8.banking.exception.DataAccessException;
import com.p1g8.banking.Model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import com.p1g8.banking.dao.EmployeeDao;

public class JdbcEmployeeDao implements EmployeeDao {
    private final ConnectionProvider connectionProvider;

    public JdbcEmployeeDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    //Let's find employee by ID
    @Override
    public Optional<Employee> findById(long employeeId) {
        String sql = """
                SELECT * FROM employees
                WHERE id = ?
                """;
        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setLong(1, employeeId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch(SQLException e){
            throw new DataAccessException("Failed to find employee by ID", e);
        }

    }
//Let's find employee by username
    @Override
    public Optional<Employee> findByUserName(String userName) {
        String sql = """
                SELECT * FROM employees
                WHERE user_name = ?
                """;
        try(Connection conn = connectionProvider.getConnection();
           PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, userName);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e){
            throw new DataAccessException("Failed to find employee by username", e);
        }
    }


    //Create mapRow() method -> is a helper method that converts a row retrieved from PostgreSQL into a Java Employee Obj.
    private Employee mapRow(ResultSet rs) throws SQLException{
        return new Employee(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("user_name"),
                rs.getString("password_hash"),
                rs.getTimestamp("created_at").toInstant()
        );
    }

}
