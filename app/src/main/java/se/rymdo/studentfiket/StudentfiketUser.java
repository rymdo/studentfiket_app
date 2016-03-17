package se.rymdo.studentfiket;


import java.util.ArrayList;

public class StudentfiketUser extends Object {
    private int ID;
    private String name;
    private boolean close;

    public StudentfiketUser() {
        this.ID = 0;
        this.name = "Unknown name";
        this.close = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
