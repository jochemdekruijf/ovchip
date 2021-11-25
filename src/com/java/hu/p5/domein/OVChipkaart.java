package com.java.hu.p5.domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartnummer;
    private Date gedigTot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;
    private List<Product> producten;

    public OVChipkaart() {
        this.producten = new ArrayList<>();
    }

    public OVChipkaart(int kaartnummer, Date gedigTot, int klasse, int saldo, Reiziger reiziger) {
        this.kaartnummer = kaartnummer;
        this.gedigTot = gedigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        this.producten = new ArrayList<>();
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

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        if (!producten.contains(product)) {
            this.producten.add(product);
        }
        if (!product.getOvs().contains(this)) {
            product.addOv(this);
        }
    }

    public void removeProduct(Product product) {
        this.producten.remove(product);
        product.removeOv(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaartnummer == that.kaartnummer;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", gedigTot=" + gedigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                ", producten=" + producten +
                '}';
    }
}
