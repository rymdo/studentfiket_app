package se.rymdo.studentfiket;

import java.util.ArrayList;
import java.util.List;

public class StudentfiketWeek extends Object {
    private int weekNumber;
    private List<StudentfiketDay> days;

    public StudentfiketWeek() {
        this.days = new ArrayList();
        this.weekNumber = 0;
    }

    public List<StudentfiketDay> getDays() {
        return days;
    }

    public void addDay(StudentfiketDay day) {
        this.days.add(day);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getNumberOfDays() {
        return days.size();
    }
}
