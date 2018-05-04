package asus.com.example.asus.cardview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.Arrays;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static asus.com.example.asus.cardview.R.id.edit_share;
import static asus.com.example.asus.cardview.R.id.txt_user_name;



public class ForPaylasim extends AppCompatActivity {


    static final String PREF_KEY_OAUTH_TOKEN = "2381876837-IDIOT0Zkjfckpe93za0HWzGu1TM8O2TM61vCPHZ";
    static final String PREF_KEY_OAUTH_SECRET = "qia3xVlFHpU6lJEuJKidaMvK1y0XaUHJbHVx1m9B1rwvy";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    /**
     * Android uygulamasına , Twitter uygulamasını entegre etmek için, daha önce oluşturdugumuz
     * twitter uygulamasının bilgilerini değişkenlere atadık..
     */
    static String TWITTER_CONSUMER_KEY = "I2hwwguaN1HBMKlWFZK1UAj1R";
    static String TWITTER_CONSUMER_SECRET = "L4qDnjEwWTv6u6A3s623zklpgTMUAMjXZX8EhO7mclYkJeyDxC";
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
    // Login button
    Button btnLoginTwitter;
    // Update status button
    Button btnUpdateStatus;
    // Get Tweets button
    Button btnGetTweets;
    // Logout button
    Button btnLogoutTwitter;
    //Durum yazısı icin EditText
    EditText txtUpdate;
    // lbl update
    TextView lblUpdate;
    TextView lblUserName;
    // Progress dialog
    ProgressDialog pDialog;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    // Internet Connection detector
    private ConnectionDetector cd;
    //Facebook için
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView user_name;
    private Button share_button;
    private ShareDialog shareDialog;
    private EditText share_txt;

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //facebook için
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        shareDialog = new ShareDialog(ForPaylasim.this);

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_for_paylasim);


        user_name = (TextView) findViewById(txt_user_name);


        loginButton = (LoginButton) findViewById(R.id.login_button);

        share_txt = (EditText) findViewById(edit_share);

        share_button = (Button) findViewById(R.id.share_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile ", "user_friends", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject user,
                                    GraphResponse response) {


                                // user_name.setText(response.toString());

                                try {

                                    String id = user.getString("id");
                                    String name = user.getString("name");
                                    user_name.setText("Hoşgeldin " + " " + name);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                request.executeAsync();

            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException e) {

            }
        });


        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                shareToWall();

            }
        });


        //Buraya kadar olan kısım hep facebooktur.


        //ŞİMDİ TWİTTER
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();


      /*  if (!cd.isConnectingToInternet()) {

            alert.showAlertDialog(ForPaylasim.this, "Internet bağlanma hatası",
                    "Lütfen internet bağlantınızı kontrol edin", false);
            return;
        }*/

        // TWITTER_CONSUMER_KEY ve TWITTER_CONSUMER_SECRET değerlerini değişkene atanıp,atanmadığı kontrolu yaptım
        if (TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0) {

            alert.showAlertDialog(ForPaylasim.this, "Twitter oAuth tokens", "İlk önce  twitter oauth tokens değerlerini değişkene atayın!", false);
            return;
        }


        //Arayuz elemanlarını tanımladım..
        btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        btnGetTweets = (Button) findViewById(R.id.btnGetTweets);
        btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        lblUpdate = (TextView) findViewById(R.id.lblUpdate);
        lblUserName = (TextView) findViewById(R.id.lblUserName);


        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);

        //Kullanıcının Twitter hesabını giriş yapıp yapmama durumuna göre , arayuz elemanlarını görünür ve görünmez yaptım
        if (!isTwitterLoggedInAlready()) {
            //Hesabına giriş yapmamış durumunda Login butonunu görünür yaptım
            btnLoginTwitter.setVisibility(View.VISIBLE);
        } else {
            //Logout durumu
            // login button gizli
            btnLoginTwitter.setVisibility(View.GONE);

            //Update Twitter ve Get Tweets ile ilgili arayüz elemanlarını görünür yaptım
            lblUpdate.setVisibility(View.VISIBLE);
            txtUpdate.setVisibility(View.VISIBLE);
            btnUpdateStatus.setVisibility(View.VISIBLE);
            btnGetTweets.setVisibility(View.VISIBLE);
            btnLogoutTwitter.setVisibility(View.VISIBLE);
        }

        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);

        //Kullanıcının Twitter hesabını giriş yapıp yapmama durumuna göre , arayuz elemanlarını görünür ve görünmez yaptım
        if (!isTwitterLoggedInAlready()) {
            //Hesabına giriş yapmamış durumunda Login butonunu görünür yaptım
            btnLoginTwitter.setVisibility(View.VISIBLE);
        } else {
            //Logout durumu
            // login button gizli
            btnLoginTwitter.setVisibility(View.GONE);

            //Update Twitter ve Get Tweets ile ilgili arayüz elemanlarını görünür yaptım
            lblUpdate.setVisibility(View.VISIBLE);
            txtUpdate.setVisibility(View.VISIBLE);
            btnUpdateStatus.setVisibility(View.VISIBLE);
            btnGetTweets.setVisibility(View.VISIBLE);
            btnLogoutTwitter.setVisibility(View.VISIBLE);
        }

        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loginToTwitter();
            }
        });

        /**
         * Update Status butonuna tıklandığında çalısacak kod
         * */
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Kullanıcının Twitter 'a  göndermek istedigi yazıyı  değişkene atadım
                String status = txtUpdate.getText().toString();

                //Edittext den gelen değeri boşmu kontrolu yapıldı ve değer updateTwitterStatus metoduna gonderildi
                if (status.trim().length() > 0) {
                    new updateTwitterStatus().execute(status);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Durum mesajınızı yazınız", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        btnGetTweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTweets();
            }
        });

        btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                logoutFromTwitter();
            }
        });

        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

                try {
                    //Access token alınıyor
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);

                    SharedPreferences.Editor e = mSharedPreferences.edit();

                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    //login durum - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // değişikler kayıt edildi


                    //login button gizledik
                    btnLoginTwitter.setVisibility(View.GONE);

                    //Update Twitter ve Get Tweets ile ilgili arayüz elemanlarını görünür yaptım
                    lblUpdate.setVisibility(View.VISIBLE);
                    txtUpdate.setVisibility(View.VISIBLE);
                    btnUpdateStatus.setVisibility(View.VISIBLE);
                    btnGetTweets.setVisibility(View.VISIBLE);
                    btnLogoutTwitter.setVisibility(View.VISIBLE);

                    // Twitter dan kullanıcı bilgilerini aldık
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();

                    //xml arayüzünde kullanıcı adını gösterdik
                    lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
                } catch (Exception e) {

                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }


    }


    //KULLANILAN METOTLAR

    //SADECE BU FACE İÇİN
    private void shareToWall() {
        String send = share_txt.getText().toString();


        if (shareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setQuote(send)
                    .setContentUrl(Uri.parse("...."))
                    .setContentDescription("Facebook Entegrasyonu Tamamlandı" + " " + send)
                    .build();

            shareDialog.show(shareLinkContent);
        }


    }
    /*

    private void shareTwitter(String message){

        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT,message);
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    */

    /**
     * Twitter hesabına login olmasını saglayan metod
     */
    private void loginToTwitter() {
        //StrictMode kullanarak,ağ erişiminin güvenli bir şekilde yapılmasını sağlıyoruz...
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    //Twitter Kullanıcısının arkadaşlarının gönderdiği, Tweetlerin Twitter hesabından cekiyoruz
    public void getTweets() {
        //Twitter uygulamasıyla baglantıyı sağlayan key değerlerini atadık
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
                .setOAuthAccessToken(PREF_KEY_OAUTH_TOKEN)
                .setOAuthAccessTokenSecret(PREF_KEY_OAUTH_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        //Twitter objesini GetTweets sınıfına gönderdik
        Intent i = new Intent(this, GetTweets.class);
        i.putExtra("twitter", twitter);
        startActivity(i);


    }

    /**
     * Kullanıcı twitter dan logout(çıkış) yapmasını saglayan metod
     * It will just clear the application shared preferences
     */
    private void logoutFromTwitter() {
        // Token ve login gibi değişken değerlerini temizledik(sildik)
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.commit();

        //Kullanıcı  twitter dan çıkış yaptıgında, send, güncelleme durumu gibi arayuz elemanlarını gizledim
        btnLogoutTwitter.setVisibility(View.GONE);
        btnUpdateStatus.setVisibility(View.GONE);
        btnGetTweets.setVisibility(View.GONE);
        txtUpdate.setVisibility(View.GONE);
        lblUpdate.setVisibility(View.GONE);
        lblUserName.setText("");
        lblUserName.setVisibility(View.GONE);
        //ve login butonunu görünür yaptım
        btnLoginTwitter.setVisibility(View.VISIBLE);
    }

    private boolean isTwitterLoggedInAlready() {

        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    protected void onResume() {
        super.onResume();
    }

    /**
     * Twitter kullanıcısının, durum yazısını gönderen sınıf
     */
    class updateTwitterStatus extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ForPaylasim.this);
            pDialog.setMessage("Durum mesajı gönderiliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

                // Access Token
                String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // kullanıcısının, durum yazısını gönderdik(güncelleme yaptık)
                twitter4j.Status response = twitter.updateStatus(status);

            } catch (TwitterException e) {

                Log.d("Twitter Update Error", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Durum yazısı başarılı şekilde tweet yapıldı ", Toast.LENGTH_SHORT)
                            .show();

                    txtUpdate.setText("");
                }
            });
        }

    }


}
