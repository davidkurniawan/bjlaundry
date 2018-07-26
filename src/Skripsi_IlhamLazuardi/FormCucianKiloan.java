/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Skripsi_IlhamLazuardi;

import java.text.SimpleDateFormat;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.FontMetrics;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.time.LocalDate;

/**
 *
 * @author Lazuardi
 */
public class FormCucianKiloan extends javax.swing.JFrame {
    Connection koneksi;
    ResultSet hasil;
    PreparedStatement proses;
    
   
    /**
     * Creates new form FormCucianKiloan
     */
    public FormCucianKiloan() {
        initComponents();
//        tampilData();
        auto_number();
        txtNama.requestFocus();
        Date start = new Date();
//        LocalDate start = LocalDate.now();
        txtTanggal.setDate(start);
    }
    
    public PageFormat getPageFormat(PrinterJob pj)
{
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight =8.0;  
    double headerHeight = 2.0;                  
    double footerHeight = 2.0;                  
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(                    
        0,
        10,
        width,            
        height - convert_CM_To_PPI(1)
    );   //define boarder size    after that print area width is about 180 points
            
    pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
    pf.setPaper(paper);    

    return pf;
}
    
    protected static double convert_CM_To_PPI(double cm) {            
	        return toPPI(cm * 0.393600787);            
}
 
protected static double toPPI(double inch) {            
	        return inch * 72d;            
}






public class BillPrintable implements Printable {
    
   
    
    
  public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
  throws PrinterException 
  {    
      
                
        
      int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    

            double width = pageFormat.getImageableWidth();                    
           
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

            ////////// code by alqama//////////////

            FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        //    int idLength=metrics.stringWidth("000000");
            //int idLength=metrics.stringWidth("00");
            int idLength=metrics.stringWidth("000");
            int amtLength=metrics.stringWidth("000000");
            int qtyLength=metrics.stringWidth("00000");
            int priceLength=metrics.stringWidth("000000");
            int prodLength=(int)width - idLength - amtLength - qtyLength - priceLength-17;

        //    int idPosition=0;
        //    int productPosition=idPosition + idLength + 2;
        //    int pricePosition=productPosition + prodLength +10;
        //    int qtyPosition=pricePosition + priceLength + 2;
        //    int amtPosition=qtyPosition + qtyLength + 2;
            
            int productPosition = 0;
            int discountPosition= prodLength+5;
            int pricePosition = discountPosition +idLength+10;
            int qtyPosition=pricePosition + priceLength + 4;
            int amtPosition=qtyPosition + qtyLength;
            
            
              
        try{
            /*Draw Header*/
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
            int headerRectHeighta=40;
            
            ///////////////// Product names Get ///////////
                String  Kodebon = txtKodeBon.getText();
                String Nama = txtNama.getText();
                String Telepon = txtTelepon.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String Tanggal = dateFormat.format(txtTanggal.getDate());
                Object Jenis = txtJenis.getSelectedItem();
            ///////////////// Product names Get ///////////
                
            
            ///////////////// Product price Get ///////////
                int Harga = Integer.valueOf(txtHarga.getText());
                int Banyak = Integer.valueOf(txtBanyakBarang.getText());
                int Total = Integer.valueOf(txtTotalHarga.getText());
                int Uang = Integer.valueOf(txtUang.getText());
                int Kembalian = Integer.valueOf(txtKembalian.getText());
            ///////////////// Product price Get ///////////
                
             g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawString("-------------------------------------------",12,y);y+=yShift;
            g2d.drawString("            Laundry Bon Receipt            ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString(" KodeBon : "+Kodebon+"              "+Tanggal+" ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------------",10,y);y+=headerRectHeight;
            g2d.drawString(" Nama        : "+Nama+"                    ",10,y);y+=yShift;
            g2d.drawString(" Telepon     : "+Telepon+"                 ",10,y);y+=yShift;
            g2d.drawString(" Jenis       : "+Jenis+"                   ",10,y);y+=yShift;
            g2d.drawString(" Harga       : Rp. "+Harga+"               ",10,y);y+=yShift;
            g2d.drawString(" Banyak      : "+Banyak+"                  ",10,y);y+=yShift;
            g2d.drawString(" Subtotal    : Rp. "+Total+"               ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Total       : Rp. "+Total+"               ",10,y);y+=yShift;
            g2d.drawString(" Uang        : Rp. "+Uang+"                ",10,y);y+=yShift;
            g2d.drawString(" Kembalian   : Rp. "+Kembalian+"           ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------------",10,y);y+=yShift;
            g2d.drawString("                BJ Laundry                 ",10,y);y+=yShift;
            g2d.drawString("Jl. Baru LUK Kavling Star Motor No.42-43   ",10,y);y+=yShift;
            g2d.drawString("             Tangerang Selatan             ",10,y);y+=yShift;
            g2d.drawString(" Telp. 021-728 79228, Fax. 021-758 79228   ",10,y);y+=yShift;
            g2d.drawString("*******************************************",10,y);y+=yShift;
            g2d.drawString("      Terima Kasih Sudah Mempercayai       ",10,y);y+=yShift;
            g2d.drawString("*******************************************",10,y);y+=yShift;
                   
           
             
           
            
//            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
//            g2d.drawString("Customer Shopping Invoice", 30,y);y+=yShift; 
          

    }
    catch(Exception r){
    r.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }
    
//    public final void tampilData(){
//    DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("No");
//        model.addColumn("Kodebon");
//        model.addColumn("Nama");
//        model.addColumn("Alamat");
//        model.addColumn("Telepon");
//        model.addColumn("Jenis Barang");
//        model.addColumn("Total Harga");
//        model.addColumn("Catatan");
//    try{
//            int no=1;
//            String sql = "select * from dbcucikiloan";
//            koneksi = skripsi.KoneksiDB.ConnectDb();
//            proses = koneksi.prepareStatement(sql);
//            hasil = proses.executeQuery(sql);
//            while(hasil.next()){
//                model.addRow(new Object[]{no++,hasil.getString(2),hasil.getString(3),hasil.getString(4),hasil.getString(5),hasil.getString(7),hasil.getString(8),hasil.getString(11)});
//                
//                }
//            tabelKiloan.setModel(model);
//        } catch(Exception e){
//            JOptionPane.showMessageDialog(null, "GAGAL KONEKSI!");
//        }
//    }
//    
    private void auto_number(){
    try{
        String sql = "SELECT MAX(right(Kodebon,2)) AS no FROM dbcucikiloan";
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                if(hasil.first() == false){
                    txtKodeBon.setText("AK001");
                    
                }else {
                    hasil.last();
                    int auto_id = hasil.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int noLong  = no.length();
                                  for(int a  = 0; a<3-noLong; a++){
                                      no = "0" + no;
                                  }
                                  txtKodeBon.setText("AK" + no);
                }
                
            }
    } catch (Exception e) {
        JOptionPane.showInternalMessageDialog(this, "Error: \n" + e.toString(),"ADA KESALAHAN!",JOptionPane.WARNING_MESSAGE);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtBanyakBarang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTelepon = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtKodeBon = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        Kembali = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtJenis = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKiloan = new javax.swing.JTable();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        txtKembalian = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUang = new javax.swing.JTextField();
        BtnPrint = new javax.swing.JButton();
        BtnAdd = new javax.swing.JButton();
        btnTotal = new javax.swing.JButton();
        txtSemuaTotal = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane6.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CUCI KILOAN");

        jLabel16.setText("Alamat");

        jLabel17.setText("Tanggal");

        txtBanyakBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBanyakBarangActionPerformed(evt);
            }
        });
        txtBanyakBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBanyakBarangKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\LOGO BJ 100x100.png")); // NOI18N
        jLabel8.setText("CUCI KILOAN");

        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        jLabel10.setText("Telp");

        jLabel11.setText("Banyak Barang");

        txtTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeleponActionPerformed(evt);
            }
        });

        jLabel14.setText("Harga");

        jLabel13.setText("Kode Bon");

        txtKodeBon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeBonActionPerformed(evt);
            }
        });

        jLabel15.setText("Nama Pelanggan");

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon update 20x20.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnTambah.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon tambah 20x20.png")); // NOI18N
        btnTambah.setText("Simpan Data");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon hapus 20x20.png")); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        Kembali.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon kembali ke menu 20x20.png")); // NOI18N
        Kembali.setText("Kembali Ke Menu");
        Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KembaliActionPerformed(evt);
            }
        });

        jLabel2.setText("Berat");

        txtJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Kg", "2 Kg", "3 Kg", "4 Kg", "5 Kg", "6 Kg", "7 Kg", "8 Kg", "9 Kg", "10 Kg", "11 Kg", "12 Kg", "13 Kg", "14 Kg", "15 Kg", "16 Kg", "17 Kg", "18 Kg", "19 Kg", "20 Kg" }));
        txtJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJenisActionPerformed(evt);
            }
        });

        jLabel3.setText("Total Harga");

        txtTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalHargaActionPerformed(evt);
            }
        });
        txtTotalHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalHargaKeyReleased(evt);
            }
        });

        jLabel12.setText("Cari");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon cari 20x20.png")); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon reset 20x20.png")); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane3.setViewportView(txtAlamat);

        tabelKiloan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "KodeBon", "Nama", "Alamat", "Telepon", "Tanggal", "Jenis Barang", "Harga", "Total Harga", "Banyak Barang"
            }
        ));
        tabelKiloan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKiloanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelKiloan);

        jScrollPane5.setViewportView(jScrollPane1);

        txtTanggal.setDateFormatString("yyyy-MM-dd");

        txtKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKembalianActionPerformed(evt);
            }
        });

        jLabel4.setText("Uang");

        jLabel5.setText("Kembalian");

        txtUang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUangActionPerformed(evt);
            }
        });
        txtUang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUangKeyReleased(evt);
            }
        });

        BtnPrint.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon print 20x20.png")); // NOI18N
        BtnPrint.setText("Cetak Struk");
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });

        BtnAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon tambah 20x20.png")); // NOI18N
        BtnAdd.setText("Tambah");
        BtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAddActionPerformed(evt);
            }
        });

        btnTotal.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon keuntungan 20x20.png")); // NOI18N
        btnTotal.setText("Total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        txtSemuaTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSemuaTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(326, 326, 326))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel17))
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtKodeBon)
                                    .addComponent(txtTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNama))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel12))
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtTotalHarga)
                                            .addComponent(txtBanyakBarang)
                                            .addComponent(txtHarga, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtJenis, javax.swing.GroupLayout.Alignment.LEADING, 0, 248, Short.MAX_VALUE)
                                            .addComponent(txtCari))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(BtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Kembali)
                                .addGap(132, 132, 132))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSemuaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(48, 48, 48)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUang, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(BtnPrint)))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtKodeBon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBanyakBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(BtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCari))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnHapus)
                    .addComponent(btnTambah)
                    .addComponent(Kembali)
                    .addComponent(btnReset))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTotal)
                    .addComponent(txtSemuaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnPrint)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBanyakBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBanyakBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBanyakBarangActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeleponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleponActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Connection koneksi = null;
        Statement stmt = null;
        try {
            
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
        stmt = koneksi.createStatement();
        
       for(int i = 0 ; i <  tabelKiloan.getRowCount(); i++){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String Tanggal = dateFormat.format(txtTanggal.getDate());
                    String Ilham = ("'"+(String)tabelKiloan.getModel().getValueAt(i,0)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,1)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,2)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,3)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,4)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,5)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,6)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,7)+"'"+", ")+("'"+(String)tabelKiloan.getModel().getValueAt(i,8)+"'");

                stmt.executeUpdate("INSERT INTO `dbcucikiloan`(`Kodebon`,`Nama`, `Alamat`,`Telepon`,`Tanggal`,`JenisBarang`,`HargaKiloan`, `TotalHarga`,`BanyakBarang`) VALUES  ("+Ilham+")");
//                System.out.println(fa);
//            }
        }
            JOptionPane.showMessageDialog(null, "DATA BERHASIL DI TAMBAHKAN KE DATABASE!");
        } catch(SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "DATA GAGAL DITAMBAHKAN");
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    public final void search_data(){
    DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kodebon");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        model.addColumn("Tanggal");
        
        model.addColumn("Jenis Barang");
        model.addColumn("Harga");
        model.addColumn("Total Harga");
        model.addColumn("Banyak Barang");
        
        String no = txtCari.getText();
    
    try{
            String sql = "SELECT * FROM `dbcucikiloan` WHERE `Kodebon` LIKE '%"+no+"%'";
            koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                model.addRow(new Object[]{hasil.getString(2),hasil.getString(3),hasil.getString(4),hasil.getString(5),hasil.getString(6),hasil.getString(7),hasil.getString(8),hasil.getString(9),hasil.getString(10)});
                
                }
            tabelKiloan.setModel(model);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "GAGAL KONEKSI!");
        }
    }
    
    private void kosong(){
        txtKodeBon.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        txtHarga.setText("");
        txtBanyakBarang.setText("");
        txtTotalHarga.setText("");
        txtSemuaTotal.setText("");
        txtCari.setText("");
        txtJenis.setSelectedIndex(0);
        txtNama.requestFocus();
        auto_number();
    }
    
    private void KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KembaliActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FormUtama().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_KembaliActionPerformed

    private void txtKodeBonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeBonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeBonActionPerformed

    private void txtJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJenisActionPerformed
        // TODO add your handling code here:
         int harga = 0;
         int total = 0;
        if(txtJenis.getSelectedItem() == "1 Kg"){
            harga = 7000;
            total = 7000;
        }
        else if(txtJenis.getSelectedItem() == "2 Kg"){
          harga = 14000;
          total = 14000;
        }
        else if(txtJenis.getSelectedItem() == "3 Kg"){
          harga = 21000;
          total = 21000;
        }
        else if(txtJenis.getSelectedItem() == "4 Kg"){
          harga = 28000;
          total = 28000;
          
        }
        else if(txtJenis.getSelectedItem() == "5 Kg"){
          harga = 35000;
          total = 35000;
          
        }
        else if(txtJenis.getSelectedItem() == "6 Kg"){
          harga = 42000;
          total = 42000;
        }
        else if(txtJenis.getSelectedItem() == "7 Kg"){
          harga = 49000;
          total = 49000;
          
        }
        else if(txtJenis.getSelectedItem() == "8 Kg"){
          harga = 56000;
          total = 56000;
          
        }
        else if(txtJenis.getSelectedItem() == "9 Kg"){
          harga = 63000;
          total = 63000;
          
        }
        
        else if(txtJenis.getSelectedItem() == "10 Kg"){
          harga = 70000;
          total = 70000;
          
        }
        else if(txtJenis.getSelectedItem() == "11 Kg"){
          harga = 77000;
          total = 77000;
          
        }
        else if(txtJenis.getSelectedItem() == "12 Kg"){
          harga = 84000;
          total = 84000;
          
        }
        else if(txtJenis.getSelectedItem() == "13 Kg"){
          harga = 91000;
          total = 91000;
          
        }
        else if(txtJenis.getSelectedItem() == "14 Kg"){
          harga = 98000;
          total = 98000;
          
        }
        else if(txtJenis.getSelectedItem() == "15 Kg"){
          harga = 105000;
          total = 105000;
          
        }
        else if(txtJenis.getSelectedItem() == "16 Kg"){
          harga = 112000;
          total = 112000;
          
        }
        else if(txtJenis.getSelectedItem() == "17 Kg"){
          harga = 119000;
          total = 119000;
          
        }
        else if(txtJenis.getSelectedItem() == "18 Kg"){
          harga = 126000;
          total = 126000;
          
        }
        else if(txtJenis.getSelectedItem() == "19 Kg"){
          harga = 133000;
          total = 133000;
          
        }
        else if(txtJenis.getSelectedItem() == "20 Kg"){
          harga = 140000;
          total = 140000;
          
        }
        
        else{
            harga = 0;
        }
        txtHarga.setText(String.valueOf(+harga));
        txtTotalHarga.setText(String.valueOf(+total));
    }//GEN-LAST:event_txtJenisActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
        if (txtCari.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "SILAHKAN MASUKKAN KODE BON!");
        }else{
        Connection connj = null;
        Statement stmtj = null;
            
            String no = txtCari.getText();
            try
            {
              
                connj = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
                stmtj = connj.createStatement();
                String query = "select*from dbcucikiloan where Kodebon like'%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    txtKodeBon.setText(hasil.getString("kodebon"));
                    txtNama.setText(hasil.getString("nama"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    txtJenis.setSelectedItem(hasil.getString("JenisBarang"));
                    txtHarga.setText(hasil.getString("HargaKiloan"));
                    txtTotalHarga.setText(hasil.getString("TotalHarga"));
                    txtBanyakBarang.setText(hasil.getString("BanyakBarang"));
                }
                
            }catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        search_data();    
        }
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        if (txtCari.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "SILAHKAN MASUKKAN KODE BON!");
        }else{
       Connection connj = null;
        Statement stmtj = null;
            
            String no = txtCari.getText();
            try
            {
              
                connj = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
                stmtj = connj.createStatement();
                String query = "select*from dbcucikiloan where Kodebon like'%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    txtKodeBon.setText(hasil.getString("kodebon"));
                    txtNama.setText(hasil.getString("nama"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    txtJenis.setSelectedItem(hasil.getString("JenisBarang"));
                    txtHarga.setText(hasil.getString("HargaKiloan"));
                    txtTotalHarga.setText(hasil.getString("TotalHarga"));
                    txtBanyakBarang.setText(hasil.getString("BanyakBarang"));
                }
                
            }catch(SQLException | HeadlessException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        search_data();
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tabelKiloan.getModel();
        if(tabelKiloan.getSelectedRow() == -1 ){
            if(tabelKiloan.getRowCount() == 0 ){
                IMessage.setText("Tabel masih kosong");
            } else {
                IMessage.setText("Kamu Harus Select Kolomnya");
            }
        } else {
            model.removeRow(tabelKiloan.getSelectedRow());
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tabelKiloanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKiloanMouseClicked
        // TODO add your handling code here:
         int selectedRow = tabelKiloan.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabelKiloan.getModel();
        txtKodeBon.setText(model.getValueAt(selectedRow, 0).toString());
        txtNama.setText(model.getValueAt(selectedRow, 1).toString());
        txtTelepon.setText(model.getValueAt(selectedRow, 3).toString());
        Date start = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse((String)tabelKiloan.getValueAt(selectedRow, 4).toString());
        } catch (ParseException ex) {
            Logger.getLogger(FormCucianKiloan.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtTanggal.setDate(start);
        txtAlamat.setText(model.getValueAt(selectedRow, 2).toString());
        txtJenis.setSelectedItem(model.getValueAt(selectedRow, 5).toString());
        txtHarga.setText(model.getValueAt(selectedRow, 6).toString());
        txtTotalHarga.setText(model.getValueAt(selectedRow, 7).toString());
        txtBanyakBarang.setText(model.getValueAt(selectedRow, 8).toString());             
    }//GEN-LAST:event_tabelKiloanMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Tanggal = dateFormat.format(txtTanggal.getDate());
        int i = tabelKiloan.getSelectedRow();
        DefaultTableModel Model = (DefaultTableModel)tabelKiloan.getModel();
        if (i >= 0)
        {
            Model.setValueAt(txtKodeBon.getText(), i ,0);
            Model.setValueAt(txtNama.getText(), i ,1);
            Model.setValueAt(txtTelepon.getText(), i ,3);
            Model.setValueAt(txtAlamat.getText(), i ,2);
            Model.setValueAt(Tanggal, i, 4);
            Model.setValueAt(txtJenis.getSelectedItem(), i ,5);
            Model.setValueAt(txtHarga.getText(), i ,6);
            Model.setValueAt(txtTotalHarga.getText(),i , 7);
            Model.setValueAt(txtBanyakBarang.getText(), i ,8);
        }
        else {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtBanyakBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBanyakBarangKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtBanyakBarangKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
//            tampilData();
            kosong();
            txtCari.setText("");
            Date start = new Date();
            txtTanggal.setDate(start);
            auto_number();
            for (int i = 0; i < tabelKiloan.getRowCount(); i++)
            for(int j = 0; j < tabelKiloan.getColumnCount(); j++) {
            tabelKiloan.setValueAt("", i, j);
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKembalianActionPerformed

    private void txtTotalHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalHargaKeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtTotalHargaKeyReleased

    private void txtUangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUangActionPerformed

    private void txtUangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUangKeyReleased
        // TODO add your handling code here:
         int t, j , k;
        t = Integer.parseInt(txtTotalHarga.getText());
        j = Integer.parseInt(txtUang.getText());
        k = j-t;
        txtKembalian.setText(""+k);
    }//GEN-LAST:event_txtUangKeyReleased

    public void print() {
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    } 
    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        // TODO add your handling code here:
         if (txtUang.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "SILAHKAN KLIK TOTAL DAN MASUKKAN UANG PELANGGAN!");
        }else{
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
      }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAddActionPerformed
        // TODO add your handling code here:
        IMessage.setText("");
        String Jenis = (String) this.txtJenis.getSelectedItem();
        String Nama = this.txtNama.getText();
        String Alamat = this.txtAlamat.getText();
        String Telepon = this.txtTelepon.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Tanggal = dateFormat.format(txtTanggal.getDate());
        String Harga = this.txtHarga.getText();
        String Banyak = this.txtBanyakBarang.getText();
        String Total = this.txtTotalHarga.getText();
        DefaultTableModel model=(DefaultTableModel) tabelKiloan.getModel();
        if(Nama.equals("") ||
                Alamat.equals("") ||
                Telepon.equals("") ||
                Harga.equals("") ||
                Banyak.equals("") ||
                Total.equals("")){
            JOptionPane.showMessageDialog(null,"DATA BELUM LENGKAP!");
        } else {
            model.addRow(new Object[]{txtKodeBon.getText(),txtNama.getText(),txtAlamat.getText(),txtTelepon.getText(),Tanggal,Jenis,txtHarga.getText(),txtTotalHarga.getText(),txtBanyakBarang.getText()});
            
        }
        
    }//GEN-LAST:event_BtnAddActionPerformed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        // TODO add your handling code here:
        int z = 0;
        for(int i = 0; i < tabelKiloan.getRowCount();i++){
            z+=Integer.parseInt(tabelKiloan.getValueAt(i, 7).toString());
        }
        txtSemuaTotal.setText(z+"");
    }//GEN-LAST:event_btnTotalActionPerformed

    private void txtTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHargaActionPerformed

    private void txtSemuaTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSemuaTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSemuaTotalActionPerformed

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
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCucianKiloan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCucianKiloan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdd;
    private javax.swing.JButton BtnPrint;
    private javax.swing.JButton Kembali;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTotal;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tabelKiloan;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtBanyakBarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JComboBox<String> txtJenis;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtKodeBon;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSemuaTotal;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTelepon;
    private javax.swing.JTextField txtTotalHarga;
    private javax.swing.JTextField txtUang;
    // End of variables declaration//GEN-END:variables
}
