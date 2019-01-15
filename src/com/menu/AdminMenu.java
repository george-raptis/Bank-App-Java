package com.menu;

import com.customers.Admin;

import java.util.Scanner;

public class AdminMenu {

    public static void adminMenu() {
        System.out.println("Welcome to the admin menu\n" +
                "1. Register new customer\n" +
                "2. Change type of customer");
        Scanner scan = new Scanner(System.in);
        String choose = scan.next();
        Admin admin = new Admin();

        switch (choose) {
            case "1":
                admin.registerCustomer();
                break;
            case "2":
                break;
        }
    }
}
