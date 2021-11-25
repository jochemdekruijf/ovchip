package com.java.hu.p5.data;

import com.java.hu.p5.domein.OVChipkaart;
import com.java.hu.p5.domein.Product;

import java.util.List;

public interface ProductDAO {
    public boolean save (Product pr);
    public boolean update (Product pr);
    public boolean delete (Product pr);
    public List<Product> findByOv(OVChipkaart ov);
    public List<Product> findAll ();
}
