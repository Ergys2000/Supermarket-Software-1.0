package databaseAPI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Product;
import models.ProductExistsException;
import models.Supplier;

import java.io.*;
import java.util.ArrayList;

public class RWProducts {

    private ArrayList<Product> products;
    private static final String file="products.bin";
    private File fi;

    public RWProducts() {
        products=new ArrayList<>();
        fi=new File(file);
        if(fi.exists()) {
            readUsers();
            setNr();
        } else {
            writeUsers();
        }
    }

    private void setNr() {
        int max=0;
        for(Product i:products) if(max<i.ID) max=i.ID;
        Product.setCount(max+1);
    }

    private void readUsers(){
        try {
            FileInputStream fis=new FileInputStream(fi);
            ObjectInputStream ois=new ObjectInputStream(fis);
            products = (ArrayList<Product>) ois.readObject();
            ois.close();fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File cannot be written!!!");
        } catch (IOException e) {
            System.err.println("Problem with writing object");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void writeUsers() {
        try {
            FileOutputStream fos=new FileOutputStream(fi);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.close();fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Supplier> getUniqueSuppliers(){
        ArrayList<String> suppliers = new ArrayList<>();
        ArrayList<Supplier> arr = new ArrayList<>();
        for(Product p : products){
            String sup = p.getSupplier();
            if(!suppliers.contains(sup)){
                suppliers.add(sup);
                arr.add(new Supplier(sup));
            }
        }
        return arr;
    }

    public Product getProductById(int id){
        for(Product i : products){
            if(i.ID == id)
                return i;
        }
        return null;
    }

    public ArrayList<String> getUniqueCategories(){
        ArrayList<String> categories = new ArrayList<>();
        for(Product i: products){
            if(!categories.contains(i.category))
                categories.add(i.category);
        }
        return categories;
    }
    public ArrayList<Product> getProductsByCategory(String category){
        ArrayList<Product> prods = new ArrayList<>();
        for(Product i: products)
            if(i.category.equals(category))
                prods.add(i);

        return prods;
    }
    public double getSalesOfCategory(String category){
        double sum = 0;
        for(Product i: products)
            if(i.category.equals(category))
                sum += (i.bought_quantity - i.quantity) * i.sell_price;
        return sum;
    }

    public Product getProductByName(String name){
        for(Product i: products){
            if(i.name.equals(name))
                return i;
        }
        return null;
    }

    public void append(Product product)throws ProductExistsException {
        int flag = 0;
        for(Product p: products){
            if(p.name.equals(product.name)
                    && p.purchase_price == product.purchase_price
                    && p.sell_price == product.sell_price
                    && p.expiry_date.equals(product.expiry_date)
                    && p.supplier.equals(product.supplier)
                    && p.category.equals(product.category)){

                p.quantity += product.quantity;
                p.bought_quantity += product.bought_quantity;
                flag = 1;
                Product.count--;
            }else if(p.name.equals(product.name)){
                flag = 1;
                throw new ProductExistsException("Product name exists : " + product.name);
            }
        }
        if(flag == 0)
            products.add(product);
    }

    public void checkMissing(){
        for(Product i: products){
            if(i.quantity <= 5){
                Alert al = new Alert(Alert.AlertType.INFORMATION, "We don't have enough " +
                        i.name, ButtonType.OK);
                al.show();
            }
        }
    }

    public boolean check(Product product){
        for(Product i: products){
            if(     i.name.equals(product.name)
                    && i.purchase_price == product.purchase_price
                    && i.supplier.equals(product.supplier)
                    && i.expiry_date.equals(product.expiry_date))
            {
                if(i.quantity >= product.quantity){
                    return true;
                }
            }
        }
        return false;
    }

    public void remove(Product product){
        for(Product i: products){
            if(     i.name.equals(product.name)
                    && i.purchase_price == product.purchase_price
                    && i.supplier.equals(product.supplier)
                    && i.expiry_date.equals(product.expiry_date))
            {
                if(i.quantity == product.quantity){
                    i.quantity = 0;
                } else if(i.quantity > product.quantity){
                    i.quantity -= product.quantity;
                }
            }
        }
    }

    public void update(){ writeUsers(); }
}
