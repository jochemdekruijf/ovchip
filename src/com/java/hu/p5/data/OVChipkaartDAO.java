package com.java.hu.p5.data;

import com.java.hu.p5.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;
import com.java.hu.p5.domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean save (OVChipkaart ov);
    public boolean update (OVChipkaart ov);
    public boolean delete (OVChipkaart ov);
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findByProduct(Product pr);
    public List<OVChipkaart> findAll ();
}
