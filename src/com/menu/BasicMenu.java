package com.menu;

import com.customers.Basic;
import com.customers.Customers;

import java.util.Scanner;

public class BasicMenu {

    public static void basicMenu() {
        System.out.println("Welcome to the bank menu!\n" +
                "1. Check Balance\n" +
                "2. Deposit Funds\n" +
                "3. Withdraw Funds\n" +
                "4. Upgrade to premium (50 euros)\n" +
                "5. Check premium info\n" +
                "6. Logout");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        Customers customers = new Customers();
        Basic basic = new Basic();

        switch (input) {
            case "1":
                System.out.println("Your account balance is: " + LoginMenu.rememberBalance);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "2":
                basic.deposit(LoginMenu.rememberUsername);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "3":
                basic.withdraw(LoginMenu.rememberUsername);
                customers.returnToMenu(LoginMenu.rememberUsername);
                break;
            case "4":
                basic.upgrade(LoginMenu.rememberUsername);
                break;
            case "5":
                basic.premiumInfo();
                customers.returnToMenu(LoginMenu.rememberUsername);
            case "6":
                LoginMenu.rememberUsername = "";
                LoginMenu.loginMenu();
            default:
                basicMenu();
                break;
        }
    }
}
