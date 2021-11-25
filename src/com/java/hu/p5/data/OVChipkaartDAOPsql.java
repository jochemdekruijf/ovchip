package com.java.hu.p5.data;

import com.java.hu.p5.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;
import com.java.hu.p5.domein.Reiziger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;
    private ReizigerDAO rdao;
    private ProductDAO pdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public OVChipkaartDAOPsql(Connection conn, ReizigerDAOPsql rdao, ProductDAOPsql pdao) {
        this.conn = conn;
        this.rdao = rdao;
        this.pdao = pdao;
        rdao.setOvdao(this);

    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }


    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
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
            statement.setInt(5, ov.getReiziger().getId());

            if(statement.executeUpdate() != 0) {
                if(ov.getProducten() != null) {
                    ov.getProducten().forEach(product -> pdao.update(product));
                }
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
                if (ov.getProducten() != null) {
                    ov.getProducten().forEach(p -> pdao.update(p));
                }
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ov) {
        if(ov.getProducten() != null) {
            ov.getProducten().forEach(product -> pdao.delete(product));
        }

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
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> ovList = new ArrayList<>();

        try {
            String sql = "select * from ov_chipkaart where reiziger_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, reiziger.getId());
            ResultSet result =  statement.executeQuery();



            while(result.next()) {
                OVChipkaart o = new OVChipkaart();
                o.setKaartnummer(result.getInt("kaart_nummer"));
                o.setGedigTot(result.getDate("geldig_tot"));
                o.setKlasse(result.getInt("klasse"));
                o.setSaldo(result.getInt("saldo"));
                o.setReiziger(reiziger);
//                pdao.findByOVChipkaart(ovChipkaart).forEach(product -> ovChipkaart.addProduct(product));
                ovList.add(o);
            }

            result.close();

        } catch (Exception e) {
            e.getMessage();
        }

        return ovList;
    }

    @Override
    public List<OVChipkaart> findByProduct(Product pr) {
        List<OVChipkaart> ovs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ov_chipkaart_product LEFT JOIN ov_chipkaart oc on" +
                    " oc.kaart_nummer = ov_chipkaart_product.kaart_nummer" +
                    " RIGHT JOIN product p on p.product_nummer = ov_chipkaart_product.product_nummer" +
                    " WHERE p.product_nummer  = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pr.getProduct_nummer());
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                OVChipkaart ov = new OVChipkaart();
                ov.setKaartnummer( result.getInt("kaart_nummer"));
                ov.setGedigTot(result.getDate("geldig_tot"));
                ov.setKlasse(result.getInt("klasse"));
                ov.setSaldo(result.getInt("saldo"));
                ov.setReiziger(rdao.findById(result.getInt("reiziger_id")));
                ov.addProduct(pr);
                ovs.add(ov);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return ovs;
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
                rdao.findById(result.getInt("reiziger_id"));
                pdao.findByOv(ov).forEach(product -> ov.addProduct(product));
                ovs.add(ov);
            }
            return ovs;

        } catch (Exception e) {
            e.getMessage();
        }
        return ovs;
    }
 }

