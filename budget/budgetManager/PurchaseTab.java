package budget.budgetManager;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

class PurchaseTab implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Double> purchaseList = new LinkedHashMap<>();

    private double fullPrice = 0;

    double getFullPrice() {
        return fullPrice;
    }

    void addPurchase(String name, double price) {
        if (purchaseList.containsKey(name)) {
            purchaseList.put(name, price + purchaseList.get(name));
        } else {
            purchaseList.put(name, price);
        }
        fullPrice += price;
    }

    Map<String, Double> getPurchaseList() {
        return purchaseList.isEmpty() ? null : purchaseList;
    }
}
