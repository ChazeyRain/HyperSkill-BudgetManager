package budget.controllers;

import budget.budgetManager.BudgetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PurchaseListController {
    private static final BudgetManager budgetManager = BudgetManager.getBudgetManager();
    private static boolean isRunning = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String menu = "\n1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other\n";

    public static void accessMenu(PurchaseActions action) {
        isRunning = true;
        switch (action) {
            case SHOW:
                while (isRunning) {
                    showListOfPurchases();
                }
            case ADD:
                while (isRunning) {
                    addPurchase();
                }
        }
    }


    private static void showListOfPurchases() {
        int categoryID;
        String input;
        System.out.println(menu +
                "5) All\n" +
                "6) Back\n");
        input = scanner.nextLine().trim();

        if (!input.matches("[1-6]")) {
            return;
        }

        categoryID = Integer.parseInt(input);

        if (categoryID == 6) {
            isRunning = false;
            return;
        }

        Map<String, Double> purchaseList;
        if ((purchaseList = budgetManager.getPurchaseList(categoryID)) != null) {
            List<Map.Entry<String, Double>> entries = new ArrayList<>(purchaseList.entrySet());

            for (Map.Entry<String, Double> entry : entries) {
                System.out.printf("%s $%.2f\n", entry.getKey(), entry.getValue());
            }

            System.out.printf("Total sum: $%.2f\n\n", budgetManager.getFullPriceOfCategory(categoryID));
        } else {
            System.out.println("The purchase list is empty\n");
        }
    }

    private static void addPurchase() {
        int categoryID;
        String input;
        System.out.println(menu +
                "5) Back\n");
        input = scanner.nextLine().trim();

        if (!input.matches("[1-5]")) {
            System.out.println("Wrong input!");
            return;
        }

        categoryID = Integer.parseInt(input);

        if (categoryID == 5) {
            isRunning = false;
            return;
        }

        System.out.println("\nEnter purchase name:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter its price:");
        String price = scanner.nextLine().trim();

        if (!price.matches("[0-9]+\\.?[0-9]*")) {
            System.out.println("Wrong input!");
            return;
        }

        if(!budgetManager.addPurchase(categoryID, name, Double.parseDouble(price))) {
            System.out.println("You can not afford this purchase!\n");
        } else {
            System.out.println("Purchase was added!\n");
        }
    }
}
