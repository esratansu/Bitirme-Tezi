package asus.com.example.asus.cardview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class Repository extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);


        final ListView listView = (ListView) findViewById(R.id.veriler);
        final MenuItem menuItem = (MenuItem) findViewById(R.id.navigation_item_4);


        Veritabani veritabani = new Veritabani(Repository.this);
        List<String> vVeriler = veritabani.VeriListele();  // Verileri aldık.


        // Şİmdi verilerilerimi aldık geriye listviewe eklemek kaldı.


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Repository.this, android.R.layout.simple_list_item_1, android.R.id.text1, vVeriler);

        listView.setAdapter(adapter);//Veriler listviewe aktarılır.

/*Repository itemlarına tıklama işlemleri

Yerın new yazı eklenin benzerini yap.

//Repositorideki Her bir itema Click eventi verdim. */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Veritabani veritabani = new Veritabani(Repository.this);
                String repo = listView.getItemAtPosition(i).toString();  //Tıklanan itemdaki veriyi aldım.
                Intent a = new Intent(getApplicationContext(), NewYaziEkle.class);
                a.putExtra("send_repo", repo);


                // Bu satırlar başka clasın içerisindeki metoda erişişm sağlar.


                startActivity(a);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                String str = (String) listView.getItemAtPosition(position);
                int itemId = (int) listView.getItemIdAtPosition(position);
                Veritabani veritabani = new Veritabani(Repository.this);// Veritababnını tanımlamm gerkir.
                veritabani.deleteUser(str);

                adapter.remove(str);


                adapter.notifyDataSetChanged();


                return true;
            }
        });

    }


}







