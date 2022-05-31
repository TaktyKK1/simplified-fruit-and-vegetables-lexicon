package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import java.util.ArrayList;
import java.util.Comparator;

public class Roslina {

    public static ArrayList<Roslina> roslinyArrayList = new ArrayList<>();
    public static String ROSLINA_EDIT_EXTRA =  "roslinaEdit";

    private int id;
    private String nazwa;
    private String opis;
    private String typ;


    public Roslina(String nazwa, String opis, String typ) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.typ = typ;
    }

    public Roslina(int id, String nazwa, String opis, String typ) {
        this.id = id;
        this.nazwa = nazwa;
        this.opis = opis;
        this.typ = typ;
    }


    public static Roslina getRoslinaForID(int passedRoslinaID) {
        for (Roslina roslina : roslinyArrayList)
        {
            if(roslina.getId() == passedRoslinaID)
                return roslina;
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }


    public static Comparator<Roslina> nazwaAZ = (roslina1, roslina2) -> {
        String name1 = roslina1.getNazwa();
        String name2 = roslina2.getNazwa();
        return name1.compareToIgnoreCase(name2);
    };

}
