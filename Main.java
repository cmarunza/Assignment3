import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        // Specify the actual path to your CSV file here
        String filePath = "src/amazon-product-data.csv";

        // Load products from CSV and insert them into the Red-Black Tree
        long startTime = System.nanoTime();
        loadProductsFromCSV(rbt, filePath);
        long endTime = System.nanoTime();
        System.out.println("Insertions completed in total of " + (endTime - startTime)/1000000.0 + " ms");

        // Sample search queries
        startTime = System.nanoTime();
        Product result1 = rbt.search("4c69b61db1fc16e7013b43fc926e502d");
        endTime = System.nanoTime();
        System.out.println("Search completed in " + (endTime - startTime)/1000000.0 + " ms");
        System.out.println(result1 != null ? result1 : "Product not found.");

        startTime = System.nanoTime();
        Product result2 = rbt.search("66d49bbed043f5be260fa9f7fbff5957");
        endTime = System.nanoTime();
        System.out.println("Search completed in " + (endTime - startTime)/1000000.0 + " ms");
        System.out.println(result2 != null ? result2 : "Product not found.");

        startTime = System.nanoTime();
        Product result3 = rbt.search("2c55cae269aebf53838484b0d7dd931a");
        endTime = System.nanoTime();
        System.out.println("Search completed in " + (endTime - startTime)/1000000.0 + " ms");
        System.out.println(result3 != null ? result3 : "Product not found.");

        // Additional insertion to demonstrate duplicate handling
        Product newProduct = new Product("66d49bbed043f5be260fa9f7fbff5957", "Duplicate Product", "Test Category", "123.45");
        startTime = System.nanoTime();
        rbt.insert(newProduct); // This should trigger the duplicate error message
        endTime = System.nanoTime();
        System.out.println("Failed insertion completed in " + (endTime - startTime)/1000000.0 + " ms");
    }

    // Method to load products from CSV file and insert them into the Red-Black Tree
    public static void loadProductsFromCSV(RedBlackTree rbt, String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Adjust split if CSV uses a different delimiter
                if (values.length < 4 || line.endsWith(",")) continue; // Skip lines that don't have enough data
                int i = 0;

                String productId = values[i++].trim();
                while(productId.startsWith("\"") && !productId.endsWith("\"")){
                    productId += "," + values[i++].trim();
                }
                String name = values[i++].trim();
                while(name.startsWith("\"") && !name.endsWith("\"")){
                    name += "," + values[i++].trim();
                }
                String category = values[i++].trim();
                while(category.startsWith("\"") && !category.endsWith("\"")){
                    category += "," + values[i++].trim();
                }
                String price = values[i++];
                while(price.startsWith("\"") && !price.endsWith("\"")){
                    price += "," + values[i++].trim();
                }

                Product product = new Product(productId, name, category, price);
                rbt.insert(product);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price: " + e.getMessage());
        }
    }
}
