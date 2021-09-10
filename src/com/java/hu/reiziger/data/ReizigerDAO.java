package com.java.hu.reiziger.data;

import com.java.hu.reiziger.domein.Reiziger;


import java.util.List;

public interface ReizigerDAO {
    public boolean save (Reiziger reiziger);
    public boolean update (Reiziger reiziger);
    public boolean delete (Reiziger reiziger);
    public Reiziger findById (int id);
    public List<Reiziger> findByGbdatum (String datum);
    public List<Reiziger> findAll ();
}
