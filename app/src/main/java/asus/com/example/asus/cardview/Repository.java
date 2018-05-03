package asus.com.example.asus.cardview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static asus.com.example.asus.cardview.R.id.datepicker;


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


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Repository.this, android.R.layout.simple_list_item_1, android.R.id.text1, vVeriler);

        listView.setAdapter(adapter);//Veriler listviewe aktarılır.

/*Repository itemlarına tıklama işlemleri

Yerın new yazı eklenin benzerini yap.

//Repositorideki Her bir itema Click eventi verdim.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String repo = listView.getItemAtPosition(i).toString();  //Tıklanan itemdaki veriyi aldım.
               Intent a = new Intent(getApplicationContext(), NewYaziEkle.class);
               a.putExtra("send_repo",repo);


               Bu satırlar başka clasın içerisindeki metoda erişişm sağlar.

            NewYaziEkle test= new NewYaziEkle();
                //Gradebook taki mesajımızı çağırıyoruz
                test.yeniRepo(repo);


                startActivity(a);

            }
        });
        */

    }


}







