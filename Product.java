public class Product {
    String productId;
    String name;
    String category;
    String price;

    public Product(String productId, String name, String category, String price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + name + ", Category: " + category + ", Price: " + price;
    }
}
