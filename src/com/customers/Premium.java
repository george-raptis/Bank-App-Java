package com.customers;

import com.database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Premium extends Customers {

    public int deposit(String username) {

        System.out.println("Type the amount you want to deposit:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        int oldBalance = checkBalance(username);
        int newBalance = oldBalance + Integer.parseInt(input) - 2;

        Database.connect(url, "root", "12345");

        PreparedStatement stm;
        String query = "update bankdb.customer set c_deposit = " + newBalance + " where c_username = '" + username + "'";

        try {
            stm = Database.connection.prepareStatement(query);
            stm.executeUpdate();
            System.out.println("\nYour bank balance is now: " + newBalance);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return newBalance;
    }

    public int withdraw(String username) {

        System.out.println("Type the amount you want to withdraw:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        int oldBalance = checkBalance(username);
        int newBalance = oldBalance - Integer.parseInt(input) - 2;

        if (newBalance >= 0) {
            Database.connect(url, "root", "12345");

            PreparedStatement stm;
            String query = "update bankdb.customer set c_deposit = " + newBalance + " where c_username = '" + username + "'";

            try {
                stm = Database.connection.prepareStatement(query);
                stm.executeUpdate();
                System.out.println("\nYour bank balance is now: " + newBalance);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    Database.connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("You can't can't withdraw that amount of money.\n1. Try again.\n2. Back to the menu");
            withdraw(username);
        }
        return newBalance;
    }

}
