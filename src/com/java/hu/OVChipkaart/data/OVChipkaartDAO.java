package com.java.hu.OVChipkaart.data;

import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;
import com.java.hu.reiziger.domein.Reiziger;

import java.util.Collection;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save (OVChipkaart ov);
    public boolean update (OVChipkaart ov);
    public boolean delete (OVChipkaart ov);
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public List<OVChipkaart> findAll ();

    Collection<Object> findByProduct(Product pr);
}
