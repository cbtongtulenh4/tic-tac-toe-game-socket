package javakit.minhphuc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Date currentTime(){
        return new Date();
    }

    public static SimpleDateFormat formatTime(){
        return new SimpleDateFormat("HH:mm:ss");
    }

    public static void main(String[] args) {
        System.out.println(formatTime().format(currentTime()));
    }

    public static String getCurrentTimeFormatted() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String secondsToMinutes(int seconds) {
        return addZero(seconds / 60) + ":" + addZero(seconds % 60);
    }

    public static String addZero(int n) {
        if (n < 10) {
            return "0" + n;
        }
        return "" + n;
    }


}
