package com.java.hu.reiziger.data;

import com.java.hu.adres.data.AdresDAO;
import com.java.hu.adres.data.AdresDAOPsql;
import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection conn;
    private AdresDAO adao;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String sql = "insert into reiziger (reiziger_id,voorletters, tussenvoegsel, achternaam, geboortedatum) values (?,?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());

            if(statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String sql = "UPDATE  reiziger SET voorletters= ?, tussenvoegsel= ?," +
                    " achternaam =?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, reiziger.getGeboortedatum());
            statement.setInt(5, reiziger.getId());

            if(statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String sql = "delete from reiziger where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reiziger.getId());

            if(statement.executeUpdate() != 0){
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        adao = new AdresDAOPsql(conn);

        Reiziger re = new Reiziger();

        try {
            String sql = "select * from reiziger where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result =  statement.executeQuery();


            while(result.next()) {
                re.setId(result.getInt("reiziger_id"));
                re.setVoorletters(result.getString("voorletters"));
                re.setTussenvoegsel(result.getString("tussenvoegsel"));
                re.setAchternaam(result.getString("achternaam"));
                re.setGeboortedatum(result.getDate("geboortedatum"));
                re.setAdres(adao.findByReiziger(re));
            }

            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return re;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        adao = new AdresDAOPsql(conn);

        ArrayList<Reiziger> rgs = new ArrayList<>();

        try {

            String sql = "select * from reiziger where geboortedatum = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(datum));
            ResultSet result = statement.executeQuery();



            while(result.next()) {
                Reiziger r = new Reiziger();
                r.setId(result.getInt("reiziger_id"));
                r.setVoorletters(result.getString("voorletters"));
                r.setTussenvoegsel(result.getString("tussenvoegsel"));
                r.setAchternaam(result.getString("achternaam"));
                r.setGeboortedatum(result.getDate("geboortedatum"));
                rgs.add(r);

                r.setAdres(adao.findByReiziger(r));
            }
            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return rgs;
    }

    @Override
    public List<Reiziger> findAll(){
        adao = new AdresDAOPsql(conn);

        ArrayList<Reiziger> rgs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM reiziger";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                Reiziger r = new Reiziger();
                r.setId(result.getInt("reiziger_id"));
                r.setVoorletters(result.getString("voorletters"));
                r.setTussenvoegsel(result.getString("tussenvoegsel"));
                r.setAchternaam(result.getString("achternaam"));
                r.setGeboortedatum(result.getDate("geboortedatum"));
                rgs.add(r);
            }
            return rgs;

        } catch (Exception e) {
            e.getMessage();
        }
        return rgs;
    }
}
