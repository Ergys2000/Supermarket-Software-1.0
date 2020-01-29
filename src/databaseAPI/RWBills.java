package databaseAPI;

import models.Bill;
import models.MyDate;
import models.Product;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RWBills {

    private ArrayList<Bill> bills;
    private static final String file="bills.bin";
    private File fi;

    public RWBills(){
        bills=new ArrayList<>();
        fi=new File(file);
        if(fi.exists()) {
            readBills();
        } else {
            writeBills();
        }
    }

    private void readBills(){
        try {
            FileInputStream fis=new FileInputStream(fi);
            ObjectInputStream ois=new ObjectInputStream(fis);
            bills = (ArrayList<Bill>) ois.readObject();
            ois.close();fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File cannot be written!!!");
        } catch (IOException e) {
            System.err.println("Problem with writing object");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void writeBills() {
        try {
            FileOutputStream fos=new FileOutputStream(fi);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(bills);
            oos.close();fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bill getProductsSold(){
        Bill giantBill = new Bill();
        for (Bill bill : bills){
            for(Product p : bill.getProducts()){
                giantBill.append(p);
            }
        }
        return giantBill;
    }

    public Bill getProductsSold(MyDate sd, MyDate ed){
        Bill giantBill = new Bill();
        for (Bill bill : bills){

            SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
            MyDate date = new MyDate(dtf.format(bill.date));

            if(date.biggerEqualTo(sd) && date.smallerEqualTo(ed)){
                for(Product p : bill.getProducts()){
                    giantBill.append(p);
                }
            }

        }
        return giantBill;
    }

    public void add(Bill bill){
        bills.add(bill);
    }
    public void update(){
        writeBills();
    }

    public double getSalesOfCategory(String category) {
        double sum = 0;
        for (Bill i : bills){
            for (Product p : i.getProducts()) {
                if (p.category.equals(category))
                    sum += p.quantity * p.sell_price;
            }
        }
        return sum;
    }

    public double getSalesOfCategory(String category, MyDate sd, MyDate ed) {
        double sum = 0;
        Bill giantBill = getProductsSold(sd, ed);
        for (Product p : giantBill.getProducts()) {
            if (p.category.equals(category))
                sum += p.quantity * p.sell_price;
        }
        return sum;
    }

    public ArrayList<String> getUniqueCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (Bill b : bills) {
            for (Product i : b.getProducts()) {
                if (!categories.contains(i.category))
                    categories.add(i.category);
            }
        }
        return categories;
    }

    public ArrayList<String> getUniqueCategories(MyDate sd, MyDate ed) {
        ArrayList<String> categories = new ArrayList<>();
        Bill giantBill = getProductsSold(sd, ed);
        for (Product i : giantBill.getProducts()) {
            if (!categories.contains(i.category))
                categories.add(i.category);
        }
        return categories;
    }


    public ArrayList<Product> getProductsByCategory(String category){
        Bill bill = getProductsSold();
        ArrayList<Product> prods = new ArrayList<>();
        for(Product i: bill.getProducts())
            if(i.category.equals(category))
                prods.add(i);

        return prods;
    }

    public ArrayList<Product> getProductsByCategory(String category, MyDate sd, MyDate ed){
        Bill bill = getProductsSold(sd, ed);
        ArrayList<Product> prods = new ArrayList<>();
        for(Product i: bill.getProducts())
            if(i.category.equals(category))
                prods.add(i);

        return prods;
    }
}
