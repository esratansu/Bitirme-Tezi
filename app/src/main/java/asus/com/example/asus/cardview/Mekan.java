package asus.com.example.asus.cardview;

/**
 * Created by ASUS on 26.04.2018.
 */

public class Mekan {
    private String isim, adres;

    public Mekan(String isim, String adres) {
        this.isim = isim;
        this.adres = adres;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}