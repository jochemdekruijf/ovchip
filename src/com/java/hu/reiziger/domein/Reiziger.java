package com.java.hu.reiziger.domein;

import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.adres.domein.Adres;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovs;

    public Reiziger() {
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam , Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        if (tussenvoegsel == null) tussenvoegsel = "";
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public void setOvs(List<OVChipkaart> ovs) {
        this.ovs = ovs;
    }

    public List<OVChipkaart> getOvs() {
        return ovs;
    }

    @Override
    public String toString() {
//        String postcode = "";
//        String huisnummer = "";
//
//        if (adres !=  null) {
//            if (adres.getPostcode() != null) {
//                adres.getPostcode();
//            }
//
//            if (adres.getHuisnummer() != null) {
//                adres.getPostcode();
//            }
//        }

        return "Reiziger{" +
                "id=" + id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum + '\'' +
                ", postcode =" + adres.getPostcode() +
                ", huisnummer =" + adres.getHuisnummer() +
                ", ovs=" + ovs +
                '}';
    }

}
