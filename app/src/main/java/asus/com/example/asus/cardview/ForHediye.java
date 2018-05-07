package asus.com.example.asus.cardview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import static asus.com.example.asus.cardview.R.id.siteler;

public class ForHediye extends AppCompatActivity {

    private String[] ulkeler =
            {"https://www.hediyesepeti.com/", "https://www.hediyefabrikasi.com/", "https://www.hediyehanem.com/"};
    private List<Album> siteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_hediye);


        ListView listemiz = (ListView) findViewById(siteler);

        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, ulkeler);

        listemiz.setAdapter(veriAdaptoru);

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Integer s = position;
                Intent i = new Intent(getApplicationContext(), HediyeSecim.class);
                i.putExtra("send_position", (int) s);
                startActivity(i);


            }
        });
    }
}
