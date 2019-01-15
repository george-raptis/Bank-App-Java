package com.customers;

import com.database.Database;
import com.menu.BasicMenu;
import com.menu.LoginMenu;
import com.menu.PremiumMenu;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Basic extends Customers {

    public int deposit(String username) {

        System.out.println("Type the amount you want to deposit:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        int oldBalance = checkBalance(username);
        int newBalance = oldBalance + Integer.parseInt(input) - 10;

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
        int newBalance = oldBalance - Integer.parseInt(input) - 10;

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

    public void upgrade(String username) {

        System.out.println("Are you sure you want to upgrade to premium?\n" +
                "1. Yes\n2. No");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        switch (input) {
            case "1":
                payPremium(username);
                PremiumMenu.premiumMenu();
                break;
            case "2":
                BasicMenu.basicMenu();
                break;
            default:
                upgrade(username);
                break;
        }

    }

    private static void payPremium(String username) {

        int oldBalance = LoginMenu.rememberBalance;
        int newBalance = oldBalance - 50;

        Database.connect(url, "root", "12345");

        String query = "update bankdb.customer set c_deposit = " + newBalance + ", c_type = 2 where c_username = '" + username + "'";
        PreparedStatement stm;

        try {
            stm = Database.connection.prepareStatement(query);
            stm.executeUpdate();
            System.out.println("Your account has been upgraded to premium.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void premiumInfo() {
        System.out.println("Upgrade you account to premium for only 50 euros,\n" +
                "and enjoy lowered transaction fees right after purchase!");
    }

}
