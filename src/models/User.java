package models;

import java.io.IOException;
import java.io.Serializable;

public abstract class User implements  Checker, Serializable {
    public String username;
    private String password;
    public MyDate birthday;
    public int ID;
    private static final long serialVersionUID = 25092000;
    public static int nrUsers=0;
    protected User(String username, String password, MyDate bday){
       this.username = username;
       this.password = password;
       this.birthday = bday;
       this.ID = nrUsers++;
    }

    @Override
    public boolean checkUser(String user, String pass) {
        if(this.username.equals(user) && this.password.equals(pass)){
            return true;
        }
        return false;
    }

    public static void setNrUsers(int n){
        nrUsers = n;
    }

    public User getUser(){
        return this;
    }
    public int getID() {
        return ID;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
