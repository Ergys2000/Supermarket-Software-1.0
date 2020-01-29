package models;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Bill implements Serializable {
    private static final long serialVersionUID = 38092000;
    private ArrayList<Product> products;
    public Date date;
    String uniqueID;

    public double total = 0;
    public Bill(){
        products = new ArrayList<Product>();
        date = new Date();
        uniqueID = UUID.randomUUID().toString();
    }
    public Bill(ArrayList<Product> products){
        for(Product i: products){
            append(i);
            total += i.quantity * i.sell_price;
        }
        date = new Date();
    }
    public ArrayList<Product> getProducts(){
        return products;
    }

    public void append(Product product){
        int flag = 0;
        for(Product i: products){
            if(     i.name.equals(product.name)
                    && i.sell_price == product.sell_price
                    && i.purchase_price == product.purchase_price
                    && i.supplier.equals(product.supplier)
                    && i.expiry_date.equals(product.expiry_date))
            {
                i.quantity += product.quantity;
                flag = 1;
            }
        }
        if(flag == 0){
            products.add(product);
        }
        total += product.sell_price * product.quantity;
    }

    public void toFile(String owner, int billCount) {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String filename = "src" + File.separator + "bills" + File.separator + "Bill-" + owner +
                "-" + billCount + ".txt";
        File fi = new File(filename);
        try{
            if(!fi.exists()){
                fi.createNewFile();
            }
            PrintWriter out = new PrintWriter(fi);
            out.println("Date : " + dtf.format(date));
            out.println("*******************************************************************");
            out.printf("%-25s%-20s%-20s", "Name", "Quantity", "Price\n");
            for(Product i: products){
                out.printf("%-25s%-20f%-20f\n", i.name, i.quantity, i.sell_price);
            }
            out.println("\n\nTotal : " + total);
            out.println("********************************************************************");
            out.close();
        }catch(FileNotFoundException e){
           e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
