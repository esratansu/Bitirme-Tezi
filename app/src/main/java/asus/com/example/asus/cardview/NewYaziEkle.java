package asus.com.example.asus.cardview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewYaziEkle extends AppCompatActivity {
    EditText blokYazi;
    TextView datepicker;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle nToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_yazi_ekle);
        blokYazi = (EditText) findViewById(R.id.editBlok);
        Button button = (Button) findViewById(R.id.save);
        datepicker = (TextView) findViewById(R.id.datepicker);
        Bundle extras = getIntent().getExtras();
        String datevalue = extras.getString("send_date");
        datepicker.setText(datevalue);
        Bundle extra = getIntent().getExtras();
        String gelenRepo = extra.getString("send_repo");
        if (gelenRepo == " ") {
            blokYazi.setText("");
        } else {
            blokYazi.setText(gelenRepo);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewYaziEkle.this, "The blog was saved.", Toast.LENGTH_SHORT).show();
                Veritabani veritabani = new Veritabani(NewYaziEkle.this);// Veritababnını tanımlamm gerkir.
                veritabani.VeriEkle(blokYazi.getText().toString(), datepicker.getText().toString());

            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        nToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
//TOGGLEDAN ITEM SEÇİLDİĞİNDE YAPILAN İŞLEMLER
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        // close drawer when item is tapped
                        //INTENT YÖNLENDİRME YAPILIYOR....
                        switch (id) {
                            case R.id.navigation_item_1:
                                Intent intent = new Intent(NewYaziEkle.this, ForAlarm.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_item_2:
                                intent = new Intent(NewYaziEkle.this, ForGps.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_item_3:
                                intent = new Intent(NewYaziEkle.this, ForPaylasim.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_item_4:
                                intent = new Intent(NewYaziEkle.this, Repository.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_item_5:
                                intent = new Intent(NewYaziEkle.this, Forsquare.class);

                                startActivity(intent);
                                break;
                            case R.id.navigation_item_6:
                                intent = new Intent(NewYaziEkle.this, ForWeather.class);
                                startActivity(intent);
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected. For example, swap UI fragments here
                        return true;
                    }
                });
    }
    //TOGGLEDAN ITEM SEÇME İŞLEMİNİ YAPMAK İÇİN TOGGLE I AÇMAYA YARAR.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (nToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}