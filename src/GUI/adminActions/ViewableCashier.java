package GUI.adminActions;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewableCashier {
        public SimpleStringProperty name;
        public SimpleIntegerProperty billNr;
        public SimpleLongProperty sold;

        public ViewableCashier(String name, int nr,  double sold) {
            this.name = new SimpleStringProperty(name);
            this.sold = new SimpleLongProperty((long)sold);
            this.billNr = new SimpleIntegerProperty(nr);
        }

    public int getBillNr() {
        return billNr.get();
    }

    public SimpleIntegerProperty billNrProperty() {
        return billNr;
    }

    public void setBillNr(int billNr) {
        this.billNr.set(billNr);
    }

    public String getName() {
            return name.get();
        }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
                                   this.name.set(name);
                                                       }

    public double getSold() {
                          return sold.get();
                                            }

    public SimpleLongProperty soldProperty() {
                                           return sold;
                                                       }

    public void setSold(double sold) {
                                   this.sold.set((long)sold);
        }
}
