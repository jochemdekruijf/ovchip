package com.java.hu.OVChipkaart.data;

import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.adres.data.AdresDAOPsql;
import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql  implements OVChipkaartDAO{
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ov) {
        try {
            String sql = "insert into ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) values (?,?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());
            statement.setDate(2, ov.getGedigTot());
            statement.setInt(3, ov.getKlasse());
            statement.setInt(4, ov.getSaldo());
            statement.setInt(5, ov.getReiziger_id());

            if(statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }

    @Override
    public boolean update(OVChipkaart ov) {
        try {
            String sql = "UPDATE  ov_chipkaart SET kaart_nummer= ?, geldig_tot= ?," +
                    " klasse =?, saldo = ? WHERE kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());
            statement.setDate(2, ov.getGedigTot());
            statement.setInt(3, ov.getKlasse());
            statement.setInt(4, ov.getSaldo());
            statement.setInt(5, ov.getKaartnummer());

            if(statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ov) {
        try {
            String sql = "delete from ov_chipkaart where kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());

            if(statement.executeUpdate() != 0){
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public OVChipkaart findById(int id) {
        OVChipkaart ov = new OVChipkaart();

        try {
            String sql = "select * from ov_chipkaart where kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result =  statement.executeQuery();


            while(result.next()) {
                ov.setKaartnummer(result.getInt("kaart_nummer"));
                ov.setGedigTot(result.getDate("geldig_tot"));
                ov.setKlasse(result.getInt("klasse"));
                ov.setSaldo(result.getInt("saldo"));
                ov.setReiziger_id(result.getInt("reiziger_id"));
            }

            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return ov;
    }

    @Override
    public OVChipkaart findByReiziger(int rid) {
        OVChipkaart ov = new OVChipkaart();

        try {
            String sql = "select * from ov_chipkaart where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, rid);
            ResultSet result =  statement.executeQuery();


            while(result.next()) {
                ov.setKaartnummer(result.getInt("kaart_nummer"));
                ov.setGedigTot(result.getDate("geldig_tot"));
                ov.setKlasse(result.getInt("klasse"));
                ov.setSaldo(result.getInt("saldo"));
                ov.setReiziger_id(result.getInt("reiziger_id"));
            }

            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return ov;
    }

    @Override
    public List<OVChipkaart> findAll() {

        ArrayList<OVChipkaart> ovs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ov_chipkaart";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                OVChipkaart ov = new OVChipkaart();
                ov.setKaartnummer(result.getInt("kaart_nummer"));
                ov.setGedigTot(result.getDate("geldig_tot"));
                ov.setKlasse(result.getInt("klasse"));
                ov.setSaldo(result.getInt("saldo"));
                ov.setReiziger_id(result.getInt("reiziger_id"));
                ovs.add(ov);
            }
            return ovs;

        } catch (Exception e) {
            e.getMessage();
        }
        return ovs;
    }
}
