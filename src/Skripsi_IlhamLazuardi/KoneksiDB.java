/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Skripsi_IlhamLazuardi;

import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Lazuardi
 */
public class KoneksiDB {
    Connection conn = null;
    public static Connection ConnectDb()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/skripsi","root","");
            return conn;
        }
        catch(ClassNotFoundException | SQLException e)
        {
        JOptionPane.showMessageDialog(null, e);
        return null;
        }
    }
}
