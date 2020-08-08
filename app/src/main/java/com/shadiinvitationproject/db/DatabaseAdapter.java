package com.shadiinvitationproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.shadiinvitationproject.network.Retrofit.ResponseModels.Dob;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Location;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Name;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Picture;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Result;
import com.shadiinvitationproject.network.Retrofit.ResponseModels.Street;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    public static final String VERSION = "1.0";
    public static final String DATABASE_NAME = "myDb.db";
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE_MATCHRECORD = "create table IF NOT EXISTS "
            + "MATCHRECORD"
            + "( ID integer PRIMARY KEY AUTOINCREMENT,GENDER text,TITLE text,FNAME text,LNAME text,EMAIL text UNIQUE,DOB text,AGE text,REGISTERDATE text,REGISTERAGE text," +
            "PHONE text,CELL text,PICLARGEURL text,PICMEDIUMURL text,PICTHUMBNAILURL text,STATUS text); ";

    static final String DATABASE_CREATE_LOCATION = "create table IF NOT EXISTS "
            + "LOCATION"
            + "(STNUMBER text,STNAME text,CITY text,STATE text,COUNTRY text,POSTCODE text,LAT text,LON text,TZOFFSET text,TZDESC text,EMAIL text primary key,NATIONALITY text); ";

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (db != null && db.isOpen())
            db.close();

    }

    public void insertRecord(Result result) {

        ContentValues cvMatchrecord = new ContentValues();

        cvMatchrecord.put("GENDER", result.getGender());
        cvMatchrecord.put("TITLE", result.getName().getTitle());
        cvMatchrecord.put("FNAME", result.getName().getFirst());
        cvMatchrecord.put("LNAME", result.getName().getLast());
        cvMatchrecord.put("EMAIL", result.getEmail());
        cvMatchrecord.put("DOB", result.getDob().getDate());
        cvMatchrecord.put("AGE", result.getDob().getAge() + "");
        cvMatchrecord.put("REGISTERDATE", result.getRegistered().getDate());
        cvMatchrecord.put("REGISTERAGE", result.getRegistered().getAge());
        cvMatchrecord.put("PHONE", result.getPhone());
        cvMatchrecord.put("CELL", result.getCell());
        cvMatchrecord.put("PICLARGEURL", result.getPicture().getLarge());
        cvMatchrecord.put("PICMEDIUMURL", result.getPicture().getMedium());
        cvMatchrecord.put("PICTHUMBNAILURL", result.getPicture().getThumbnail());

        ContentValues cvLocation = new ContentValues();

        cvLocation.put("STNUMBER", result.getLocation().getStreet().getNumber());
        cvLocation.put("STNAME", result.getLocation().getStreet().getName());
        cvLocation.put("CITY", result.getLocation().getCity());
        cvLocation.put("STATE", result.getLocation().getState());
        cvLocation.put("COUNTRY", result.getLocation().getCountry());
        cvLocation.put("POSTCODE", result.getLocation().getPostcode());
        cvLocation.put("LAT", result.getLocation().getCoordinates().getLatitude());
        cvLocation.put("LON", result.getLocation().getCoordinates().getLongitude());
        cvLocation.put("TZOFFSET", result.getLocation().getTimezone().getOffset());
        cvLocation.put("TZDESC", result.getLocation().getTimezone().getDescription());
        cvLocation.put("EMAIL", result.getEmail());
        cvLocation.put("NATIONALITY", result.getNat());

        long insertChkMatchrecord = db.insert("MATCHRECORD", null, cvMatchrecord);
        long insertChkLocation = db.insert("LOCATION", null, cvLocation);
        System.out.println("insertCheck MATCHRECORD " + insertChkMatchrecord);
        System.out.println("insertCheck LOCATION " + insertChkLocation);

    }

    public void updateStatus(Result result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS","ACCEPT");
        db.update("MATCHRECORD",contentValues,"EMAIL = '" + result.getEmail() + "'",null);
    }

    public void deleteRecord(Result result) {
        db.delete("MATCHRECORD", "EMAIL = '" + result.getEmail() + "'", null);
        db.delete("LOCATION", "EMAIL = '" + result.getEmail() + "'", null);
    }

    public List<Result> getAllRecords(String position) {
        List<Result> resultList = new ArrayList<>();

        String MY_QUERY = "";
        if (position.equals("0")) {
            MY_QUERY = "SELECT * FROM MATCHRECORD M INNER JOIN LOCATION L ON M.EMAIL = L.EMAIL WHERE M.STATUS IS NULL";
        } else {
            MY_QUERY = "SELECT * FROM MATCHRECORD M INNER JOIN LOCATION L ON M.EMAIL = L.EMAIL WHERE M.STATUS = 'ACCEPT'";
        }

        Cursor cursor = db.rawQuery(MY_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                Name name = new Name();
                Dob dob = new Dob();
                Location location = new Location();
                Street street = new Street();
                Picture picture = new Picture();

                name.setTitle(cursor.getString((cursor.getColumnIndex("TITLE"))));
                name.setFirst(cursor.getString((cursor.getColumnIndex("FNAME"))));
                name.setLast(cursor.getString((cursor.getColumnIndex("LNAME"))));

                dob.setDate(cursor.getString((cursor.getColumnIndex("DOB"))));
                dob.setAge(Long.parseLong(cursor.getString((cursor.getColumnIndex("AGE")))));

                location.setCity(cursor.getString((cursor.getColumnIndex("CITY"))));
                street.setName(cursor.getString((cursor.getColumnIndex("STNAME"))));
                location.setStreet(street);
                location.setState(cursor.getString((cursor.getColumnIndex("STATE"))));
                location.setCountry(cursor.getString((cursor.getColumnIndex("COUNTRY"))));
                picture.setThumbnail(cursor.getString((cursor.getColumnIndex("PICTHUMBNAILURL"))));
                picture.setLarge(cursor.getString((cursor.getColumnIndex("PICLARGEURL"))));
                picture.setMedium(cursor.getString((cursor.getColumnIndex("PICMEDIUMURL"))));

                result.setName(name);
                result.setEmail(cursor.getString((cursor.getColumnIndex("EMAIL"))));
                result.setDob(dob);
                result.setLocation(location);
                result.setPicture(picture);

                resultList.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return resultList;
    }

}