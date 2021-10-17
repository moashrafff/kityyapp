package com.ashraff.cats_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dataBaseAccess {

    private SQLiteOpenHelper sqLiteOpenHelper ;
    private SQLiteDatabase sqLiteDatabase ;
    private static dataBaseAccess instance ;


    private  dataBaseAccess  (Context context) {
        this.sqLiteOpenHelper = new myDataBase(context) ;
    }

    public static dataBaseAccess getInstance (Context context) {

        if (instance == null){
            instance = new dataBaseAccess(context);
        }
        return instance;

    }

    public void open(){

        this.sqLiteDatabase = this.sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){

        if (this.sqLiteDatabase != null)
            this.sqLiteDatabase.close();
    }

    public boolean insertCat (cat cat) {

        ContentValues contentValues = new ContentValues() ;
        contentValues.put(myDataBase.DB_cln_name, cat.getName());
        contentValues.put(myDataBase.DB_cln_color, cat.getColor());
        contentValues.put(myDataBase.DB_cln_weight, cat.getWeight());
        contentValues.put(myDataBase.DB_cln_eyeColor, cat.getEyeColor());
        contentValues.put(myDataBase.DB_cln_image, cat.getImage());

        long result = sqLiteDatabase.insert(myDataBase.DB_Tbl_Name,null,contentValues) ;

        return result != -1 ;
    }

    public boolean updateCat (cat cat) {

        ContentValues contentValues = new ContentValues() ;
        contentValues.put(myDataBase.DB_cln_name, cat.getName());
        contentValues.put(myDataBase.DB_cln_color, cat.getColor());
        contentValues.put(myDataBase.DB_cln_weight, cat.getWeight());
        contentValues.put(myDataBase.DB_cln_eyeColor, cat.getEyeColor());
        contentValues.put(myDataBase.DB_cln_image, cat.getImage());

        String [] args = {cat.getId()+""} ;
        long result = sqLiteDatabase.update(myDataBase.DB_Tbl_Name,contentValues,"id=?", args );

        return result > 0 ;
    }

    public boolean deleteCat (cat cat) {
        String [] args = {cat.getId()+""} ;
        long result = sqLiteDatabase.delete(myDataBase.DB_Tbl_Name," id=? ", args );

        return result > 0 ;
    }

    public long getCatsCount () {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase,myDataBase.DB_Tbl_Name);
    }

    public ArrayList<cat> getAllCats () {

        ArrayList<cat> cats = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + myDataBase.DB_Tbl_Name, null);

        if (cursor != null && cursor.moveToNext()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(myDataBase.DB_cln_id));
                String name = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_name));
                String color = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_color));
                double weight = cursor.getDouble(cursor.getColumnIndex(myDataBase.DB_cln_weight));
                String eyeColor = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_eyeColor));
                String image = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_image));

                cat c = new cat(id,name,color,weight,eyeColor,image);
                cats.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cats;
    }

    public ArrayList<cat> getCatWithArgs (String modelSearch) {

        ArrayList<cat> cats = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + myDataBase.DB_Tbl_Name+ " Where " + myDataBase.DB_cln_name+ " like ?", new String[]{modelSearch + "%"});

        if (cursor != null && cursor.moveToNext()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(myDataBase.DB_cln_id));
                String name = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_name));
                String color = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_color));
                double weight = cursor.getDouble(cursor.getColumnIndex(myDataBase.DB_cln_weight));
                String eyeColor = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_eyeColor));
                String image = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_image));

                cats.add(new cat(id,name,color,weight,eyeColor,image));

            }while (cursor.moveToNext());
            cursor.close();
        }
        return cats;
    }

    public cat getCat (int carId) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + myDataBase.DB_Tbl_Name+ " Where " + myDataBase.DB_cln_id+ "=?", new String[]{carId + ""});

        if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(myDataBase.DB_cln_id));
                String name = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_name));
                String color = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_color));
                double weight = cursor.getDouble(cursor.getColumnIndex(myDataBase.DB_cln_weight));
                String eyeColor = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_eyeColor));
                String image = cursor.getString(cursor.getColumnIndex(myDataBase.DB_cln_image));
                cat c = new cat(id,name,color,weight,eyeColor,image);
            cursor.close();
            return c;
        }
        return null;
    }





}
