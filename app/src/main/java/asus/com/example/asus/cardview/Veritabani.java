package asus.com.example.asus.cardview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static asus.com.example.asus.cardview.R.id.veriler;

public class Veritabani extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";//database adı

    public static final String TABLE_NAME = "kitap_listesi";
    public static String KITAP_ADI = "kitap_adi";
    public static String KITAP_ID = "id";
    public static String KITAP_TEL = "yazar";

    public Veritabani(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void kitapSil(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KITAP_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KITAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KITAP_ADI + " TEXT, " + KITAP_TEL + " TEXT" + ")";;
        db.execSQL(CREATE_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);  // Veritabanında aynı isimde tablo varsa önceden siler tekrar oluşturur.
        onCreate(db);


    }

    public void VeriEkle(String kitap_adi, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP_ADI, kitap_adi);
        values.put(KITAP_TEL, date);


        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık



    }

    //dinamik oluyor listler.
    public List<String> VeriListele() {


        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sütunlar = {KITAP_ID, KITAP_ADI, KITAP_TEL};


        Cursor cursor = db.query(TABLE_NAME, sütunlar, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            veriler.add(cursor.getInt(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2));   //sütunlar stringinde hangi sırayla tanımlamışsam o sırayla alırım.

        }
        db.close();
        return veriler;


    }



}
