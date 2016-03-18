package se.rymdo.studentfiket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    public boolean isDayOpen() {
        if(this.shifts.size() > 0) {
            return true;
        }
        return false;
    }

    public int getNumberOfShifts() {
        return this.shifts.size();
    }

    public Date getStartOfDay() {
        if (this.shifts.size() > 0) {
            return this.shifts.get(0).getStartTime();
        }
        return null;
    }

    public Date getEndOfDay() {
        if (this.shifts.size() > 0) {
            return this.shifts.get(this.shifts.size()-1).getEndTime();
        }
        return null;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StudentfiketShift getFirstShift() {
        if (this.shifts.size() > 0) {
          return this.shifts.get(0);
        }
        return null;
    }

    public StudentfiketShift getLastShift() {
        if (this.shifts.size() > 0) {
            return this.shifts.get(this.shifts.size()-1);
        }
        return null;
    }

    public boolean closedInBetweenFirstAndLastShift() {

        StudentfiketShift last_shift = null;
        boolean isClosedInBetween = false;
        for(Iterator<StudentfiketShift> shifts_iter =  this.shifts.iterator(); shifts_iter.hasNext();) {
            StudentfiketShift shift = shifts_iter.next();

            if(last_shift == null) {
                last_shift = shift;
            } else {
                if(!(last_shift.getEndTime().equals(shift.getStartTime()))) {
                    isClosedInBetween = true;
                }
                last_shift = shift;
            }

        }

        return isClosedInBetween;
    }

    public String getDayOpenString() {

        String str = "Closed";

        if(!this.isDayOpen()) {
            return str;
        }

        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
        String startTime = hourMinuteFormat.format(this.getStartOfDay());
        String endTime = hourMinuteFormat.format(this.getEndOfDay());
        str = startTime + " - " + endTime;

        if(this.closedInBetweenFirstAndLastShift()) {
            str += " **";
        }

        return  str;
    }

}
