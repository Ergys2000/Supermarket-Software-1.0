package models;

import java.util.ArrayList;

public class Supplier {
    public String name;
    public ArrayList<Product> products;

    public Supplier(String name){
        this.name = name;
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public void addProduct(Product product){
            int flag = 0;
            for(Product i: products){
                if(     i.name.equals(product.name)
                        && i.purchase_price == product.purchase_price
                        && i.expiry_date.equals(product.expiry_date))
                {
                    flag = 1;
                }
            }
            if(flag == 0){
                products.add(product);
            }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
