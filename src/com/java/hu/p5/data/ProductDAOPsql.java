package com.java.hu.p5.data;

import com.java.hu.p5.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{
    private Connection conn;
    private OVChipkaartDAO ovdao;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public ProductDAOPsql(Connection conn, OVChipkaartDAO ovdao) {
        this.conn = conn;
        this.ovdao = ovdao;
    }

//    public ProductDAOPsql(Connection conn, OVChipkaartDAOPsql ovdao,ReizigerDAOPsql  rdao) {
//        this.conn = conn;
//        ovdao.setRdao(rdao);
//        ovdao.setPdao(this);
//        this.ovdao = ovdao;
//    }

    public ProductDAOPsql(Connection conn, OVChipkaartDAOPsql ovdao, ReizigerDAOPsql rdao) {
        this.conn = conn;
        ovdao.setRdao(rdao);
        ovdao.setPdao(this);
        this.ovdao = ovdao;
    }

    @Override
    public boolean save(Product pr) {
        try {

            String sql = "insert into product (product_nummer, naam, beschrijving, prijs) values (?,?, ?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pr.getProduct_nummer());
            statement.setString(2, pr.getNaam());
            statement.setString(3, pr.getBeschrijving());
            statement.setDouble(4, pr.getPrijs());

            if(statement.executeUpdate() != 0) {
                if (pr.getOvs() != null) {
                    pr.getOvs().forEach(ovChipkaart -> addOvChipkaartProduct(ovChipkaart, pr));
                }
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }

    @Override
    public boolean update(Product pr) {

        try {
            String sql = "UPDATE  product SET product_nummer= ?, naam= ?," +
                    " beschrijving =?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pr.getProduct_nummer());
            statement.setString(2, pr.getNaam());
            statement.setString(3, pr.getBeschrijving());
            statement.setDouble(4, pr.getPrijs());

            if(statement.executeUpdate() != 0) {
                if (pr.getOvs() != null) {
                    pr.getOvs().forEach(ovChipkaart -> {
                        if (!ovdao.findByProduct(pr).contains(ovChipkaart)) {
                            if(!deleteOvChipkaartProduct(ovChipkaart, pr)) {
                                addOvChipkaartProduct(ovChipkaart, pr);
                            }
                        }
                    });
                }

            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean delete(Product pr) {
        if (pr.getOvs() != null) {
            pr.getOvs().forEach(ovChipkaart -> deleteOvChipkaartProduct(ovChipkaart, pr));
        }

        try {
            String sql = "delete from product where product_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pr.getProduct_nummer());

            if(statement.executeUpdate() != 0){
                return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @Override
    public List<Product> findByOv(OVChipkaart ov) {
        ArrayList<Product> producten = new ArrayList<>();

        try {
            String sql = "select *\n" +
                    "from ov_chipkaart_product as ovpr\n" +
                    "join product as pr on ovpr.product_nummer  = pr.product_nummer\n" +
                    "where kaart_nummer = ? ";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Product p = new Product();
                p.setProduct_nummer(result.getInt("product_nummer"));
                p.setNaam(result.getString("naam"));
                p.setBeschrijving( result.getString("beschrijving"));
                p.setPrijs(result.getDouble("prijs"));
                p.addOv(ov);
                producten.add(p);
            }
            return producten;

        } catch (Exception e) {
            e.getMessage();
        }
        return producten;
    }

    @Override
    public List<Product> findAll() {
        ArrayList<Product> producten = new ArrayList<>();

        try {
            String sql = "select * from product";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                Product p = new Product();
                p.setProduct_nummer(result.getInt("product_nummer"));
                p.setNaam(result.getString("naam"));
                p.setBeschrijving(result.getString("beschrijving"));
                p.setPrijs(result.getDouble("prijs"));

                ovdao.findByProduct(p).forEach(ov -> p.addOv(ov));

                producten.add(p);
            }
            return producten;

        } catch (Exception e) {
            e.getMessage();
        }
        return producten;
    }

    public boolean addOvChipkaartProduct(OVChipkaart ov, Product pr) {
        try {
            String sql = "INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, ov.getKaartnummer());
            statement.setInt(2, pr.getProduct_nummer());
            statement.setString(3, "actief");
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));

            if (statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean deleteOvChipkaartProduct(OVChipkaart ov, Product pr) {
        try {
            String sql = "delete from ov_chipkaart_product where product_nummer = ? and kaart_nummer = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, ov.getKaartnummer());
            statement.setInt(2, pr.getProduct_nummer());

            if (statement.executeUpdate() != 0) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
