import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String username = "root";
    private static final String password = "12345";
    private Scanner scanner;
    // private TreeMap<String,Product> inventory;
    private static final int LOW_STOCK_LIMIT=5;                                                  
    public InventoryManagementSystem(){
        scanner = new Scanner(System.in);
    }
    public static void main(String[] args) {
        try{                                                         
            Class.forName("com.mysql.cj.jdbc.Driver");               
        }catch(ClassNotFoundException e){        
            System.out.println("not extablished:");                    
            System.out.println(e.getMessage());                      
        }    
        try (Connection connection = DriverManager.getConnection(url,username,password)){
            new InventoryManagementSystem().run(connection);
        } catch (SQLException e) {
        }                                                        
        
    }
    public void run(Connection connection){
        int choice;
        System.out.println("===== Inventory Management System =====");
        do { 
            showMenu();
            choice  = getIntInput("Enter Your Choice:");
            try {
                switch (choice) {
                    case 1 -> addProduct(connection);
                    case 2 -> updateProduct(connection);
                    case 3 -> deleteProduct(connection);
                    case 4 -> viewInventory(connection);
                    case 5 -> searchProduct(connection);
                    case 6 -> issueStock(connection);
                    case 7 -> restock(connection);
                    case 0 -> {
                        System.out.println("Thank you for using the system!");
                    }
                    default -> System.out.println("Invalid choice!");
                }

            } catch (InventoryException | SQLException | IllegalArgumentException e  ) {
                System.out.println("Error:" + e.getMessage());
            }
            if (choice!=0) pressEnterToContinue();
        } while (choice!=0);

    }
    private void addProduct(Connection connection) throws InventoryException,SQLException{
        String query = "INSERT INTO inventory VALUES(?,?,?,?)";
        String id = getStringInput("Enter Product Id:").toUpperCase();
        // if (inventory.containsKey(id)) {
        //     throw new InventoryException ("Product already exists!");
        // }
        String name = getStringInput("Enter Product Name:");
        double price = getDoubleInput("Enter Product Price:");
        int quantity = getIntInput("Enter Quantity:");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setDouble(3, price);
        preparedStatement.setInt(4, quantity);
        int rowsAffected = preparedStatement.executeUpdate();
        // inventory.put(id,new Product(id,name,price,quantity));
        if (rowsAffected>0)System.out.println("✓ Product Added Succesfully");
        else System.out.println("Product Not Added!");
        preparedStatement.close();
        
    }
    private void updateProduct(Connection connection) throws InventoryException,SQLException{
        String query = "UPDATE inventory set ?=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        String id = getStringInput("Enter Product Id:").toUpperCase();
        // if (!inventory.containsKey(id)){
        //     throw new InventoryException("Product Not Exist");
        // }
        preparedStatement.setString(3,id);
        // Product p = inventory.get(id);
        // System.out.println("Current:"+p);

        if (getYesNo("Update Name?")){
            String str=getStringInput("Enter New Name:");
            // p.setName(str);
            preparedStatement.setString(1,"name");
            preparedStatement.setString(2,str);
            preparedStatement.executeUpdate();
            // inventory.put(id,p);
        }
        if (getYesNo("Update Price?")){
            Double num =getDoubleInput("Enter New Price");
            // p.setPrice(num);
            preparedStatement.setString(1,"price");
            preparedStatement.setDouble(2, num);
            preparedStatement.executeUpdate();
            // inventory.put(id,p);
        }
        preparedStatement.close();
    }
    private void deleteProduct(Connection connection) throws InventoryException,SQLException{
       String query="DELETE from inventory WHERE id=?";
       String id = getStringInput("Enter Product Id:").toUpperCase();
    //    if (!inventory.containsKey(id)){
    //        throw new InventoryException("Product Not Exist");
    //    }
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       preparedStatement.setString(1,id);
       preparedStatement.executeUpdate();
    //    inventory.remove(id);
       System.out.println("Product removed successfully!");
       preparedStatement.close();

    }
    private void viewInventory(Connection connection) throws SQLException{
        String query = "SELECT * from inventory";
        // if (inventory.isEmpty()) {
        //     System.out.println("Inventory is empty");
        //     return;
        // }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
        viewFormat();
        double totalVal=0;
        do{
            Product p = new Product(resultSet.getString("id"), resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("quantity"));
            System.out.print(p);
            if (p.getQuantity()<LOW_STOCK_LIMIT){
                System.out.print("⚠️ Low Stock");
            }
            System.out.println();
            totalVal+=p.getPrice()*p.getQuantity();
        }while (resultSet.next());
        System.out.println("-".repeat(60));
        System.out.println("Total Inventory Value:"+totalVal);
    } else System.out.println("Inventory is empty");
        preparedStatement.close();
    }
    private void searchProduct(Connection connection) throws InventoryException,SQLException{
        String query = "SELECT * from inventory where id=?";
        String id = getStringInput("Enter Product id:").toUpperCase();
        // if (!inventory.containsKey(id)){
        //     throw new InventoryException("Product Not Exist");
        // }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
        Product p = new Product(resultSet.getString("id"), resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("quantity"));
        viewFormat();
        System.out.println(p);
        }else System.out.println("Product Not found!");
        preparedStatement.close();
    }
    private void issueStock(Connection connection) throws InventoryException,SQLException{
        String query1 = "Select quantity from inventory where id=?";
        String query2 = "UPDATE inventory SET quantity=quantity-? where id = ?";
        PreparedStatement viewStatement = connection.prepareStatement(query1);
        PreparedStatement updateStatement = connection.prepareStatement(query2);
        String id = getStringInput("Enter Product Id:").toUpperCase();
        viewStatement.setString(1, id);
        ResultSet resultSet = viewStatement.executeQuery();
        if (resultSet.next()){
        // if (!inventory.containsKey(id)){
        //     throw new InventoryException("Product Not Exist");
        // }
        // System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        int currStock = resultSet.getInt("quantity");
        System.out.println("current Stock:"+currStock);
        int amount = getIntInput("Enter quantity to issue:");
        if (amount<0){
            throw new InventoryException("Input can't be negative!");
        }
        // inventory.get(id).decreseQuantity(amount);     // for map<>;
        if (currStock>amount){
            updateStatement.setInt(1,amount);
            updateStatement.setString(2, id);
            updateStatement.executeUpdate();
            System.out.println("Updated Stock:"+(currStock-amount));
            System.out.println("✓ Stock issued");
        }else System.out.println("Quanity insufficient!");
    }else System.out.println("ID not found!");
    }
    private void restock(Connection connection) throws InventoryException,SQLException{
        String query1 = "Select quantity from inventory where id=?";
        String query2 = "UPDATE inventory SET quantity=quantity+? where id = ?";
        PreparedStatement viewStatement = connection.prepareStatement(query1);
        PreparedStatement updateStatement = connection.prepareStatement(query2);
        String id = getStringInput("Enter Product Id:").toUpperCase();
        viewStatement.setString(1, id);
        ResultSet resultSet = viewStatement.executeQuery();
        // if (!inventory.containsKey(id)){
        //     throw new InventoryException("Product Not Exist");
        // }
        // System.out.println("Current Stock:"+inventory.get(id).getQuantity());
        if (resultSet.next()){
        int currStock = resultSet.getInt("quantity");
        System.out.println("current Stock:"+currStock);
        int amount = getIntInput("Enter quantity to stock Up!");
        if (amount<0){
            throw new InventoryException("Input can't be negative!");
        }
        updateStatement.setInt(1, amount);
        updateStatement.setString(2, id);
        updateStatement.executeUpdate();
        // inventory.get(id).addQuantity(amount);
        System.out.println("Updated Stock:"+(currStock+amount));
        System.out.println("✓ Product restocked");
    }else System.out.println("ID not found!");
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
            if (str.equals("Y") || str.equals("N")){
                return str.equals("Y");
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