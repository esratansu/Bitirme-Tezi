package asus.com.example.asus.cardview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;




public class MesajGosterActivity extends AppCompatActivity {
    // private asus.com.example.asus.cardview.AlbumsAdapter adapter;
    //  private List<Album> albumList;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_goster);

        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (year + 1) + "/" + month + "/" + dayOfMonth;
                Intent intent = new Intent(MesajGosterActivity.this, NewYaziEkle.class);
                intent.putExtra("send_date", date);
                startActivity(intent);

            }
        });

    }



}
