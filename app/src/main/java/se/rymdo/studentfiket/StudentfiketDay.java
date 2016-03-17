package se.rymdo.studentfiket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentfiketDay extends Object {
    private Date date;
    private List<StudentfiketShift> shifts;

    public StudentfiketDay() {
        this.shifts = new ArrayList();
        this.date = null;
    }

    public List<StudentfiketShift> getShifts() {
        return shifts;
    }

    public void addShift(StudentfiketShift shift) {
        this.shifts.add(shift);
    }

    public int getNumberOfShifts() {
        return this.shifts.size();
    }

    public Date getStartOfDay() {
        return null;
    }

    public Date getEndOfDay() {
        return null;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
