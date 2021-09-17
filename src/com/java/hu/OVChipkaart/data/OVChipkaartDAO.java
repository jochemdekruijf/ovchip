package com.java.hu.OVChipkaart.data;

import com.java.hu.OVChipkaart.domein.OVChipkaart;
import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save (OVChipkaart ov);
    public boolean update (OVChipkaart ov);
    public boolean delete (OVChipkaart ov);
    public OVChipkaart findByReiziger (Reiziger reiziger);
    public List<OVChipkaart> findAll ();
}
