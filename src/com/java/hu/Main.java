package com.java.hu;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
      String jbcvURL = "jdbc:postgresql://localhost:5432/ovchip";
      String username = "postgres";
      String password = "root";

    Connection conn = DriverManager.getConnection(jbcvURL, username, password);

    String sql = "SELECT * FROM reiziger";
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery(sql);

    System.out.println("Alle reizigers:");
    while (result.next()) {
        String s = "";
        s += "#" + result.getInt("reiziger_id") + " ";
        s += result.getString("voorletters") + " ";

        if (result.getString("tussenvoegsel") != null) {
            s += " " + result.getString("tussenvoegsel") + " ";
        }

        s += " " + result.getString("achternaam") + " ";
        s += " (" + result.getDate("geboortedatum") + ") ";
        System.out.println(s);
    }
    statement.close();
    result.close();
    conn.close();
    }
}
