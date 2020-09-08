
package com.shadiDemo.shadi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dob {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("age")
    @Expose
    private Integer age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = convertDateFormat(date);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static String convertDateFormat(String inputTime) {
        String result = inputTime;
        try {
            DateFormat f1 = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);; //HH for hour of the day (0 - 2, Locale.ENGLISH3)
            Date d = f1.parse(inputTime);
            DateFormat f2 = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);
            result = f2.format(d).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}