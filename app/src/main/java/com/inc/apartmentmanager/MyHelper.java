package com.inc.apartmentmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {

    private static final String dbname="mydb";
    private static final String tablename1="EXPENSE";
    private static final String tablename2="INCOME";
 /*   private static final String tablename3="Owner_Detail";*/
    private static final int version=1;

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public MyHelper(Context context){
        super(context,dbname,null,version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase=sqLiteDatabase;
        String sql_create1="CREATE TABLE IF NOT EXISTS "+tablename1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,DAY INTEGER,MONTH INTEGER,YEAR INTEGER,AMOUNT REAL,DESCRIPTION TEXT,PAYEE TEXT )";
        sqLiteDatabase.execSQL(sql_create1);
        String sql_create2="CREATE TABLE IF NOT EXISTS "+tablename2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,DAY INTEGER,MONTH INTEGER,YEAR INTEGER,AMOUNT REAL,DESCRIPTION TEXT,PAYEE TEXT )";
        sqLiteDatabase.execSQL(sql_create2);
       /* String sql_create3="CREATE TABLE IF NOT EXISTS "+tablename3+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,WING TEXT,BLOCK INTEGER,NAME TEXT,MOBILE1 TEXT,MOBILE2 TEXT,EMAIL TEXT,PERSON INTEGER,VEHICLE INTEGER,OCCUPATION TEXT,HOUSENO TEXT )";
        sqLiteDatabase.execSQL(sql_create3);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_delete1=" DROP TABLE IF EXISTS "+tablename1;
        String sql_delete2=" DROP TABLE IF EXISTS "+tablename2;
        sqLiteDatabase.execSQL(sql_delete1);
        sqLiteDatabase.execSQL(sql_delete2);
        onCreate(sqLiteDatabase);
    }

    public void insert_into_table(int day,int month,int year,String amount,String description,String payee,int key){
        SQLiteDatabase db=this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;

        try {
            values=new ContentValues();
            values.put("DAY",day);
            values.put("MONTH",month);
            values.put("YEAR",year);
            values.put("AMOUNT",Double.valueOf(amount));
            values.put("DESCRIPTION",description);
            values.put("PAYEE",payee);

            if(key==1){
                Long i;
                i=db.insert(tablename1,null,values);
                Log.i("Insert in Expense ",i+"");
                db.setTransactionSuccessful();
            }

            if(key==2){
                Long i;
                i=db.insert(tablename2,null,values);
                Log.i("Insert in Income ",i+"");
                db.setTransactionSuccessful();
            }


        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Insert Error in table1");
        }

        finally {
            db.endTransaction();
            db.close();
        }
    }

    public Double TotalExpense_by_Year(int year){
        Double total= Double.valueOf(0);
        SQLiteDatabase db=this.getReadableDatabase();
        String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename1+" WHERE YEAR="+year;
        Cursor cursor = db.rawQuery(sql_sum, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        return total;
    }

    public Double TotalIncome_by_Year(int year){
        Double total= Double.valueOf(0);
        SQLiteDatabase db=this.getReadableDatabase();
        String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename2+" WHERE YEAR="+year;
        Cursor cursor = db.rawQuery(sql_sum, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        return total;
    }

    public Double Deposit_of_block_by_year(String wingblock,int year){
        Double total= Double.valueOf(0);
        SQLiteDatabase db=this.getReadableDatabase();

        String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename2+" WHERE YEAR="+year+" AND PAYEE='"+wingblock+"'";

        try {
            Cursor cursor = db.rawQuery(sql_sum, null);
            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }
            return total;
        }
        catch (Exception ex){
            return Double.valueOf(0);
        }
    }

    public Double expense_paid_by_block_by_year(String wingblock,int year){
        Double total= Double.valueOf(0);
        SQLiteDatabase db=this.getReadableDatabase();
        String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename1+" WHERE YEAR="+year+" AND PAYEE='"+wingblock+"'";

        try {
            Cursor cursor = db.rawQuery(sql_sum, null);
            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }
            return total;
        }
        catch (Exception ex){
            return Double.valueOf(0);
        }

    }

    public ArrayList<ArrayList<String>> get_detail(int year,int key){
        ArrayList<ArrayList<String>> detail=new ArrayList<>();

        ArrayList<String> datelist=new ArrayList<>();
        ArrayList<String> namelist=new ArrayList<>();
        ArrayList<String> amountlist=new ArrayList<>();
        ArrayList<String> descriptionlist=new ArrayList<>();
        ArrayList<String> Categorylist=new ArrayList<>();


        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=null;

        if(key==1) {
            cursor = db.rawQuery("SELECT * FROM " + tablename1 + " WHERE YEAR=" + year +" ORDER BY ID DESC", null);
        }
        if(key==2) {
            cursor = db.rawQuery("SELECT * FROM " + tablename2 + " WHERE YEAR=" + year+" ORDER BY ID DESC" , null);
        }

        if(cursor!=null && cursor.moveToFirst()){
            do{
                String dateinfo=Integer.toString(cursor.getInt(1))+"/"+Integer.toString(cursor.getInt(2))+"/"+Integer.toString(cursor.getInt(3));
                datelist.add(dateinfo);
                amountlist.add(cursor.getString(4));
                descriptionlist.add(cursor.getString(5));
                namelist.add(cursor.getString(6));

            }while (cursor.moveToNext());
        }

        detail.add(datelist);
        detail.add(namelist);
        detail.add(Categorylist);
        detail.add(amountlist);
        detail.add(descriptionlist);

        return detail;
    }

    public ArrayList<ArrayList<String>> get_detail_last(int year,int key,int number){
        ArrayList<ArrayList<String>> detail=new ArrayList<>();

        ArrayList<String> datelist=new ArrayList<>();
        ArrayList<String> namelist=new ArrayList<>();
        ArrayList<String> amountlist=new ArrayList<>();
        ArrayList<String> descriptionlist=new ArrayList<>();
        ArrayList<String> Categorylist=new ArrayList<>();


        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=null;

        if(key==1) {
            cursor = db.rawQuery("SELECT * FROM " + tablename1 + " WHERE YEAR=" + year +" ORDER BY ID DESC", null);
        }
        if(key==2) {
            cursor = db.rawQuery("SELECT * FROM " + tablename2 + " WHERE YEAR=" + year+" ORDER BY ID DESC" , null);
        }

        int i=0;

        if(cursor!=null && cursor.moveToFirst()){
            do{
                String dateinfo=Integer.toString(cursor.getInt(1))+"/"+Integer.toString(cursor.getInt(2))+"/"+Integer.toString(cursor.getInt(3));
                datelist.add(dateinfo);
                amountlist.add(cursor.getString(4));
                descriptionlist.add(cursor.getString(5));
                namelist.add(cursor.getString(6));
                i++;

            }while (cursor.moveToNext() && i<number);
        }

        detail.add(datelist);
        detail.add(namelist);
        detail.add(Categorylist);
        detail.add(amountlist);
        detail.add(descriptionlist);

        return detail;
    }

}