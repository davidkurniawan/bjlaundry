/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
    */
   package Skripsi_IlhamLazuardi;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

   /**
    *
    * @author Lazuardi
    */
   public class FormDaftarAgen extends javax.swing.JFrame {
       Connection koneksi;
       ResultSet hasil;
       PreparedStatement proses;

   public final void tampilData(){
        try{
            String sql = "select * from dbdaftaragen";
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery();
            tabeldaftaragen.setModel(DbUtils.resultSetToTableModel(hasil));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "GAGAL KONEKSI!");
        }
    }
    /**
     * Creates new form FormDaftarAgen
     * @param conn
     */
    public FormDaftarAgen() {
        initComponents();
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
        tampilData();
        auto_number();
        namaagen.requestFocus();
    }
    
    public void autonumber()
	{
		try{
        String sql = "SELECT MAX(right(Kodeagen,2)) AS no FROM dbdaftaragen";
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                if(hasil.first() == false){
                    kd_agen.setText("AGEN001");
                    
                }else {
                    hasil.last();
                    int auto_id = hasil.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int noLong  = no.length();
                                  for(int a  = 0; a<3-noLong; a++){
                                      no = "0" + no;
                                  }
                                  kd_agen.setText("AGEN" + no);
                }
                
            }
    } catch (Exception e) {
        JOptionPane.showInternalMessageDialog(this, "Error: \n" + e.toString(),"ADA KESALAHAN!",JOptionPane.WARNING_MESSAGE);
    }
    }
    
    public final void tampil_Data(){
    DefaultTableModel model = new DefaultTableModel();
        model.addColumn("KodeAgen");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        
        String no = btnCari.getText();
    
    try{
            String sql = "SELECT * FROM `dbdaftaragen` WHERE `KodeAgen` "+no+"";
            koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                model.addRow(new Object[]{hasil.getString(2),hasil.getString(3),hasil.getString(4)});
                
                }
            tabeldaftaragen.setModel(model);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "GAGAL KONEKSI!");
        }
    }
    
    private void kosong(){
    kd_agen.setText("");
    namaagen.setText("");
    almtagen.setText("");
    tlpagen.setText("");
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabeldaftaragen = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        almtagen = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        kd_agen = new javax.swing.JTextField();
        namaagen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tlpagen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DAFTAR AGEN");
        setBackground(new java.awt.Color(102, 102, 0));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\LOGO BJ 100x100.png")); // NOI18N
        jLabel9.setText("DAFTAR AGEN LAUNDRY");

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon kembali ke menu 20x20.png")); // NOI18N
        jButton1.setText("Kembali Ke Menu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabeldaftaragen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "KodeAgen", "Nama", "Alamat", "Telepon"
            }
        ));
        tabeldaftaragen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldaftaragenMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabeldaftaragen);

        almtagen.setColumns(20);
        almtagen.setRows(5);
        jScrollPane1.setViewportView(almtagen);

        jLabel1.setText("Kode Agen");

        kd_agen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kd_agenActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama Agen");

        jLabel3.setText("Alamat Agen");

        jLabel4.setText("Tlp Agen");

        btnTambah.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon tambah 20x20.png")); // NOI18N
        btnTambah.setText("Simpan Data");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon reset 20x20.png")); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon hapus 20x20.png")); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon cari 20x20.png")); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon update 20x20.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tlpagen)
                            .addComponent(namaagen)
                            .addComponent(kd_agen)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(146, 146, 146))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel9)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(kd_agen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tlpagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnTambah)
                    .addComponent(jButton1)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FormUtama().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        Connection conn = null;
        Statement stmt = null;

        if (kd_agen.getText().equals("") ||
            namaagen.getText().equals("") ||
            almtagen.getText().equals("") ||
            tlpagen.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "DATA BELUM LENGKAP!");
        }else{
            String kdagen = kd_agen.getText();
            String nama = namaagen.getText();
            String alamat = almtagen.getText();
            String tlp = tlpagen.getText();

            try
            {
                conn = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
                stmt = conn.createStatement();
                stmt.executeUpdate("insert into dbdaftaragen values ('"+kdagen+"', '"+nama+"', '"+alamat+"', '"+tlp+"')");

                JOptionPane.showMessageDialog(null, "DATA BERHASIL DITAMBAHKAN!");
                kd_agen.requestFocus();
                tampilData();
                auto_number();
            }
            catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);

            }
            
        }
        namaagen.setText("");
        almtagen.setText("");
        tlpagen.setText("");
        namaagen.requestFocus();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        namaagen.setText("");
        almtagen.setText("");
        tlpagen.setText("");
        auto_number();
    }//GEN-LAST:event_btnResetActionPerformed

    private void kd_agenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kd_agenActionPerformed
        // TODO add your handling code here:
        if (kd_agen.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "DATA TIDAK ADA!");
            autonumber();
            namaagen.requestFocus();
        }else{
        Connection connj = null;
        Statement stmtj = null;
            
            String no = kd_agen.getText();
            
            try
            {
              
                connj = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
                stmtj = connj.createStatement();
                String query = "select*from dbdaftaragen where KodeAgen like '%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    kd_agen.setText(hasil.getString("kodeagen"));
                    namaagen.setText(hasil.getString("nama"));
                    almtagen.setText(hasil.getString("alamat"));
                    tlpagen.setText(hasil.getString("telepon"));
                }
                
            }catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
            tampilData();
        }
    }//GEN-LAST:event_kd_agenActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(
        null,"Yakin Ingin Menghapus Data?","Konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.YES_NO_OPTION)
        {
          Connection koneksi = null;
        Statement stmt = null;
        
            try
        {
            String sql = ("DELETE FROM `dbdaftaragen` WHERE KodeAgen='"+kd_agen.getText()+"'");
            java.sql.Connection conn=(Connection)KoneksiDB.ConnectDb();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "DATA BERHASIL DI HAPUS!");
        } catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);
            
            }
            tampilData();
            kosong();  
            autonumber();  
        }
        
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        if (kd_agen.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "DATA TIDAK ADA!");
            autonumber();
            namaagen.requestFocus();
        }else{
        Connection connj = null;
        Statement stmtj = null;
            
            String no = kd_agen.getText();
            
            try
            {
              
                connj = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
                stmtj = connj.createStatement();
                String query = "select*from dbdaftaragen where KodeAgen like '%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    kd_agen.setText(hasil.getString("kodeagen"));
                    namaagen.setText(hasil.getString("nama"));
                    almtagen.setText(hasil.getString("alamat"));
                    tlpagen.setText(hasil.getString("telepon"));
                }
                
            }catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
            tampilData();
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void tabeldaftaragenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldaftaragenMouseClicked
        // TODO add your handling code here:
        int baris = tabeldaftaragen.rowAtPoint(evt.getPoint());
        String KodeAgen = tabeldaftaragen.getValueAt(baris, 0).toString();
        kd_agen.setText(KodeAgen);
        
        String Nama = tabeldaftaragen.getValueAt(baris, 1).toString();
        namaagen.setText(Nama);
        
        String Alamat = tabeldaftaragen.getValueAt(baris, 2).toString();
        almtagen.setText(Alamat);
        
        String Telepon = tabeldaftaragen.getValueAt(baris, 3).toString();
        tlpagen.setText(Telepon);
          
    }//GEN-LAST:event_tabeldaftaragenMouseClicked
    
    private void auto_number(){
    try{
        String sql = "SELECT MAX(right(KodeAgen,2)) AS no FROM dbdaftaragen";
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                if(hasil.first() == false){
                    kd_agen.setText("AGEN001");
                    
                }else {
                    hasil.last();
                    int auto_id = hasil.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int noLong  = no.length();
                                  for(int a  = 0; a<3-noLong; a++){
                                      no = "0" + no;
                                  }
                                  kd_agen.setText("AGEN" + no);
                }
                
            }
    } catch (Exception e) {
        JOptionPane.showInternalMessageDialog(this, "Error: \n" + e.toString(),"ADA KESALAHAN!",JOptionPane.WARNING_MESSAGE);
    }
}
    
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Connection koneksi = null;
        Statement stmt = null;
         
            String Kodeagen = this.kd_agen.getText();
            String Nama = this.namaagen.getText();
            String Alamat = this.almtagen.getText();
            String Telepon = this.tlpagen.getText();
            
    try
        {
            
            String sql = ("UPDATE `dbdaftaragen` SET `KodeAgen`='"+Kodeagen+"',`Nama`='"+Nama+"',`Alamat`='"+Alamat+"',`Telepon`='"+Telepon+"' WHERE `KodeAgen`='"+Kodeagen+"'");
            java.sql.Connection conn=(Connection)KoneksiDB.ConnectDb();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "DATA BERHASIL DI UPDATE!");
        } catch(HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, "DATA GAGAL!"+e.getMessage());
            } catch (SQLException ex) {
            Logger.getLogger(FormDaftarAgen.class.getName()).log(Level.SEVERE, null, ex);
        }
            tampilData();
            kosong();
            autonumber();
    }//GEN-LAST:event_btnUpdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDaftarAgen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDaftarAgen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDaftarAgen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDaftarAgen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea almtagen;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kd_agen;
    private javax.swing.JTextField namaagen;
    private javax.swing.JTable tabeldaftaragen;
    private javax.swing.JTextField tlpagen;
    // End of variables declaration//GEN-END:variables
}

