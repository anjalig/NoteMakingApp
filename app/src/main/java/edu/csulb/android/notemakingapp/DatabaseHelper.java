//package applications.editablelistview;
package edu.csulb.android.notemakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "CAPTION";
    public static final String COL3 = "PATH";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT,"+ COL3+ " TEXT)";
        db.execSQL(createTable);
        System.out.println(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }


    public void insertPhoto(String path,String caption){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL3, path);
        values.put(COL2, caption);
// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAll(){
        List<String> labels = new ArrayList<String>();
// Select All Query
        String selectQuery = "SELECT * FROM " +
                TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex(COL2)));
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
// returning labels
        return labels;
    }
    public String getImage(String caption){

        String path="";
        String[] tableColumns = new String[] {
                COL1, COL2, COL3};
        String whereClause = COL2 + " = ?";
        String[] whereArgs = new String[] {
                caption
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, tableColumns, whereClause, whereArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                path=(cursor.getString(cursor.getColumnIndex(COL3)));
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
// returning labels
        return path;
    }
}

