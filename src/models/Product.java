package models;

import java.io.Serializable;

public class Product implements Serializable {
    public static int count=0;
    public int ID;
    public String name, category, supplier;
    public double quantity;
    public double bought_quantity;
    public double purchase_price;
    public double sell_price;
    public MyDate purchase_date;
    public MyDate expiry_date;
    private static final long serialVersionUID = 25092001;


    public Product(String name, String category, String supplier, double quantity, double purchase_price,
                   double sell_price, MyDate purchase_date, MyDate expiry_date) {
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.quantity = quantity;
        this.bought_quantity = quantity;
        this.purchase_price = purchase_price;
        this.sell_price = sell_price;
        this.purchase_date = purchase_date;
        this.expiry_date = expiry_date;
        this.ID = count++;
    }
    public static void setCount(int n){
        count = n;
    }

    @Override
    public String toString() {
        return "\nProduct:" +
                "\n\tID = " + ID +
                "\n\tname = " + name + '\n' +
                "\tcategory = " + category + '\n' +
                "\tsupplier = " + supplier + '\n' +
                "\tquantity = " + quantity + '\n'  +
                "\tpurchase_price = " + purchase_price + '\n' +
                "\tsell_price = " + sell_price + '\n' +
                "\tpurchase_date = " + purchase_date + '\n' +
                "\texpiry_date = " + expiry_date + '\n';
    }

    public double getBought_quantity(){
        return bought_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public MyDate getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(MyDate purchase_date) {
        this.purchase_date = purchase_date;
    }

    public MyDate getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(MyDate expiry_date) {
        this.expiry_date = expiry_date;
    }
}
