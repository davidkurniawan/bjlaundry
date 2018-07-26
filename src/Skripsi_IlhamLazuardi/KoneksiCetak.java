/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Skripsi_IlhamLazuardi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Web Programmer
 */
public class KoneksiCetak {
    public static String PathReport = System.getProperty("user.dir") + "/src/Laporan/";
    public static Connection koneksi;
    public static Connection ConnectDb(){
        if (koneksi == null){
            try{
                String url = new String();
                String user = new String();
                String password = new String();
                url = "jdbc:mysql://localhost:3306/skripsi";
                user ="root";
                password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
            } catch (SQLException e){
                System.out.print("koneksi error");
            } 
        }
        return null;
    }
}
