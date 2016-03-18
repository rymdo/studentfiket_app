package se.rymdo.studentfiket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StudentfiketWeek extends Object {
    private int weekNumber;
    private List<StudentfiketDay> days;
    private boolean showWeekend;

    public StudentfiketWeek() {
        this.days = new ArrayList();
        this.weekNumber = 0;
        this.showWeekend = false;
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

    public boolean isShowWeekend() {
        return showWeekend;
    }

    public void setShowWeekend(boolean showWeekend) {
        this.showWeekend = showWeekend;
    }

    public String getWeekStartDateString() {
        String startDayString = "Unknown";

        try {
            Date startDayDate = this.getDays().get(0).getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDayString = dateFormat.format(startDayDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDayString;
    }

    public String getWeekEndDateString() {
        String endDayString = "Unknown";

        try {
            Date lastValidDay = null;

            for (Iterator<StudentfiketDay> dates_iter =  this.days.iterator(); dates_iter.hasNext(); ) {
                StudentfiketDay date = dates_iter.next();

                if(lastValidDay == null) {
                    lastValidDay = date.getDate();
                } else {
                    if(lastValidDay != null && date.getDate() != null) {
                        if(date.getDate().after(lastValidDay)) {
                            lastValidDay = date.getDate();
                        }
                    }
                }

            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            endDayString = dateFormat.format(lastValidDay);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return endDayString;
    }
}
