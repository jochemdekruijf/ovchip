package com.java.hu.OVChipkaart.domein;

import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date gedigTot;
    private String klasse;
    private int saldo;
    private int reiziger_id;

    public OVChipkaart(int kaartnummer, Date gedigTot, String klasse, int saldo, int reiziger_id) {
        this.kaartnummer = kaartnummer;
        this.gedigTot = gedigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public Date getGedigTot() {
        return gedigTot;
    }

    public void setGedigTot(Date gedigTot) {
        this.gedigTot = gedigTot;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }
}
