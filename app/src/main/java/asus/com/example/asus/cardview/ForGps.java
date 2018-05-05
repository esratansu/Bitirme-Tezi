package asus.com.example.asus.cardview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import static asus.com.example.asus.cardview.R.id.listView;

public class ForGps extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_ACCESS_FINE_LOCATION = 1;
    private static final int RC_PLACE_PICKER = 2;
    CustomAdapterForGps adapter;

    GoogleApiClient mGoogleApiClient;
    ArrayList<Mekan> placeList = new ArrayList<Mekan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_gps);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        ListView lv = (ListView) findViewById(listView);
        adapter = new CustomAdapterForGps(ForGps.this, placeList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String adres = placeList.get(position).getAdres();

                Intent i = new Intent(ForGps.this, GoogleMapsApi.class);


                i.putExtra("send_loca", adres);

                startActivity(i);


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        CheckBox checkBoxLoc = (CheckBox) findViewById(R.id.checkBoxLocation);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkBoxLoc.setChecked(false);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (checkBoxLoc.isChecked()) {
                checkBoxLoc.setChecked(true);
                checkBoxLoc.setEnabled(false);
            }
        }

    }

    public void locationPermissionClicked(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_ACCESS_FINE_LOCATION);
    }

    public void lokasyonEkleButonuSecildi(View view) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Lokasyona Erişim İznini Vermeniz Gerekiyor", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent placePickerIntent = builder.build(this);
            startActivityForResult(placePickerIntent, RC_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e("MainActivity", String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("MainActivity", String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e("MainActivity", String.format("PlacePicker Exception: %s", e.getMessage()));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PLACE_PICKER && resultCode == RESULT_OK) {
            Place secilenLokasyon = PlacePicker.getPlace(this, data);
            if (secilenLokasyon == null) {
                Toast.makeText(this, "Lokasyon Seçilmedi", Toast.LENGTH_SHORT).show();
                return;
            }

            String secilenLokasyonId = secilenLokasyon.getId();

            veritabanınaEkle(secilenLokasyonId);
            placeIdListele();


        }
    }


    private void veritabanınaEkle(String id) {
        Database db = new Database(this);
        db.lokasyonIdEkle(id);

    }


    public void placeIdListele() {

        placeList.clear();
        Database db = new Database(this);
        Cursor data = db.getPlaceData();
        while (data.moveToNext()) {
            Places.GeoDataApi.getPlaceById(mGoogleApiClient, data.getString(1))
                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(PlaceBuffer places) {
                            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                placeList.add(new Mekan(places.get(0).getName().toString(), places.get(0).getAddress().toString()));
                            } else {
                                Log.e("Main", "Lokasyon bulunamadı");
                            }
                            adapter.notifyDataSetChanged();
                            places.release();

                        }

                    });
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        placeIdListele();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Main", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Main", "onConnectionFailed");

    }


}