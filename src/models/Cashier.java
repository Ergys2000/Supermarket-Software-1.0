package models;

import java.util.ArrayList;

public class Cashier extends User{

    public String name;
    public String surname;
    public double salary = 25000;
    private ArrayList<Bill> bills = new ArrayList<>();

    public Cashier(String username, String password, String name, String surname, MyDate bday){
        super(username, password, bday);
        this.name = name;
        this.surname = surname;
    }

    public ArrayList<Bill> getBills(){
        return this.bills;
    }

    public void setSalary(double d){
        this.salary = d;
    }

    public void addBill(Bill bill){
        bills.add(bill);
    }

    public double computeSales(){
        double total = 0;
        for(Bill i: bills){
            total += i.total;
        }
        return total;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
