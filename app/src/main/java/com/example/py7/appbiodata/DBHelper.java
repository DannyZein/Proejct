package com.example.py7.appbiodata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_biodata";

    public static final String table_name = "tabel_biodata";
    public static final String  table_login = "tabel_login";

    public static final String row_id = "_id";
    public static final String row_nomor = "Nomor";
    public static final String row_nama = "Nama";
    public static final String row_jk = "JK";
    public static final String row_tempatLahir = "TempatLahir";
    public static final String row_tglLahir = "Tanggal";
    public static final String row_alamat = "Alamat";
    public static final String row_foto = "Foto";

    public static final String row_idUser = "_id";
    public static final String row_username = "Username";
    public static final  String row_password = "Password";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nomor + " TEXT, " + row_nama + " TEXT, " + row_jk + " TEXT, "
                + row_tempatLahir + " TEXT, " + row_tglLahir + " TEXT, "+ row_alamat + "TEXT, " + row_foto + " TEXT)";
        db.execSQL(query);

        String queryLogin = "CREATE TABLE " + table_login + "("+ row_idUser + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_username +"TEXT," + row_password + "TEXT)";
        db.execSQL(queryLogin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_login);
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);
        return cur;
    }

    //Get 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data to Database
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    //SEARCH DATA
    public Cursor searchData(String keyword){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_nama + " like ? " + " ORDER BY " +
                row_id + " DESC ", new String[] { "%" + keyword + "%" }, null);
                return cur;
    }

    //insert data to table
     public void insertLogin(ContentValues values){
        db.insert(table_login, null, values);
     }

     public boolean checkUser (String username, String password){
        String[] columns = {row_idUser};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + "and" + row_password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(table_login, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
     }
}
