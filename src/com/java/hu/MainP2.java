package com.java.hu;

import com.java.hu.adres.data.AdresDAO;
import com.java.hu.adres.data.AdresDAOPsql;
import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.data.ReizigerDAO;
import com.java.hu.reiziger.data.ReizigerDAOPsql;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainP2 {
    private static Connection conn;

    public static void  getConnection() throws SQLException {
        String jbcvURL = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "root";
         conn =  DriverManager.getConnection(jbcvURL, username, password);

    }

    private static void closeConnection() throws SQLException {
        conn.close();
    }


    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("Test ReizigerDAO :");

        // Haalt alle reizigers op
        List<Reiziger> reizigers = rdao.findAll();

        System.out.println("ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Maakt een nieuwe reiziger aan en slaat deze op
        String gbd = "1881-05-14";
        Reiziger rei = new Reiziger(105, "K", "", "Janssen", java.sql.Date.valueOf(gbd));
        System.out.print("Vooraf " + reizigers.size() + " reizigers, erna ReizigerDAO.save()");
        rdao.save(rei);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        // Haalt een reizigers op id
        System.out.println("ReizigerDAO.findById() haalt de volgende reiziger op:");
        System.out.println(rdao.findById(105));

        // Haalt een reizigers op via geboortedatum
        reizigers = rdao.findByGbdatum("2002-09-17");
        System.out.println("ReizigerDAO.findByGbdatum() haalt de volgende reizgers op:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }


        // Verandert de reiziger info
        System.out.println("Reiziger voor update:");
        System.out.println(rdao.findById(105).toString());
        rei.setVoorletters("L");
        rei.setTussenvoegsel("de");
        rei.setAchternaam("Lange");
        rei.setGeboortedatum(java.sql.Date.valueOf("1794-5-15"));
        rdao.update(rei);
        System.out.println("Reiziger info  na verandering:");
        System.out.println(rdao.findById(105));
        System.out.println();

        // Verwijdert de nieuwe reiziger
        reizigers = rdao.findAll();
        System.out.print("Vooraf " + reizigers.size() + " reizigers en erna ReizigerDAO.delete() ");
        rdao.delete(rei);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

    }

    private static void testAdresDAO(AdresDAO adao , ReizigerDAO rdao) throws SQLException {
        System.out.println("Test AdresDAO:");

        // Haalt alle adressen op
        List<Adres> adressen = adao.findAll();
        System.out.println("AdresDAO.findAll() haalt de volgende adressen op:");
        for (Adres a : adressen) {
            System.out.println(a.toString());
        }

        // Voegt een nieuwe adres toe
        System.out.println("Reiziger voor het toevoegingen van een adres:");
        System.out.println(rdao.findById(105));
        Adres a = new Adres(4, "1234AB", "12C", "teststraat", "groningen", rdao.findById(4));
        adao.save(a);
        System.out.println(" Reiziger na het toevoeging van een adres:");
        System.out.println(rdao.findById(105));


        // Verandert het adres
        System.out.println("Adres voor de update:");
        System.out.println(a.toString());
        a.setHuisnummer("6");
        adao.update(a);
        System.out.println("Adres na de update:");
        System.out.println(a);

        // Haalt het adres van de reiziger op
        System.out.println("Adres van reiziger " + rdao.findById(105).getAdres() + ":");
        System.out.println(adao.findByReiziger(rdao.findById(105)));

        // Verwijdert het nieuwe adres
        adressen = adao.findAll();
        System.out.print("Vooraf " + adressen.size() + " adressen, erna AdresDAO.delete() ");
        adao.delete(a);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen");

    }

    public static void main(String[] args) throws SQLException {
        getConnection();
        testReizigerDAO(new ReizigerDAOPsql(conn));
        testAdresDAO(new AdresDAOPsql(conn), new ReizigerDAOPsql(conn));
        closeConnection();

    }
}
