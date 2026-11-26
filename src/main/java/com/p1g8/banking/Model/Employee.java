package com.p1g8.banking.Model;

import java.time.Instant;

public class Employee {
   private long employeeId;
   private String firstName;
   private String lastName;
   private String userName;

   private String passwordHash;

    public Employee(long employeeId, String firstName, String lastName, String userName, String passwordHash, Instant createdAt) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    private Instant createdAt;

   @Override
   public String toString(){
      return "Employee[Id = " + employeeId + ", firstName = " + firstName +
            ", lastName = " + lastName + ", user = " + userName + "]";
   }
}