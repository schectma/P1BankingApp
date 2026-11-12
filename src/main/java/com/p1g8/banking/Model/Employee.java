package com.p1g8.banking.Model;

public class Employee {
    private long employeeId;
    private String firstName;
    private String lastName;
    private String userName;

    private String passwordHash;

   private boolean active;

   @Override
    public String toString(){
       return "Employee[Id = " + employeeId + ", firstName = " + firstName +
               ", lastName = " + lastName + ", user = " + userName +
               ", active = " + active + "]";
   }


}
