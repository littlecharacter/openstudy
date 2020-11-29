package com.lc.javase.base;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeStudyTest {
    @Test
    public void testShowDateTime() throws Exception {
        new DateTimeStudy().showDateTime();
    }

    @Test
    public void testIsUrgency() throws Exception {
        new DateTimeStudy().isUrgency();
    }

    @Test
    public void testGetNonworkDay() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        while (calendar.getTime().before(df.parse("2020-01-01"))) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                System.out.println(df.format(calendar.getTime()));
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void testGetCurrentTime() throws Exception {
        new DateTimeStudy().getCurrentTime();
    }
}