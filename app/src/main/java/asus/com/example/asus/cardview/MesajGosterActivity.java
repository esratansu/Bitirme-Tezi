package asus.com.example.asus.cardview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.List;
public class MesajGosterActivity extends AppCompatActivity {
    CalendarView calendarView;
    private asus.com.example.asus.cardview.AlbumsAdapter adapter;
    private List<Album> albumList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_goster);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                if (month == 4 && dayOfMonth == 2) {
                    Intent intent = new Intent(MesajGosterActivity.this, NewYaziEkle.class);
                    Toast.makeText(MesajGosterActivity.this, "DÜNYA SAMİMİYET GÜNÜMÜZ KUTLU OLSUN", Toast.LENGTH_LONG).show();
                    String date = year + "/" + month + "/" + dayOfMonth;
                    intent.putExtra("send_date", date);
                    startActivity(intent);
                } else if ((month == 2 && dayOfMonth == 17) || (month == 11 && dayOfMonth == 14) || (month == 7 && dayOfMonth == 30)) {
                    Intent intent = new Intent(MesajGosterActivity.this, NewYaziEkle.class);
                    Toast.makeText(MesajGosterActivity.this, "ALTIN KIZLARDAN BİRİNİN DOĞUM GÜNÜ,KUTLU OLSUN!!!", Toast.LENGTH_LONG).show();
                    String date = year + "/" + month + "/" + dayOfMonth;
                    intent.putExtra("send_date", date);
                    startActivity(intent);
                } else if ((month == 2 && dayOfMonth == 16) || (month == 11 && dayOfMonth == 13) || (month == 7 && dayOfMonth == 29) || (month == 4 && dayOfMonth == 1)) {
                    Intent intent = new Intent(MesajGosterActivity.this, NotificationC.class);

                    String date = year + "/" + month + "/" + dayOfMonth;
                    intent.putExtra("send_not", date);
                    startActivity(intent);
                } else {
                    String date = year + "/" + month + "/" + dayOfMonth;
                    Intent intent = new Intent(MesajGosterActivity.this, NewYaziEkle.class);
                    intent.putExtra("send_date", date);
                    startActivity(intent);
                }
            }
        });
    }
    public void tost(String mesaj) {
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
    }
}
