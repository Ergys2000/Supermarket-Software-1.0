package models;

public class ProductExistsException extends Exception{
    public ProductExistsException(String string){
        super(string);
    }
}
