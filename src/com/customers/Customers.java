package com.customers;

import com.database.Database;
import com.menu.AdminMenu;
import com.menu.BasicMenu;
import com.menu.LoginMenu;
import sun.rmi.runtime.Log;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customers {

    private String username;
    private String fName;
    private String lName;
    private int depositAmount;

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Customers.url = url;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "username='" + username + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }

    static String url = "jdbc:mysql://localhost:3306/bankdb?useSSL=false";

    public int checkLogin(String password, String username) {

        Database.connect(url, "root", "12345");

        String query2 = "select count(*) as count from bankdb.customer where c_password = '" + password + "' and c_username = '" + username + "'";
        PreparedStatement stm2;
        int check = 0;

        try {
            stm2 = Database.connection.prepareStatement(query2);
            ResultSet rs = stm2.executeQuery();
            while (rs.next()) {
                check = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public void wrongLogin() {
        System.out.println("Wrong username or password. Please try again");
        LoginMenu.loginMenu();
    }

    public String checkRole(String username) {

            Database.connect(url, "root", "12345");

            String queryrole = "select c_type as role from bankdb.customer where c_username = '" + username + "'";
            PreparedStatement stmrole;
            String checkrole = null;
            try {
                stmrole = Database.connection.prepareStatement(queryrole);
                ResultSet rs = stmrole.executeQuery();
                while (rs.next()) {
                    checkrole = rs.getString("role");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    Database.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return checkrole;
    }

    public int checkIfExists(String username) {

        Database.connect(url, "root", "12345");

        String query = "select count(*) as count from bankdb.customer where c_username = '" + username + "'";
        PreparedStatement stm;
        int checkusername = 0;

        try {
            stm = Database.connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                checkusername = rs.getInt("count");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                Database.connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return checkusername;

    }

    public int checkBalance(String username) {

        Database.connect(url, "root", "12345");

        String query = "select c_deposit as deposit from bankdb.customer where c_username = '" + username + "'";
        PreparedStatement stm;
        int deposit = 0;

        try {
            stm = Database.connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                deposit = rs.getInt("deposit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Database.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deposit;
    }

    public int deposit(String username) {

        System.out.println("Type the amount you want to deposit:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        int oldBalance = checkBalance(username);
        int newBalance = oldBalance + Integer.parseInt(input);

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
        int newBalance = oldBalance - Integer.parseInt(input);

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

    public void returnToMenu(String username) {
        System.out.println("\n1. Return to menu");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (input.equals("1")) {
            String role = checkRole(username);
            switch (role) {
                case "0":
                    AdminMenu.adminMenu();
                    break;
                case "1":
                    BasicMenu.basicMenu();
                    break;
            }
        } else {
            returnToMenu(username);
        }
    }

}
