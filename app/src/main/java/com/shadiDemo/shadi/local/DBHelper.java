package com.shadiDemo.shadi.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.shadiDemo.shadi.model.Dob;
import com.shadiDemo.shadi.model.Location;
import com.shadiDemo.shadi.model.Login;
import com.shadiDemo.shadi.model.Name;
import com.shadiDemo.shadi.model.Picture;
import com.shadiDemo.shadi.model.Result;
import com.shadiDemo.shadi.model.Street;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Matches.db";
    public static final String MATCHES_TABLE_NAME = "table_match";
    public static final String MATCHES_COLUMN_ID = "id";
    public static final String MATCHES_COLUMN_NAME_USERNAME = "username";
    public static final String MATCHES_COLUMN_NAME_TITLE = "title";
    public static final String MATCHES_COLUMN_NAME_FIRST = "first";
    public static final String MATCHES_COLUMN_NAME_last = "last";
    public static final String MATCHES_COLUMN_PIC = "pic";
    public static final String MATCHES_COLUMN_EMAIL = "email";
    public static final String MATCHES_COLUMN_MOBILE = "mobile";
    public static final String MATCHES_COLUMN_GENDER = "gender";
    public static final String MATCHES_COLUMN_AGE = "age";
    public static final String MATCHES_COLUMN_DOB = "dob";
    public static final String MATCHES_COLUMN_STREET = "street";
    public static final String MATCHES_COLUMN_CITY = "city";
    public static final String MATCHES_COLUMN_STATE = "state";
    public static final String MATCHES_COLUMN_COUNTRY = "country";
    public static final String MATCHES_COLUMN_POSTAL = "postal";
    public static final String MATCHES_COLUMN_STATUS = "status";
    private static DBHelper dbHelper;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + MATCHES_TABLE_NAME +
                        "(id integer primary key, username text,title text,first text,last text,pic text,email text,mobile text, gender text,age integer,dob text, street text,city text,state text,country text,postal text,status text, UNIQUE(username))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + MATCHES_TABLE_NAME);
        onCreate(db);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context.getApplicationContext());
        return dbHelper;
    }

    public void insertMatches(ArrayList<Result> matchesList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < matchesList.size(); i++) {
            Result result = matchesList.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MATCHES_COLUMN_NAME_USERNAME, result.getLogin().getUsername());
            contentValues.put(MATCHES_COLUMN_NAME_TITLE, result.getName().getTitle());
            contentValues.put(MATCHES_COLUMN_NAME_FIRST, result.getName().getFirst());
            contentValues.put(MATCHES_COLUMN_NAME_last, result.getName().getLast());
            contentValues.put(MATCHES_COLUMN_NAME_last, result.getName().getLast());
            contentValues.put(MATCHES_COLUMN_PIC, result.getPicture().getLarge());
            contentValues.put(MATCHES_COLUMN_AGE, result.getDob().getAge());
            contentValues.put(MATCHES_COLUMN_DOB, result.getDob().getDate());
            contentValues.put(MATCHES_COLUMN_EMAIL, result.getEmail());
            contentValues.put(MATCHES_COLUMN_MOBILE, result.getCell());
            contentValues.put(MATCHES_COLUMN_GENDER, result.getGender());
            contentValues.put(MATCHES_COLUMN_STREET, result.getLocation().getStreet().getName());
            contentValues.put(MATCHES_COLUMN_CITY, result.getLocation().getCity());
            contentValues.put(MATCHES_COLUMN_STATE, result.getLocation().getState());
            contentValues.put(MATCHES_COLUMN_COUNTRY, result.getLocation().getCountry());
            contentValues.put(MATCHES_COLUMN_POSTAL, result.getLocation().getPostcode());
            contentValues.put(MATCHES_COLUMN_STATUS, "");
            db.insert(MATCHES_TABLE_NAME, null, contentValues);
        }
    }

    public ArrayList<Result> getAllMatches() {
        SQLiteDatabase db;
        Cursor cursor = null;
        ArrayList<Result> matchesList = new ArrayList<>();
        String ls_selectQuery = "SELECT *  FROM " + MATCHES_TABLE_NAME;
        try {
            db = getWritableDatabase();
            cursor = db.rawQuery(ls_selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Result result = new Result();
                    Login login = new Login();
                    login.setUsername(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_NAME_USERNAME)));
                    result.setLogin(login);

                    Name name = new Name();
                    name.setTitle(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_NAME_TITLE)));
                    name.setFirst(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_NAME_FIRST)));
                    name.setLast(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_NAME_last)));
                    result.setName(name);

                    Location location = new Location();
                    location.setCity(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_CITY)));
                    location.setState(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_STATE)));
                    location.setCountry(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_COUNTRY)));
                    location.setPostcode(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_POSTAL)));
                    Street street = new Street();
                    street.setName(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_STREET)));
                    location.setStreet(street);
                    result.setLocation(location);

                    Dob dob = new Dob();
                    dob.setAge(cursor.getInt(cursor.getColumnIndex(MATCHES_COLUMN_AGE)));
                    dob.setDate(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_DOB)));
                    result.setDob(dob);

                    Picture picture = new Picture();
                    picture.setLarge(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_PIC)));
                    result.setPicture(picture);

                    result.setGender(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_GENDER)));
                    result.setCell(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_MOBILE)));
                    result.setEmail(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_EMAIL)));
                    result.setStatus(cursor.getString(cursor.getColumnIndex(MATCHES_COLUMN_STATUS)));

                    matchesList.add(result);
                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return matchesList;
    }


    public boolean updateMatch(String status, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MATCHES_COLUMN_STATUS, status);
        db.update(MATCHES_TABLE_NAME, contentValues, MATCHES_COLUMN_NAME_USERNAME + " = ? ", new String[]{userName});
        return true;
    }


}