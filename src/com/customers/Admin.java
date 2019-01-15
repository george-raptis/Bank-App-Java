package com.customers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.database.Database;
import com.menu.AdminMenu;

public class Admin extends Customers {

    public void registerCustomer() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type the username:");
        String username = scan.next();
        Customers customers = new Customers();

        int exists = customers.checkIfExists(username);

        if (exists == 0) {

            System.out.println("Type the password of the new customer:");
            String password = scan.next();
            System.out.println("Type the first name of the new customer:");
            String fName = scan.next();
            System.out.println("Type the last name of the new customer:");
            String lname = scan.next();
            System.out.println("Type the ID number of the new customer:");
            String id = scan.next();
            System.out.println("Type the amount of the first deposit of the new customer:");
            int deposit = scan.nextInt();

            Database.connect(url, "root", "12345");

            String query = "insert into bankdb.customer values (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stm1;

            try {
                stm1 = Database.connection.prepareStatement(query);
                stm1.setString(1, null);
                stm1.setString(2, username);
                stm1.setString(3, password);
                stm1.setString(4, fName);
                stm1.setString(5, lname);
                stm1.setString(6, id);
                stm1.setInt(7, deposit);
                stm1.setInt(8, 1);
                stm1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    Database.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("The customer was created successfully!");
            AdminMenu.adminMenu();
        } else {
            System.out.println("The username you entered already exists\n1. Try again"
                    + "\nAnything else to go back to the menu");

            String choose = scan.next();

            if (choose.equals("1")) {
                registerCustomer();
            } else {
                AdminMenu.adminMenu();
            }
        }
    }
}
