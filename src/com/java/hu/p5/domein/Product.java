package com.java.hu.p5.domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int  product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaart> ovs;

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovs =  new ArrayList<>();
    }

    public Product() {
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvs() {
        return ovs;
    }

    public void setOvs(List<OVChipkaart> ovs) {
        this.ovs = ovs;
    }

    public void addOv(OVChipkaart ovs) {
        this.ovs.add(ovs);
    }


    public void removeOv(com.java.hu.p5.domein.OVChipkaart ovs) {
        this.ovs.remove(ovs);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovs=" + ovs +
                '}';
    }
}
