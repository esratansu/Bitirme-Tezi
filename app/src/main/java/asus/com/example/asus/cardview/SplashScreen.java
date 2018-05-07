package asus.com.example.asus.cardview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
        mSplashThread = new Thread(){
            @Override public void run(){
                try {
                    synchronized(this){
                        wait(4000);
                    }
                }catch(InterruptedException ex){
                }
                finally{
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
        mSplashThread.start();// thread objesini calistriyoruz

    }
}