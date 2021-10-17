package com.ashraff.cats_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class myDataBase extends SQLiteAssetHelper {


    public static final String DB_Name = "cats.db";
    public static final String DB_Tbl_Name = "cats";
    public static final int DB_VERSION = 1 ;
    public static final String DB_cln_id = "id";
    public static final String DB_cln_name = "name";
    public static final String DB_cln_color = "color";
    public static final String DB_cln_weight = "weight";
    public static final String DB_cln_eyeColor = "eye_color";
    public static final String DB_cln_image = "image";



    public myDataBase(Context context) {
        super(context, DB_Name,null, DB_VERSION);

    }



}



