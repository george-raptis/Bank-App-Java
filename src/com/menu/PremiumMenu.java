package com.menu;

import com.customers.Customers;
import com.customers.Premium;

import java.util.Scanner;

public class PremiumMenu {

    public static void premiumMenu() {

        System.out.println("Welcome to the premium bank menu!\n" +
                "1. Check Balance\n" +
                "2. Deposit Funds\n" +
                "3. Withdraw Funds\n" +
                "4. Logout");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        Customers customers = new Customers();
        Premium premium = new Premium();

        switch (input) {
            case "1":
                System.out.println("Your account balance is: " + LoginMenu.rememberBalance);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "2":
                premium.deposit(LoginMenu.rememberUsername);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "3":
                premium.withdraw(LoginMenu.rememberUsername);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "4":
                LoginMenu.rememberUsername = "";
                LoginMenu.loginMenu();
            default:
                premiumMenu();
                break;
        }

    }
}
