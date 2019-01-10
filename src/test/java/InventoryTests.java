import org.junit.Assert;
import org.junit.Test;

public class InventoryTests {
    @Test
    public void addApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(2, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void addMoreApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(3, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void updatePrice() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .7, 2);

        Assert.assertEquals(.7, inventory.getProduct("apple").getPrice(), .0001);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeApples() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple", 12);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeFakeProduct() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.removeProduct("fakeProduct", 10);
    }

    @Test
    public void inStockApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        Assert.assertEquals(true, inventory.inStock("apple"));
    }

    @Test
    public void notInStockApples() {
        Inventory inventory = new Inventory();
        Assert.assertEquals(false, inventory.inStock("apple"));
    }

    @Test
    public void inStockFakeProduct() {
        Inventory inventory = new Inventory();
        Assert.assertEquals(false, inventory.inStock("fakeProduct"));
    }

    @Test
    public void valueInventoryTest() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.addProduct("potato", .4, 10);
        Assert.assertEquals(10,inventory.totalInventoryValue(),.00001);
    }



}
