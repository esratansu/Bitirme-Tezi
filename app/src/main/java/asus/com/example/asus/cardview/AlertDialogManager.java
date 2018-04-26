package asus.com.example.asus.cardview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ASUS on 18.04.2018.
 */

public class AlertDialogManager {
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Uyarı pencerisine  baslık atadım
        alertDialog.setTitle(title);

        // Uyarı pencerisine, uyarı mesajını atadım
        alertDialog.setMessage(message);

        if(status != null)
            // Uyarı pencerisine, icon resmi atadım
            alertDialog.setIcon((status) ? R.mipmap.ic_launcher: R.mipmap.ic_launcher);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Uyarı mesajını iceren, uyarı pencerisini gösterttim
        alertDialog.show();
    }
}
