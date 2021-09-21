package com.java.hu.reiziger.data;

import com.java.hu.OVChipkaart.data.OVChipkaartDAO;
import com.java.hu.OVChipkaart.data.OVChipkaartDAOPsql;
import com.java.hu.OVChipkaart.domein.OVChipkaart;
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
    private OVChipkaartDAO ovdao;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public ReizigerDAOPsql(Connection conn, AdresDAOPsql adao, OVChipkaartDAOPsql ovdao) {
        this.conn = conn;
        this.adao = adao;
        this.ovdao = ovdao;
        adao.setReizigerDAO(this);
        ovdao.setReizigerDAO(this);
    }

    public ReizigerDAOPsql(Connection conn, AdresDAOPsql adao) {
        this.conn = conn;
        this.adao = adao;
        adao.setReizigerDAO(this);
    }

    public ReizigerDAOPsql(Connection conn, OVChipkaartDAOPsql ovdao) {
        this.conn = conn;
        this.ovdao = ovdao;
        ovdao.setReizigerDAO(this);
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
                if(reiziger.getAdres() != null) {
                    adao.save(reiziger.getAdres());
                }

                if(reiziger.getOvs() != null) {
                    if (!reiziger.getOvs().isEmpty()) {
                        for (OVChipkaart o : reiziger.getOvs()) {
                            ovdao.save(o);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.getMessage();
            return false;
        }

        return true;
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
                if(reiziger.getAdres() == null) {
                    return true;
                }

                if (!adao.update(reiziger.getAdres())) {
                    adao.save(reiziger.getAdres());
                }

                if (ovdao.findByReiziger(reiziger).containsAll(reiziger.getOvs())){
                    for (OVChipkaart o : reiziger.getOvs()) {
                        ovdao.update(o);
                    }
                } else {
                    List<OVChipkaart> ovs= new ArrayList<>(reiziger.getOvs());
                    ovs.removeAll(ovdao.findByReiziger(reiziger));
                    ovs.forEach(o -> ovdao.save(o));
                }

            }

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String sql = "delete from reiziger where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reiziger.getId());

            if(statement.executeUpdate() != 0){
                if(reiziger.getAdres() != null) {
                    adao.delete(reiziger.getAdres());
                }

                if(reiziger.getOvs() != null) {
                    for (OVChipkaart o : reiziger.getOvs()) {
                        ovdao.delete(o);
                    }
                }
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {

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
                re.setOvs(ovdao.findByReiziger(re));
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
                r.setAdres(adao.findByReiziger(r));
                r.setOvs(ovdao.findByReiziger(r));
                rgs.add(r);
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

                r.setAdres(adao.findByReiziger(r));

//                if (ovdao.findByReiziger(r) != null) {
//                    r.setOvs(ovdao.findByReiziger(r));
//                }


            }
            return rgs;

        } catch (Exception e) {
            e.getMessage();
        }
        return rgs;
    }
}
