package com.java.hu;

import com.java.hu.OVChipkaart.data.OVChipkaartDAO;
import com.java.hu.OVChipkaart.data.OVChipkaartDAOPsql;
import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.adres.data.AdresDAOPsql;
import com.java.hu.reiziger.data.ReizigerDAO;
import com.java.hu.reiziger.data.ReizigerDAOPsql;
import com.java.hu.reiziger.domein.Reiziger;


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

//        List<OVChipkaart> ovs = ovdao.findAll();
//
//        // Haalt alle ovs op
//        System.out.println("OVChipkaart.findAll() geeft de volgende kaarten:");
//        for (OVChipkaart o : ovs) {
//            System.out.println(o);
//        }
//        System.out.println();
//        OVChipkaart ov = new OVChipkaart(1300, java.sql.Date.valueOf("2021-5-15"), 2, 130, 3);
//
//        // Voegt een ovchipkaart toe
//
//        System.out.print("Vooraf " + ovs.size() + " ovs, erna OVChipkaart.save()");
//        ovdao.save(ov);
//        ovs = ovdao.findAll();
//        System.out.println(ovs.size() + " ovs");
//        System.out.println();
//
//        // Update een ov chipkaart
//        System.out.println("info voor update" + ov);
//        ov.setKlasse(1);
//        ov.setSaldo(1500);
//        ovdao.update(ov);
//        OVChipkaart updatedOV = ovdao.findById(ov.getKaartnummer());
//        System.out.println("info na update  " + updatedOV);
//        System.out.println();
//
//        // verwijdert een ovchipkaart
//        System.out.print("Vooraf " + ovs.size() + " ovs, erna OVChipkaart.delete()");
//        ovdao.delete(ov);
//        ovs = ovdao.findAll();
//        System.out.println(ovs.size() + " ovs");
//        System.out.println();
//
//        // Haalt een ov op reiziger id
//        System.out.println("Haalt een ov op basis van reiziger id");
////        OVChipkaart o = ovdao.findByReiziger(2);
//        System.out.println(o);
//        System.out.println();

    }

    private static void testOVChipkaartReizigerBidirectionalRelation(OVChipkaartDAO ovdao, ReizigerDAO rdao){
        System.out.println("test bidirectionele relatie");
//        Reiziger r = new Reiziger(205, "K", "", "Janssen", java.sql.Date.valueOf("2021-12-1"));
//        OVChipkaart ov2 = new OVChipkaart(1400, java.sql.Date.valueOf("2020-6-15"), 1, 1330, 205);
//        // set en persisteer de data
//        r.setOV(ov2);
//        rdao.save(r);
//        ovdao.save(ov2);
//
//        // haalt de data op
//        System.out.println("OV van de reiziger");
//        OVChipkaart o = ovdao.findByReiziger(r);
//        System.out.println(o);
//        System.out.println();
//
//        System.out.println("Data van de reiziger van de ov");
//        Reiziger re = rdao.findById(o.getReiziger());
//        System.out.println(re);
//        System.out.println();
//
//        rdao.delete(r);
//        ovdao.delete(ov2);
    }

    public static void main(String[] args) throws SQLException {
        getConnection();
        testOVChipkaartDAO(new OVChipkaartDAOPsql(conn));
        testOVChipkaartReizigerBidirectionalRelation(new OVChipkaartDAOPsql(conn), new ReizigerDAOPsql(conn));
        closeConnection();
    }
}
