package budget.controllers;

import budget.budgetManager.BudgetManager;

import java.io.*;
import java.util.Scanner;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true;
    private static BudgetManager budgetManager;

    public static void runController() {
        budgetManager = BudgetManager.getBudgetManager();
        while(isRunning) {
            menu();
        }
    }

    private static void menu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
        String input = scanner.nextLine().trim();

        switch (input) {
            case "0":
                System.out.println("\nBye!");
                isRunning = false;
                //BudgetManager.save();
                return;
            case "1":
                addIncome();
                return;
            case "2":
                PurchaseListController.accessMenu(PurchaseActions.ADD);
                return;
            case "3":
                PurchaseListController.accessMenu(PurchaseActions.SHOW);
                return;
            case "4":
                balance();
                return;
            case "5":
                System.out.println(BudgetManager.save() ?
                        "\nPurchases were saved!\n" :
                        "\nPurchases weren't saved!\n");
                return;
            case "6":
                System.out.println(BudgetManager.load() ?
                        "\nPurchases were loaded!\n" :
                        "\nPurchases weren't loaded!\n");
                budgetManager = BudgetManager.getBudgetManager();
                return;
            case "7":
                SortingController.runController();
                return;
            default:
                System.out.println("Wrong input!");
        }
    }

    private static void addIncome() {
        System.out.println("\nEnter income:");
        String income = scanner.nextLine().trim();

        if (!income.matches("[0-9]+\\.?[0-9]*")) {
            System.out.println("Wrong input!");
            return;
        }

        budgetManager.addIncome(Double.parseDouble(income));

        System.out.println("Income was added!\n");
    }

    private static void balance() {
        System.out.printf("\nBalance: $%.2f\n\n", budgetManager.getBalance());
    }
}
