package com.example.darek.lekcja13;

/**
 * Created by Darek on 2017-07-04.
 */

public class Phone {
    private long id;
    private String marka;
    private String nazwa;
    private String opis;

    public Phone(long id, String marka, String nazwa, String opis){
        this.id = id;
        this.marka = marka;
        this.nazwa = nazwa;
        this.opis = opis;
    }

    public long getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }
}
