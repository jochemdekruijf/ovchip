package com.java.hu.OVChipkaart.domein;

import com.java.hu.reiziger.domein.Reiziger;

import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date gedigTot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaartnummer, Date gedigTot, int klasse, int saldo, Reiziger reiziger) {
        this.kaartnummer = kaartnummer;
        this.gedigTot = gedigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger= reiziger;
    }

    public OVChipkaart() {

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

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", gedigTot=" + gedigTot +
                ", klasse='" + klasse + '\'' +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
