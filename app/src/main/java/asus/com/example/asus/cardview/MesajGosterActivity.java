package asus.com.example.asus.cardview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

import static asus.com.example.asus.cardview.R.id.btn1;
import static asus.com.example.asus.cardview.R.id.btn2;
import static asus.com.example.asus.cardview.R.id.btn3;
import static asus.com.example.asus.cardview.R.id.btn4;


public class MesajGosterActivity extends AppCompatActivity {
    // private asus.com.example.asus.cardview.AlbumsAdapter adapter;
    //  private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_goster);


        Intent intent = getIntent();
       /*

        Button btn1=(Button)findViewById(R.id.btn1);
        Button btn2=(Button)findViewById(R.id.btn2);
        Button btn3=(Button)findViewById(R.id.btn3);
        Button btn4=(Button)findViewById(R.id.btn4);

        if((AlbumsAdapter.class.getName().equals("JANUARY"))|| (AlbumsAdapter.class.getName().equals("MARCH"))||(AlbumsAdapter.class.getName().equals("MAY"))|| (AlbumsAdapter.class.getName().equals("JULY"))||(AlbumsAdapter.class.getName().equals("AUGUST"))|| (AlbumsAdapter.class.getName().equals("OCTOBER")) || (AlbumsAdapter.class.getName().equals("DECEMBER"))){

            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);

        }
        else if(AlbumsAdapter.class.getName().equals("FEBRUARY"))
        {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);

        }

        else {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.GONE);

        }


*/




    }

    public void BlokOpen(View view) {
        // düğmeye yanıt verecek bir şeyler


        Intent intent = new Intent(MesajGosterActivity.this, NewYaziEkle.class);
        intent.putExtra("veri", "id");
        startActivity(intent);
    }

}
