package com.example.ass5a3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class myDatabase extends SQLiteOpenHelper {

    public static final String COMPANY_DATABASE="employee.db";
    public static final String EMPLOYEE_TABLE = "employee";
    public static final String E_COLUMN_1 = "EMP_NO";
    public static final String E_COLUMN_2 = "NAME";
    public static final String E_COLUMN_3 = "ADDRESS";
    public static final String E_COLUMN_4 = "PHONE";
    public static final String E_COLUMN_5 = "SALARY";
    public static final String E_COLUMN_6 = "DEPARTMENT";

    public static final String DEPARTMENT_TABLE = "department";
    public static final String D_COLUMN_1 = "ID";
    public static final String D_COLUMN_2 = "NAME";
    public static final String D_COLUMN_3 = "LOCATION";

    public myDatabase( Context context) {
        super(context,COMPANY_DATABASE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+EMPLOYEE_TABLE+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ADDRESS TEXT,PHONE TEXT,SALARY TEXT,DEPARTMENT INTEGER, FOREIGN KEY(DEPARTMENT) REFERENCES department(ID))");
        db.execSQL("CREATE TABLE "+DEPARTMENT_TABLE+"(ID INTEGER PRIMARY KEY ,NAME TEXT,LOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertDataEmp(String name,String address,String phone,String salary,int dep_id){
        Log.d("myDatabase","insertDataEmp");
        Log.d("myDatabase","name"+name);
        Log.d("myDatabase","address"+address);
        Log.d("myDatabase","phone"+phone);
        Log.d("myDatabase","salary"+salary);
        Log.d("myDatabase","dep_id"+dep_id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(E_COLUMN_1,1);
        cv.put(E_COLUMN_2,name);
        cv.put(E_COLUMN_3,address);
        cv.put(E_COLUMN_4,phone);
        cv.put(E_COLUMN_5,salary);
        cv.put(E_COLUMN_6, dep_id);
        long result = db.insert(EMPLOYEE_TABLE,null,cv);
        return result;
    }

    public void insertDataDep(int dep_id, String dep_name, String dep_loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(D_COLUMN_1,dep_id);
        cv.put(D_COLUMN_2,dep_name);
        cv.put(D_COLUMN_3,dep_loc);
        db.insert(DEPARTMENT_TABLE,null,cv);
    }

    public Cursor showDataEmp(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+EMPLOYEE_TABLE,null);

        return cursor;
    }

    public Cursor showDataDep() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DEPARTMENT_TABLE, null);

        return cursor;
    }

}
