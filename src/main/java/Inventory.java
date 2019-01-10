import java.util.ArrayList;
import java.util.List;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private List<Product> products = new ArrayList<>();

    private int getProductIndex(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return i;
            }
        }

        return -1;
    }

    double totalInventoryValue(){
        double inventoryValue = 0;
        for (int i = 0; i < products.size(); i++) {
            inventoryValue += (products.get(i).getPrice() * products.get(i).getQuantity());
        }
        return inventoryValue;
    }

    boolean inStock(String productId){
        for (int i = 0; i < products.size(); i++){
            if (products.get(i).getProductId().equals(productId) && products.get(i).getQuantity() > 0){
                return true;
            }
        }
        return false;
    }

    void addProduct(String productId, double price, int quantity) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            Product product = products.get(index);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }

        } else {
            products.add(new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        boolean realProduct = false;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getProductId().equals(productId)) {
                realProduct = true;
                if (product.getQuantity() > quantity) {
                    product.addStock(0 - quantity);
                } else if (product.getQuantity() == quantity) {
                    throw new InsufficientInventory(0, product.getQuantity());
                } else {
                    throw new InsufficientInventory(products.get(i).getQuantity(), product.getQuantity());
                }
            }
        }
        if (!realProduct){
            throw new InsufficientInventory(-1, -1);
        }
    }

    Product getProduct(String productId) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            return products.get(index);
        } else {
            return null;
        }
    }

    String getAllProductNames() {
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }

        return String.join(", ", productIds);
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.getAllProductNames());
    }
}
