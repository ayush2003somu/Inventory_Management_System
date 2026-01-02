import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
class Product{
    private String id;
    private String name;
    private double price;
    private int quantity;
    public Product(String id,String name,double price,int quantity){
        if (price<0 || quantity<0){
            throw new IllegalArgumentException("price and quantity should always be non-negative");
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    //Getters
    public String getId(){
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        if (price<0)throw new IllegalArgumentException("price should always be non-negative");
        this.price = price;
    }
    public void addQuantity(int amount){
        if (amount<0) throw new IllegalArgumentException(" Please enter any positive value");
        this.quantity+=amount;
    }
    public void decreseQuantity(int amount){
        if (amount<0) throw new IllegalArgumentException(" Please enter any positive value");
        if (amount>this.quantity) throw new IllegalArgumentException("Insufficeint stock");
        this.quantity-=amount;
    }

    // just to format the output of the products;

    @Override
    public String toString(){
        return String.format("| %-8s | %-20s | %-10.2f | %-8d |", id,name,price,quantity);
    }
    }

class InventoryException extends Exception{
    public InventoryException(String message){
        super(message);
    }
}
public class InventoryManagementSystem{
    private Scanner scanner;
    private Map<String,Product> inventory;
    private static final int LOW_STOCK_LIMIT = 5;
    public InventoryManagementSystem(){
        inventory = FileManager.loadInventory();
        scanner = new Scanner(System.in);
    }
    public static void main(String[] args) {
        new InventoryManagementSystem().run();
    }
    public void run(){
        int choice;
        System.out.println("===== Inventory Management System =====");
        do { 
            showMenu();
            choice  = getIntInput("Enter Your Choice:");
            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> updateProduct();
                    case 3 -> deleteProduct();
                    case 4 -> viewInventory();
                    case 5 -> searchProduct();
                    case 6 -> issueStock();
                    case 7 -> restock();
                    case 0 -> {
                        FileManager.saveInventory(inventory);
                        System.out.println("Thank you for using the system!");
                    }
                    default -> System.out.println("Invalid choice!");
                }

            } catch (InventoryException | IllegalArgumentException e ) {
                System.out.println("Error:" + e.getMessage());
            }
            if (choice!=0) pressEnterToContinue();
        } while (choice!=0);

    }
    private void addProduct() throws InventoryException{
        String id = getStringInput("Enter Product Id:").toUpperCase();
        if (inventory.containsKey(id)) {
            throw new InventoryException ("Product already exists!");
        }
        String name = getStringInput("Enter Product Name:");
        double price = getDoubleInput("Enter Product Price:");
        int quantity = getIntInput("Enter Quantityt:");
        inventory.put(id,new Product(id,name,price,quantity));
        System.out.println("✓ Product Added Succesfully");
        
    }
    private void updateProduct() throws InventoryException{
        String id = getStringInput("Enter Product Id:").toUpperCase();
        if (!inventory.containsKey(id)){
            throw new InventoryException("Product Not Exist");
        }
        Product p = inventory.get(id);
        System.out.println("Current:"+p);

        if (getYesNo("Update Name?")){
            p.setName(getStringInput("Enter New Name:"));
            inventory.put(id,p);
        }
        if (getYesNo("Update Price?")){
            p.setPrice(getIntInput("Enter New Price"));
            inventory.put(id,p);
        }
        if (getYesNo("Update Quantity?")){
            int amount = getIntInput("Enter updated Quantity:");
            if (amount<0) throw new InventoryException("Quantity can't be negative.");
            p.decreseQuantity(p.getQuantity());
            p.addQuantity(amount);
            inventory.put(id,p);
        }
    }
    private void deleteProduct() throws InventoryException{
        String id = getStringInput("Enter Product Id:").toUpperCase();
        if (!inventory.containsKey(id)){
            throw new InventoryException("Product Not Exist");
        }
        inventory.remove(id);
        System.out.println("Product removed successfully!");
    }
    private void viewInventory(){
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }
        viewFormat();
        double totalVal=0;
        for (Product p:inventory.values()){
            System.out.print(p);
            if (p.getQuantity()<LOW_STOCK_LIMIT){
                System.out.print("⚠️ Low Stock");
            }
            System.out.println();
            totalVal+=p.getPrice()*p.getQuantity();
        }
        System.out.println("-".repeat(60));
        System.out.println("Total Inventory Value:"+totalVal);
    }
    private void searchProduct() throws InventoryException{
        String id = getStringInput("Enter Product id:").toUpperCase();
        if (!inventory.containsKey(id)){
            throw new InventoryException("Product Not Exist");
        }
        viewFormat();
        System.out.println(inventory.get(id));
    }
    private void issueStock() throws InventoryException{
        String id = getStringInput("Enter Product Id:").toUpperCase();
        if (!inventory.containsKey(id)){
            throw new InventoryException("Product Not Exist");
        }
        System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        int amount = getIntInput("Enter quantity to issue:");
        if (amount<0){
            throw new InventoryException("Input can't be negative!");
        }
        inventory.get(id).decreseQuantity(amount);
        System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        System.out.println("✓ Stock issued");
    }
    private void restock() throws InventoryException{
        String id = getStringInput("Enter Product Id:").toUpperCase();
        if (!inventory.containsKey(id)){
            throw new InventoryException("Product Not Exist");
        }
        System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        int amount = getIntInput("Enter quantity to stock Up!");
        if (amount<0){
            throw new InventoryException("Input can't be negative!");
        }
        inventory.get(id).addQuantity(amount);
        System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        System.out.println("✓ Product restocked");
    }
    private void showMenu(){
        System.out.println("\n" + "=".repeat(50));
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. View Inventory");
        System.out.println("5. Search Product");
        System.out.println("6. Issue Stock");
        System.out.println("7. Restock");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }
    private double getDoubleInput(String s){
        while (true) {
            try {
                System.out.print(s);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number");
            }
        }
    }
    private Boolean getYesNo(String s){
        do { 
            System.out.print(s+"y/n?"+":");
            String str = scanner.nextLine().toUpperCase();
            if (str=="Y" || str=="N"){
                return str=="Y";
            }
            System.out.println("Invalid Input");
            pressEnterToContinue();
        }while (true);
    }
    private String getStringInput(String s){
        System.out.print(s);
        return scanner.nextLine();
    }
    private int getIntInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer");
            }
        }
    }
    private void viewFormat(){
        System.out.println("\n" + "-".repeat(60));
        System.out.printf("| %-8s | %-20s | %-10.2s | %-8s |%n",
                "ID", "NAME", "PRICE", "QTY");
        System.out.println("-".repeat(60));
    }
    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
class FileManager{
    public static Map<String,Product> loadInventory (){
        Map<String,Product> currentInventory = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("inventory.txt"));
            String line;
            while((line=br.readLine())!=null){
                String[] data = line.split(",");
                Product p = new Product(data[0],data[1],Double.parseDouble(data[2]),Integer.parseInt(data[3]));
                currentInventory.put(data[0],p);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentInventory;
    }
    public static void saveInventory(Map<String,Product> map){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.txt"))) {
            for (Product p : map.values()) {
                bw.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}