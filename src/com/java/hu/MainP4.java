package com.java.hu;

import com.java.hu.OVChipkaart.data.OVChipkaartDAO;
import com.java.hu.OVChipkaart.data.OVChipkaartDAOPsql;
import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.adres.data.AdresDAOPsql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainP4 {
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

    private static void testOVChipkaartDAO(OVChipkaartDAO ovdao){
        System.out.println("Test OVChipkaar DAO :");

        List<OVChipkaart> ovs = ovdao.findAll();

        // Haalt alle ovs op
        System.out.println("OVChipkaart.findAll() geeft de volgende kaarten:");
        for (OVChipkaart o : ovs) {
            System.out.println(o);
        }
        System.out.println();
        OVChipkaart ov = new OVChipkaart(1300, java.sql.Date.valueOf("2021-5-15"), 2, 130, 3);

        // Voegt een ovchipkaart toe

        System.out.print("Vooraf " + ovs.size() + " ovs, erna OVChipkaart.save()");
        ovdao.save(ov);
        ovs = ovdao.findAll();
        System.out.println(ovs.size() + " ovs");
        System.out.println();

        // Update een ov chipkaart
        System.out.println("info voor update" + ov);
        ov.setKlasse(1);
        ov.setSaldo(1500);
        ovdao.update(ov);
        OVChipkaart updatedOV = ovdao.findById(ov.getKaartnummer());
        System.out.println("info na update  " + updatedOV);
        System.out.println();

        // verwijdert een ovchipkaart
        System.out.print("Vooraf " + ovs.size() + " ovs, erna OVChipkaart.delete()");
        ovdao.delete(ov);
        ovs = ovdao.findAll();
        System.out.println(ovs.size() + " ovs");
        System.out.println();

        // Haalt een ov op id
        System.out.println("Haalt een ov op basis van id");
        OVChipkaart o = ovdao.findByReiziger(2);
        System.out.println(o);
        System.out.println();


    }

    public static void main(String[] args) throws SQLException {
        getConnection();
        testOVChipkaartDAO(new OVChipkaartDAOPsql(conn));
        closeConnection();
    }
}
