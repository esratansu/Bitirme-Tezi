package asus.com.example.asus.cardview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
import static asus.com.example.asus.cardview.R.id.veriler;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Veritabani extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";//database adı
    public static final String TABLE_NAME = "blog_list";
    public static String BLOG_TEXT = "blog_text";
    public static String BLOG_ID = "id";
    public static String BLOG_DATE = "date";
    public Veritabani(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + BLOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BLOG_TEXT + " TEXT, " + BLOG_DATE + " TEXT" + ")";
        ;
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);  // Veritabanında aynı isimde tablo varsa önceden siler tekrar oluşturur.
        onCreate(db);
    }

    public void VeriEkle(String blog_text, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BLOG_TEXT, blog_text);
        values.put(BLOG_DATE, date);
        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık
    }
    //dinamik oluyor listler.
    public List<String> VeriListele() {

        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sütunlar = {BLOG_ID, BLOG_TEXT, BLOG_DATE};
        Cursor cursor = db.query(TABLE_NAME, sütunlar, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            veriler.add(cursor.getInt(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2));   //sütunlar stringinde hangi sırayla tanımlamışsam o sırayla alırım.

        }
        db.close();
        return veriler;
    }

    public void deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("blog_list", "blog_text" + " = ?",
                new String[]{id});
        Toast.makeText(getApplicationContext(), "The registration has been deleted.", Toast.LENGTH_LONG).show();
    }


    public void UpdateData(String string, String string1) {
    }
}
