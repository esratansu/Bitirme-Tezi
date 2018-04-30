package asus.com.example.asus.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Repository extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);


        final ListView listView = (ListView) findViewById(R.id.veriler);
        MenuItem menuItem = (MenuItem) findViewById(R.id.navigation_item_4);


        Veritabani veritabani = new Veritabani(Repository.this);
        List<String> vVeriler = veritabani.VeriListele();  // Verileri aldık.


        // Şİmdi verilerilerimi aldık geriye listviewe eklemek kaldı.


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Repository.this, android.R.layout.simple_list_item_1, android.R.id.text1, vVeriler);

        listView.setAdapter(adapter);//Veriler listviewe aktarılır.

    }


}







