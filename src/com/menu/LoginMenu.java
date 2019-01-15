package com.menu;

import java.util.Scanner;
import com.database.Database;
import com.customers.Customers;

public class LoginMenu {

    static String rememberUsername = "";
    public static int rememberBalance = 0;

    public static void loginMenu() {
        String url = "jdbc:mysql://localhost:3306/bankdb?useSSL=false";
        Database.connect(url, "root", "12345");

        System.out.println("Welcome to the menu!");
        Scanner scan = new Scanner(System.in);

        // username
        System.out.println("Type the username:");
        String username = scan.next();

        // password
        System.out.println("Type the password:");
        String password = scan.next();

        Customers customers = new Customers();
        int checkLogin = customers.checkLogin(password, username);

        if (checkLogin == 1) {
            rememberUsername = username;
            rememberBalance = customers.checkBalance(username);
            String role = customers.checkRole(username);
            System.out.println("role: " + role);
            switch (role) {
                case "0":
                    AdminMenu.adminMenu();
                    break;
                case "1":
                    BasicMenu.basicMenu();
                    break;
                case "2":
                    PremiumMenu.premiumMenu();
                    break;
            }
        } else {
            customers.wrongLogin();
        }
    }
}
