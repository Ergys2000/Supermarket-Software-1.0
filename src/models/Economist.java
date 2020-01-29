package models;


public class Economist extends User{
    public String name, surname;
    public double salary = 50000;

    public Economist(String username, String password, String name, String surname, MyDate bday){
        super(username, password, bday);
        this.name = name;
        this.surname = surname;
    }
    public void setSalary(double d){
        this.salary = d;
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
        return "Economist{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
