package models;

import java.io.Serializable;
import java.time.LocalDate;

public class MyDate implements Serializable {
    public int d, m, y;

    public MyDate(int d, int m, int y){
        this.d = d;
        this.m = m;
        this.y = y;
    }
    public MyDate(LocalDate date){
        String datestring = date.toString();
        String[] dt = datestring.split("[/-]");
        this.y = Integer.parseInt(dt[0]);
        this.m = Integer.parseInt(dt[1]);
        this.d = Integer.parseInt(dt[2]);
    }
    public MyDate(String date){
        String[] dt = date.split("[/-]");
        this.d = Integer.parseInt(dt[0]);
        this.m = Integer.parseInt(dt[1]);
        this.y = Integer.parseInt(dt[2]);
    }

    public boolean biggerEqualTo(MyDate date){
        if (this.y > date.y) return true;
        else if(this.y == date.y)
           if(this.m > date.m) return true;
           else if(this.m == date.m)
               if(this.d >= date.d) return true;
        return false;
    }
    public boolean smallerEqualTo(MyDate date){
        if (this.y < date.y) return true;
        else if(this.y == date.y)
            if(this.m < date.m) return true;
            else if(this.m == date.m)
                if(this.d <= date.d) return true;
        return false;
    }

    public boolean equals(MyDate date){
        if(this.d == date.d && this.m == date.m && this.y == date.y)
            return true;
        return false;
    }
    @Override
    public String toString() {
        return "" + d + "/" + m + "/" + y;
    }
}
