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

    @Override
    public boolean save(OVChipkaart ov) {
        try {
            String sql = "insert into ov_chipkaart (kaart_nummer,geldig_tot, klasse, saldo, reiziger_id) values (?,?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());
            statement.setString(2, ov.getGedigTot());
            statement.setString(3, ov.getKlasse());
            statement.setString(4, ov.getSaldo());
            statement.setDate(5, ov.getReiziger_id());

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
                    " klasse =?, saldo = ? WHERE reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ov.getKaartnummer());
            statement.setString(2, ov.getGedigTot());
            statement.setString(3, ov.getKlasse());
            statement.setDate(4, ov.getSaldo());
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
    public boolean delete(OVChipkaart ov) {
        try {
            String sql = "delete from ov_chipkaart where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getReiziger_id());

            if(statement.executeUpdate() != 0){
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public OVChipkaart findByReiziger(Reiziger reiziger) {
        adao = new AdresDAOPsql(conn);

        Reiziger re = new Reiziger();

        try {
            String sql = "select * from ov_chipkaart where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setInt(1, ov.getR);
            ResultSet result =  statement.executeQuery();


            while(result.next()) {
//                re.setId(result.getInt("reiziger_id"));
//                re.setVoorletters(result.getString("voorletters"));
//                re.setTussenvoegsel(result.getString("tussenvoegsel"));
//                re.setAchternaam(result.getString("achternaam"));
//                re.setGeboortedatum(result.getDate("geboortedatum"));
//                re.setAdres(adao.findByReiziger(re));
            }

            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return re;
    }

    @Override
    public List<OVChipkaart> findAll() {
        adao = new AdresDAOPsql(conn);

        ArrayList<OVChipkaart> rgs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ov_chipkaart";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                Reiziger r = new OVChipkaart();
//                r.setId(result.getInt("reiziger_id"));
//                r.setVoorletters(result.getString("voorletters"));
//                r.setTussenvoegsel(result.getString("tussenvoegsel"));
//                r.setAchternaam(result.getString("achternaam"));
//                r.setGeboortedatum(result.getDate("geboortedatum"));
                rgs.add(r);
            }
            return rgs;

        } catch (Exception e) {
            e.getMessage();
        }
        return rgs;
    }
}
