package budget.budgetManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BudgetManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final File file = new File("purchases.txt");
    private static BudgetManager budgetManager = new BudgetManager();

    private double balance;
    private final List<PurchaseTab> categoriesList;

    private BudgetManager() {
        categoriesList = new ArrayList<>();

        balance = 0;

        for (int i = 0; i < 5; i++) {
            categoriesList.add(new PurchaseTab());
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addIncome(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public boolean addPurchase(int categoryID, String name, double price) {

        if ((balance -= price) < 0) {
            balance += price;
            return false;
        }
        categoriesList.get(4).addPurchase(name, price);
        categoriesList.get(categoryID - 1).addPurchase(name, price);
        return true;
    }

    public Map<String, Double> getPurchaseList(int categoryID) {
        return categoriesList.get(categoryID - 1).getPurchaseList();
    }

    public double getFullPriceOfCategory(int categoryID) {
        return categoriesList.get(categoryID - 1).getFullPrice();
    }

    public static BudgetManager getBudgetManager() {
        return budgetManager;
    }

    public static boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.reset();
            oos.writeObject(BudgetManager.budgetManager);

            oos.close();
            bos.close();
            fos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean load() {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);

            BudgetManager.budgetManager = (BudgetManager) ois.readObject();

            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
