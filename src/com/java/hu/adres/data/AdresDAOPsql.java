package com.java.hu.adres.data;

import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.data.ReizigerDAO;
import com.java.hu.reiziger.data.ReizigerDAOPsql;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{
    private Connection conn;
    ReizigerDAO reizigerDAO;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres){
        try {
            String sql = "insert into adres (postcode, huisnummer, straat, woonplaats) values (?,?, ?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());

            if (statement.executeUpdate() != 0) {
                return true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
       return false;
    }

    @Override
    public boolean update(Adres adres){
        try {
            String sql = "UPDATE  adres SET postcode= ?, huisnummer= ?," +
                    " straat =?, woonplaats = ? WHERE adres_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getId());

            if (statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String sql = "delete from adres where adres_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, adres.getId());
            if (statement.executeUpdate() != 0) {
                return true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Adres adres = null;
        try {
            String sql = "select * from adres where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reiziger.getId());
            ResultSet result =  statement.executeQuery();

            Adres a = new Adres();

            while(result.next()) {
                a.setId(result.getInt("adres_id"));
                a.setPostcode(result.getString("postcode"));
                a.setHuisnummer(result.getString("huisnummer"));
                a.setStraat(result.getString("straat"));
                a.setWoonplaats(result.getString("woonplaats"));
                a.setReiziger(reiziger);
            }
            result.close();

        } catch (Exception e) {
            e.getMessage();
        }
        return adres;
//        Adres adres = null;
//        try {
//
//            PreparedStatement statement = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
//
//            statement.setInt(1, reiziger.getId());
//
//            ResultSet result = statement.executeQuery();
//
//            while (result.next()) {
//                adres = new Adres(
//                        result.getInt("adres_id"),
//                        result.getString("postcode"),
//                        result.getString("huisnummer"),
//                        result.getString("straat"),
//                        result.getString("woonplaats"),
//                        reiziger);
//            }
//
//            result.close();
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return adres;
    }

    @Override
    public List<Adres> findAll(){
        ReizigerDAO rdao = new ReizigerDAOPsql(conn);
        ArrayList<Adres> adresList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM adres";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);



            while(result.next()) {
                Adres a = new Adres();
                a.setId(result.getInt("adres_id"));
                a.setPostcode(result.getString("postcode"));
                a.setHuisnummer(result.getString("huisnummer"));
                a.setStraat(result.getString("straat"));
                a.setWoonplaats(result.getString("woonplaats"));
                adresList.add(a);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return adresList;
    }
}
