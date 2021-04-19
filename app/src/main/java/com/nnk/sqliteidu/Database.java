package com.nnk.sqliteidu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final String DBNAME ="Details";
    public static final int VERSION = 1;
    public static final String TBNAME = "Student";

    public static final String COL1="ID";
    public static final String COL2="Name";
    public static final String COL3 = "Contact";
    public static final String COL4="Email";
    public static final String COL5="Password";
    public Database(Context context) {
        super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String qry = "Create Table "+TBNAME+"('"+COL1+"' text,'"+COL2+"' text,'"+COL3+"' text,'"+COL4+"' text,'"+COL5+"' text)";
        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insert(String id,String name,String contact,String email,String pass){
        ContentValues cv = new ContentValues();
        cv.put(COL1,id);
        cv.put(COL2,name);
        cv.put(COL3,contact);
        cv.put(COL4,email);
        cv.put(COL5,pass);

        SQLiteDatabase sql = getWritableDatabase();
        sql.insert(TBNAME,null,cv);
        return true;
    }
    public boolean logincheck(String email,String pass) {
        String col[] = {COL5};
        String sel = COL4 + " = ?";
        String sel_args[] = {email};

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TBNAME, col, sel, sel_args, null, null, null);
        boolean res = c.moveToFirst();
        if (res) {
            String npass = c.getString(0);
            if (npass.equals(pass)) {
                return true;
            } else {
                //Toast.makeText(context,"No Empolyee", Toast.LENGTH_SHORT).show();
                return false;

            }
        } else {
            return false;
        }

    }

    public boolean update(String id,String name,String contact,String email,String pass){
        SQLiteDatabase sql =  getWritableDatabase();
        String where = COL1 + "=" +id;
        ContentValues cv =  new ContentValues();
        cv.put(COL1,id);
        cv.put(COL2,name);
        cv.put(COL3,contact);
        cv.put(COL4,email);
        cv.put(COL5,pass);
        sql.update(TBNAME,cv,where,null);
        return true;
    }

}
