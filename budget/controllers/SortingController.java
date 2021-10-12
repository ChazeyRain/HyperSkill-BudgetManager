package budget.controllers;

import budget.budgetManager.BudgetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SortingController {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true;
    private static BudgetManager budgetManager;

    private static final String[] types = new String[]{"All", "Food", "Clothes", "Entertainment", "Other"};

    public static void runController() {
        budgetManager = BudgetManager.getBudgetManager();
        isRunning = true;
        while(isRunning) {
            menu();
        }
    }

    private static void menu() {
        System.out.println("\nHow do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1":
                sortAll();
                return;
            case "2":
                sortByType();
                return;
            case "3":
                sortCertainType();
                return;
            case "4":
                isRunning = false;
                System.out.println();
                return;
            default:
                System.out.println("Wrong input!");
        }
    }

    private static void sortAll() {
        sortID(types[0], 5);
    }

    private static void sortByType() {
        int[] typesID = new int[]{1, 2, 3, 4};

        for (int i = 0; i < typesID.length; i++) {
            for (int j = 0; j < typesID.length - i - 1; j++) {
                if (budgetManager.getFullPriceOfCategory(typesID[j])
                        < budgetManager.getFullPriceOfCategory(typesID[j + 1])) {
                    int temp = typesID[j];
                    typesID[j] = typesID[j + 1];
                    typesID[j + 1] = temp;
                }
            }
        }

        System.out.println();
        for (int type : typesID) {
            System.out.printf("%s - $%.2f\n", types[type], budgetManager.getFullPriceOfCategory(type));
        }
        System.out.println();
    }

    private static void sortCertainType() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int categoryID;
        String input;
        input = scanner.nextLine().trim();
        if (!input.matches("[1-5]")) {
            System.out.println("Wrong input!");
            return;
        }
        categoryID = Integer.parseInt(input);

        sortID(types[categoryID], categoryID);
    }

    private static void sortID(String type, int id) {
        Map<String, Double> goodsMap = budgetManager.getPurchaseList(id);

        if (goodsMap == null) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }

        List<String> goodsList = new ArrayList<>(goodsMap.keySet());

        for (int i = 0; i < goodsList.size(); i++) {
            for (int j = 0; j < goodsList.size() - i - 1; j++) {
                if (goodsMap.get(goodsList.get(j)) < goodsMap.get(goodsList.get(j + 1))) {
                    String temp = goodsList.get(j);
                    goodsList.set(j, goodsList.get(j + 1));
                    goodsList.set(j + 1, temp);
                }
            }
        }
        System.out.printf(!"All".equals(type) ? "\n%s:\n" : "\n", type);
        for (String key : goodsList) {
            System.out.printf("%s $%.2f\n", key, goodsMap.get(key));
        }
        System.out.println();
    }

}
