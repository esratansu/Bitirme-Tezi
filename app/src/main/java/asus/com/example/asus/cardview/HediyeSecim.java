package asus.com.example.asus.cardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class HediyeSecim extends AppCompatActivity {
    WebView popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hediye_secim);


        Integer value = getIntent().getExtras().getInt("send_position");
        WebView webview = (WebView) findViewById(R.id.popup);
        webview.getSettings().setJavaScriptEnabled(true);


        switch (value) {
            case 0:

                webview.loadUrl("https://www.hediyesepeti.com/");
                break;
            case 1:

                webview.loadUrl("https://www.hediyefabrikasi.com/");
                break;
            case 2:

                webview.loadUrl("https://www.hediyehanem.com/");
                break;

        }

    }
}
