package com.java.hu.adres.data;

import com.java.hu.adres.domein.Adres;
import com.java.hu.reiziger.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    public boolean save (Adres adres) throws SQLException;
    public boolean update (Adres Adres);
    public boolean delete (Adres Adres);
    public Adres findByReiziger (Reiziger reiziger);
    public List<Adres> findAll ();
}
