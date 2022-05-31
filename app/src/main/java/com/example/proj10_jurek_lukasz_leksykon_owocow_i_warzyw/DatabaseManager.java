package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseManager extends SQLiteOpenHelper {

    private static DatabaseManager databaseManager;
    private static final String db_name = "RoslinyDB.db";
    private static final int db_version = 1;
    private static final String table_name = "Rosliny";

    private static final String counter = "counter";
    private static final String title_field = "title";
    private static final String desc_field = "desc";
    private static final String type_field = "type";



    public DatabaseManager(Context context) {
        super(context,db_name, null, db_version);
    }

    public static DatabaseManager instanceOfDatabase(Context context){
        if(databaseManager==null){
            databaseManager = new DatabaseManager(context);
        }
        return databaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(table_name)
                .append("(")
                .append(counter)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(title_field)
                .append(" TEXT UNIQUE, ")
                .append(desc_field)
                .append(" TEXT, ")
                .append(type_field)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void dodajRosline(Roslina roslina)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues zawartosc = new ContentValues();
        zawartosc.put(title_field, roslina.getNazwa());
        zawartosc.put(desc_field, roslina.getOpis());
        zawartosc.put(type_field, roslina.getTyp());
        db.insert(table_name, null, zawartosc);
    }

    public void populateRoslinyListArray()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor result = db.rawQuery("SELECT * FROM " + table_name, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(0);
                    String title = result.getString(1);
                    String desc = result.getString(2);
                    String type = result.getString(3);
                    Roslina roslina = new Roslina(id,title,desc,type);
                    Roslina.roslinyArrayList.add(roslina);
                }
            }
        }
    }

    public void updateRoslinaInDB(Roslina roslina)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title_field, roslina.getNazwa());
        contentValues.put(desc_field, roslina.getOpis());
        contentValues.put(type_field, roslina.getTyp());
        db.update(table_name, contentValues, counter + " =? ", new String[]{String.valueOf(roslina.getId())});
    }

    public void deleteRoslinaInDB(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {""+id};
        db.delete(table_name,counter + " =? ", args);
    }
}
