package databaseAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import GUI.adminActions.AddUser;
import models.*;

public class RWUsers {
    private ArrayList<User> users;
    private static final String file="users.bin";
    private File fi;
    public RWUsers() {
        users=new ArrayList<>();
        fi=new File(file);
        if(fi.exists()) {
            readUsers();
            setNr();
        } else {
            firstUsage();
        }
    }

    private void setNr() {
        int max=0;
        for(User i:users) if(max<i.getID()) max=i.getID();
        User.setNrUsers(max+1);
    }

    private void firstUsage() {
        AddUser.firstAddUser(this);
    }

    private void writeUsers() {
        try {
            FileOutputStream fos=new FileOutputStream(fi);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("File cannot be written!!!");
        } catch (IOException e) {
            System.err.println("Problem with writing object");
        }
    }

    private void readUsers() {
        try {
            FileInputStream fis=new FileInputStream(fi);
            ObjectInputStream ois=new ObjectInputStream(fis);
            users=(ArrayList<User>)ois.readObject();
            ois.close();fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Class does not match");
        }

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void add(User x){
        users.add(x);
        writeUsers();
    }

    public void delete(User x) {
        users.remove(x);
        writeUsers();
    }

    public User getUserById(int id) {
        for(User i:users)
            if(i.getID()==id) return  i;
        return null;
    }

    public boolean usernameExists(String string){
        for(User i: users){
            if(i.username.equals(string)){
                return true;
            }
        }
        return false;
    }

    public User checkLogin(String user,String pass) {
        for(User i:users)
            if( i.checkUser(user, pass))
                return i;
        return null;
    }

    public double getSalariesTotal(){
        double total = 0;
        for(User u: users){
            if(u instanceof Economist)
                total += ((Economist) u).salary;
            else if(u instanceof Cashier)
                total += ((Cashier) u).salary;
            else
                total += 0;
        }
        return total;
    }

    public void update() {
        writeUsers();
    }

}
