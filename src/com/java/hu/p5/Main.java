package com.java.hu.p5;

import com.java.hu.p5.data.*;
import com.java.hu.p5.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;
import com.java.hu.p5.data.ReizigerDAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
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

    private static void testProductDAO(OVChipkaartDAO ovdao , ReizigerDAO rdao, ProductDAO pdao) {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle producten op uit de database
        List<Product> productList = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : productList) {
            System.out.println(p);
        }
        System.out.println();

        System.out.println("[Test] ProductDAO.findAll() na toevoeging Product:");
        Product product = new Product(7,"Test Naam", "Test Beschr",69);
        pdao.save(product);
        productList = pdao.findAll();
        for (Product p : productList) {
            System.out.println(p);
        }
        System.out.println();

        System.out.println("[Test] ProductDAO.findAll() na verwijdering Product:");
        pdao.delete(product);
        productList = pdao.findAll();
        for (Product p : productList) {
            System.out.println(p);
        }
        System.out.println();

        // Voeg een nieuwe product aan ov-chipkaart toe in de database
        OVChipkaart o = ovdao.findByReiziger(rdao.findById(5)).get(0);
        System.out.println(o);
        Product p = pdao.findAll().get(5);

        System.out.println("[Test] Ov-Chipkaart voor toevoeging Product:");
        System.out.println(o);

        System.out.println("[Test] Ov-Chipkaart na toevoeging Product:");
        o.addProduct(p);
        ovdao.update(o);
        System.out.println(o);
        System.out.println();

        // Verwijder het product van de ov-chipkaart in de database
        System.out.println("[Test] OV-Chipkaart voor verwijdering product:");
        System.out.println(o);

        System.out.println("[Test] OV-Chipkaart na verwijdering product:");
        o.removeProduct(p);
        pdao.update(p);
        ovdao.update(o);
        System.out.println(o);
        System.out.println();

    }

    public static void main(String[] args) throws SQLException {
        getConnection();

        ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn, new OVChipkaartDAOPsql(conn, new ReizigerDAOPsql(conn), new ProductDAOPsql(conn)));

        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(conn, rdao, new ProductDAOPsql(conn));

        ProductDAOPsql pdao = new ProductDAOPsql(conn, ovdao, rdao);


        testProductDAO( ovdao,rdao, pdao);
        closeConnection();
    }
}
