package se.rymdo.studentfiket;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat gestureDetector;

    public TextView text_Header;
    public TextView text_week_date;
    public TextView text_day1_status;
    public TextView text_day2_status;
    public TextView text_day3_status;
    public TextView text_day4_status;
    public TextView text_day5_status;


    public String day1_text = "Closed";
    public String day2_text = "Closed";
    public String day3_text = "Closed";
    public String day4_text = "Closed";
    public String day5_text = "Closed";
    public String day6_text = "Closed";
    public String day7_text = "Closed";
    public List<String> all_names_list;

    public StudentfiketWeek thisWeek = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_names_list = new ArrayList();

        text_week_date = (TextView)findViewById(R.id.weekDisplayText);
        text_day1_status = (TextView)findViewById(R.id.day1Status);
        text_day2_status = (TextView)findViewById(R.id.day2Status);
        text_day3_status = (TextView)findViewById(R.id.day3Status);
        text_day4_status = (TextView)findViewById(R.id.day4Status);
        text_day5_status = (TextView)findViewById(R.id.day5Status);

        this.gestureDetector = new GestureDetectorCompat(this, this);

        set_all_loading();
        new Thread (new Runnable(){
            @Override
            public void run(){
                get_new_data_from_site();
            }
        }).start();

    }


    @Override
    public boolean onDown(MotionEvent e) {
        //this.awesomeMessage.setText("onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //this.awesomeMessage.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //this.awesomeMessage.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //this.awesomeMessage.setText("onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        set_all_loading();
        new Thread (new Runnable(){
            @Override
            public void run(){
                get_new_data_from_site();
            }
            }).start();

        //this.awesomeMessage.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //this.awesomeMessage.setText("onFling");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public String get_week_as_serialized_json() {
        String data_buffer = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://www.studentfiket.com/api/getweek.php");

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                data_buffer += current;
                System.out.print(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data_buffer;
    }

    public StudentfiketDay parseJSONShiftsToStudentfiketDay(JSONArray shifts) throws JSONException, ParseException {
        StudentfiketDay newDay = new StudentfiketDay();

        Date newDayDate = null;

        for(int i = 0; i < shifts.length(); i++) {
            JSONObject shift = shifts.getJSONObject(i);

            StudentfiketShift newShift = new StudentfiketShift();

            //ToDo: Add parse users
            //ToDo: Add parse org

            newShift.setsID(shift.getInt("sID"));
            String startTimeStr = shift.getString("startTime");
            String endTimeStr = shift.getString("endTime");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = format.parse(startTimeStr);
            Date endTime = format.parse(endTimeStr);
            newShift.setStartTime(startTime);
            newShift.setEndTime(endTime);
            newShift.setColor(shift.getInt("color"));
            newShift.setClose(shift.getBoolean("close"));

            if(newDayDate == null) {
                newDayDate = startTime;
            }

            newDay.addShift(newShift);
        }

        newDay.setDate(newDayDate);

        return newDay;

    }

    public StudentfiketWeek parse_serialized_data_to_studentfiket_week(String data) {
        StudentfiketWeek new_week = new StudentfiketWeek();
        new_week.setWeekNumber(-1);
        int weekNumber = -1;

        for(int i = 0; i < 7; i++) {
            StudentfiketDay new_day = new StudentfiketDay();
            new_week.addDay(new_day);
        }

        try {
            JSONObject jsonObj = new JSONObject(data);

            for (Iterator<String> dates_iter =  jsonObj.keys(); dates_iter.hasNext(); ) {
                String date = dates_iter.next();

                JSONArray shifts = jsonObj.getJSONArray(date);
                try {
                    StudentfiketDay new_day = parseJSONShiftsToStudentfiketDay(shifts);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsed_date = format.parse(date);
                    new_day.setDate(parsed_date);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(parsed_date);
                    cal.setFirstDayOfWeek(Calendar.MONDAY);

                    int firstDayOfWeek = cal.getFirstDayOfWeek();
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    int position = dayOfWeek-firstDayOfWeek;

                    if(position >= 0) {
                        new_week.getDays().set(position, new_day);
                    }

                    if(weekNumber < 0) {
                        weekNumber = cal.get(Calendar.WEEK_OF_YEAR);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        //ToDo: Fix locale week quick fix!
        if(weekNumber > 0) {
            weekNumber--;
        }

        new_week.setWeekNumber(weekNumber);

        return new_week;
    }

    public void get_new_data_from_site() {

        String serialized_data = get_week_as_serialized_json();

        if(serialized_data.length() < 1) {
            return;
        }

        StudentfiketWeek week = parse_serialized_data_to_studentfiket_week(serialized_data);

        this.thisWeek = week;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String weekDateText = thisWeek.getWeekStartDateString();
                weekDateText += " - ";
                weekDateText += thisWeek.getWeekEndDateString();
                weekDateText += " (Week ";
                weekDateText += Integer.toString(thisWeek.getWeekNumber());
                weekDateText += ")";
                text_week_date.setText(weekDateText);
                text_day1_status.setText(thisWeek.getDays().get(0).getDayOpenString());
                text_day2_status.setText(thisWeek.getDays().get(1).getDayOpenString());
                text_day3_status.setText(thisWeek.getDays().get(2).getDayOpenString());
                text_day4_status.setText(thisWeek.getDays().get(3).getDayOpenString());
                text_day5_status.setText(thisWeek.getDays().get(4).getDayOpenString());
            }
        });
    }

    public void set_all_loading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text_day1_status.setText("Loading...");
                text_day2_status.setText("Loading...");
                text_day3_status.setText("Loading...");
                text_day4_status.setText("Loading...");
                text_day5_status.setText("Loading...");
            }
        });
    }
}
