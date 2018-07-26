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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Lazuardi
 */
public class FormCuciSatuan extends javax.swing.JFrame {
    Connection koneksi;
    ResultSet hasil;
    PreparedStatement proses;
    

    /**
     * Creates new form FormCuciSatuan
     */
    public FormCuciSatuan() {
        initComponents();
        auto_number();
        txtNama.requestFocus();
        Date start = new Date();
        txtTanggal.setDate(start);
    }
    
    private void auto_number(){
    try{
        String sql = "SELECT MAX(right(Kodebon,2)) AS no FROM dbcucisatuan";
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                if(hasil.first() == false){
                    txtKodeBon.setText("AS001");
                    
                }else {
                    hasil.last();
                    int auto_id = hasil.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int noLong  = no.length();
                                  for(int a  = 0; a<3-noLong; a++){
                                      no = "0" + no;
                                  }
                                  txtKodeBon.setText("AS" + no);
                }
                
            }
    } catch (Exception e) {
        JOptionPane.showInternalMessageDialog(this, "Error: \n" + e.toString(),"ADA KESALAHAN!",JOptionPane.WARNING_MESSAGE);
    }
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
            ///////////////// Product names Get ///////////
                
            
            ///////////////// Product price Get ///////////
                int Harga = Integer.valueOf(txtHarga.getText());
                int Banyak = Integer.valueOf(txtBanyakBarang.getText());
                int TotalHarga = Integer.valueOf(txtTotalHarga.getText());
                int TotalSemua = Integer.valueOf(txtTotalSemua.getText());
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
            for(int i = 0; i < tabelSatuan.getRowCount();i++){
            g2d.drawString(" Jenis       : "+((String)tabelSatuan.getModel().getValueAt(i,5))+"              ",10,y);y+=yShift;
            g2d.drawString(" Harga       : Rp. "+((String)tabelSatuan.getModel().getValueAt(i,6))+"               ",10,y);y+=yShift;
            g2d.drawString(" Banyak      : "+((String)tabelSatuan.getModel().getValueAt(i,8))+"              ",10,y);y+=yShift;
            g2d.drawString(" Subtotal    : Rp. "+((String)tabelSatuan.getModel().getValueAt(i,7))+"               ",10,y);y+=yShift;
            }
            g2d.drawString("-------------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Total       : Rp. "+TotalSemua+"          ",10,y);y+=yShift;
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

public void print() {
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new FormCuciSatuan.BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    } 

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
            String sql = "SELECT * FROM `dbcucisatuan` WHERE `Kodebon` LIKE '%"+no+"%'";
            koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
            proses = koneksi.prepareStatement(sql);
            hasil = proses.executeQuery(sql);
            while(hasil.next()){
                model.addRow(new Object[]{hasil.getString(2),hasil.getString(3),hasil.getString(4),hasil.getString(5),hasil.getString(6),hasil.getString(8),hasil.getString(7),hasil.getString(9),hasil.getString(10)});
                
                }
            tabelSatuan.setModel(model);
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
        txtTotalSemua.setText("");
        txtCari.setText("");
        txtJenis.setSelectedIndex(0);
        txtNama.requestFocus();
        auto_number();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtKodeBon = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        txtTelepon = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJenis = new javax.swing.JComboBox<>();
        txtBanyakBarang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelSatuan = new javax.swing.JTable();
        btnTotal = new javax.swing.JButton();
        txtTotalSemua = new javax.swing.JTextField();
        txtUang = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cuci Satuan");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\LOGO BJ 100x100.png")); // NOI18N
        jLabel8.setText("CUCI SATUAN");

        jLabel1.setText("Kode Bon");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        txtTanggal.setDateFormatString("yyyy-MM-dd");

        jLabel2.setText("Nama");

        jLabel3.setText("Alamat");

        jLabel4.setText("Telepon");

        jLabel5.setText("Tanggal");

        jLabel6.setText("Jenis Barang");

        jLabel7.setText("Harga");

        txtJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baju Muslim Stelan", "Baju Kaos Biasa", "Bantal/Guling", "Bed Cover Besar", "Bed Cover Sedang", "Blezer", "Blouse & Rok", "Boneka Kecil", "Boneka Besar", "Boneka Jumbo", "Celana Jeans", "Celana Pendek", "Dasi", "Gamis", "Gordyn Tebal", "Gordyn Vitrase", "Gaun Malam/Pesta", "Handuk Besar", "Handuk Kecil", "Jas", "Jas Stelan", "Jaket Biasa", "Jaket Jeans", "Jaket Kulit", "Jok Cover Mobil/Set", "Kain Sarung/Batik", "Kain Songket/Ulos", "Karpet Tebal", "Karpet Tipis", "Kasur Besar", "Kasur Sedang", "Kasur Jumbo", "Koper Besar", "Kemeja", "Kemeja Batik", "Kebaya Panjang Stelan", "Long Dress", "Long Rok", "Mukena Stelan", "Rompi Biasa", "Rompi Kulit", "Sarung Bantal/Guling", "Selimut Tipis", "Selimut Tebal", "Sprei Pendek", "Sprei Renpel/Panjang", "Safari Stelan", "Tas Barang Kecil", "Tas Barang Besar", "Tas Buku", "Tikar", "Topi" }));
        txtJenis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtJenisMouseReleased(evt);
            }
        });
        txtJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJenisActionPerformed(evt);
            }
        });

        txtBanyakBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBanyakBarangKeyReleased(evt);
            }
        });

        jLabel9.setText("Banyak Barang");

        jLabel10.setText("Total Harga");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel11.setText("Cari");

        btnAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon tambah 20x20.png")); // NOI18N
        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon cari 20x20.png")); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
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

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon update 20x20.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon reset 20x20.png")); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnKembali.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\Icon kembali ke menu 20x20.png")); // NOI18N
        btnKembali.setText("Kembali Ke Menu");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        tabelSatuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Bon", "Nama", "Alamat", "Telepon", "Tanggal", "Jenis Barang", "Harga", "Total Harga", "Banyak Barang"
            }
        ));
        tabelSatuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSatuanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelSatuan);

        btnTotal.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon keuntungan 20x20.png")); // NOI18N
        btnTotal.setText("Total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        txtTotalSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalSemuaActionPerformed(evt);
            }
        });

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

        jLabel12.setText("Uang");

        jLabel13.setText("Kembalian");

        btnPrint.setIcon(new javax.swing.ImageIcon("C:\\Users\\Lazuardi\\Documents\\NetBeansProjects\\Skripsi_IlhamLazuardi\\Icon\\icon print 20x20.png")); // NOI18N
        btnPrint.setText("Cetak Struk");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)
                                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addComponent(txtKodeBon)
                                    .addComponent(txtTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBanyakBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnKembali)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUang, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPrint))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(306, 306, 306))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtKodeBon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtBanyakBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(btnAdd))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)
                                            .addComponent(btnCari)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset)
                    .addComponent(btnKembali))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTotal)
                    .addComponent(txtTotalSemua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        Connection koneksi = null;
        Statement stmt = null;
        try {
            
        koneksi = Skripsi_IlhamLazuardi.KoneksiDB.ConnectDb();
        stmt = koneksi.createStatement();
        
       for(int i = 0 ; i <  tabelSatuan.getRowCount(); i++){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String Tanggal = dateFormat.format(txtTanggal.getDate());
                    String Ilham = ("'"+(String)tabelSatuan.getModel().getValueAt(i,0)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,1)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,2)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,3)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,4)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,5)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,6)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,7)+"'"+", ")+("'"+(String)tabelSatuan.getModel().getValueAt(i,8)+"'");

                stmt.executeUpdate("INSERT INTO `dbcucisatuan`(`Kodebon`,`Nama`, `Alamat`,`Telepon`,`Tanggal`,`JenisBarang`,`HargaSatuan`,`TotalHarga`,`BanyakBarang`) VALUES  ("+Ilham+")");
               
        }
            JOptionPane.showMessageDialog(null, "DATA BERHASIL DI TAMBAHKAN KE DATABASE!");
        } catch(SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "DATA GAGAL DITAMBAHKAN");
            auto_number();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
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
        DefaultTableModel model=(DefaultTableModel) tabelSatuan.getModel();
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
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FormUtama().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void txtJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJenisActionPerformed
        // TODO add your handling code here:
        int harga = 0;
         int total = 0;
        if(txtJenis.getSelectedItem() == "Baju Muslim Stelan"){
            harga = 15000;
            total = 15000;
        }
        else if(txtJenis.getSelectedItem() == "Baju Kaos Biasa"){
          harga = 8000;
          total = 8000;
        }
        else if(txtJenis.getSelectedItem() == "Bantal/Guling"){
          harga = 10000;
          total = 10000;
        }
        else if(txtJenis.getSelectedItem() == "Bed Cover Besar"){
          harga = 18000;
          total = 18000;
          
        }
        else if(txtJenis.getSelectedItem() == "Bed Cover Sedang"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Blezer"){
          harga = 10000;
          total = 10000;
        }
        else if(txtJenis.getSelectedItem() == "Blouse & Rok"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Boneka Kecil"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Boneka Besar"){
          harga = 13000;
          total = 13000;
          
        }
        
        else if(txtJenis.getSelectedItem() == "Boneka Jumbo"){
          harga = 35000;
          total = 35000;
          
        }
        else if(txtJenis.getSelectedItem() == "Celana Jeans"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Celana Pendek"){
          harga = 8000;
          total = 8000;
          
        }
        else if(txtJenis.getSelectedItem() == "Dasi"){
          harga = 5000;
          total = 5000;
          
        }
        else if(txtJenis.getSelectedItem() == "Gamis"){
          harga = 25000;
          total = 25000;
          
        }
        else if(txtJenis.getSelectedItem() == "Gordyn Tebal"){
          harga = 50000;
          total = 50000;
          
        }
        else if(txtJenis.getSelectedItem() == "Gordyn Vitrase"){
          harga = 60000;
          total = 60000;
          
        }
        else if(txtJenis.getSelectedItem() == "Gaun Malam/Pesta"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Handuk Besar"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Handuk Kecil"){
          harga = 9000;
          total = 9000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jas"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jas Stelan"){
          harga = 20000;
          total = 20000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jaket Biasa"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jaket Jeans"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jaket Kulit"){
          harga = 14000;
          total = 14000;
          
        }
        else if(txtJenis.getSelectedItem() == "Jok Cover Mobil/Set"){
          harga = 35000;
          total = 35000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kain Sarung/Batik"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kain Songket/Ulos"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Karpet Tebal"){
          harga = 70000;
          total = 70000;
          
        }
        else if(txtJenis.getSelectedItem() == "Karpet Tipis"){
          harga = 50000;
          total = 50000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kasur Besar"){
          harga = 35000;
          total = 35000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kasur Sedang"){
          harga = 25000;
          total = 25000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kasur Jumbo"){
          harga = 50000;
          total = 50000;
          
        }
        else if(txtJenis.getSelectedItem() == "Koper Besar"){
          harga = 30000;
          total = 30000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kemeja"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kemeja Batik"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Kebaya Panjang Stelan"){
          harga = 18000;
          total = 18000;
          
        }
        else if(txtJenis.getSelectedItem() == "Long Dress"){
          harga = 13000;
          total = 13000;
          
        }
        else if(txtJenis.getSelectedItem() == "Long Rok"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Mukena Stelan"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Rompi Biasa"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Rompi Kulit"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Sarung Bantal/Guling"){
          harga = 5000;
          total = 5000;
          
        }
        else if(txtJenis.getSelectedItem() == "Selimut Tipis"){
          harga = 30000;
          total = 30000;
          
        }
        else if(txtJenis.getSelectedItem() == "Selimut Tebal"){
          harga = 45000;
          total = 45000;
          
        }
        else if(txtJenis.getSelectedItem() == "Sprei Pendek"){
          harga = 12000;
          total = 12000;
          
        }
        else if(txtJenis.getSelectedItem() == "Sprei Renpel/Panjang"){
          harga = 14000;
          total = 14000;
          
        }
        else if(txtJenis.getSelectedItem() == "Safari Stelan"){
          harga = 15000;
          total = 15000;
          
        }
        else if(txtJenis.getSelectedItem() == "Tas Barang Kecil"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Tas Barang Besar"){
          harga = 14000;
          total = 14000;
          
        }
        else if(txtJenis.getSelectedItem() == "Tas Buku"){
          harga = 10000;
          total = 10000;
          
        }
        else if(txtJenis.getSelectedItem() == "Tikar"){
          harga = 14000;
          total = 14000;
          
        }
        else if(txtJenis.getSelectedItem() == "Topi"){
          harga = 8000;
          total = 8000;
          
        }
        else{
            harga = 0;
        }
        txtHarga.setText(String.valueOf(+harga));
    }//GEN-LAST:event_txtJenisActionPerformed

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
                String query = "select*from dbcucisatuan where Kodebon like'%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    txtKodeBon.setText(hasil.getString("kodebon"));
                    txtNama.setText(hasil.getString("nama"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    txtJenis.setSelectedItem(hasil.getString("JenisBarang"));
                    txtHarga.setText(hasil.getString("HargaSatuan"));
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
                String query = "select*from dbcucisatuan where Kodebon like'%"+no+"%'";
            
            ResultSet hasil = stmtj.executeQuery(query);
                
                while (hasil.next())
                {
                    //txt berarti dari field ,getstring(ngambil dari database coloumn ) 
                    txtKodeBon.setText(hasil.getString("kodebon"));
                    txtNama.setText(hasil.getString("nama"));
                    txtAlamat.setText(hasil.getString("alamat"));
                    txtTelepon.setText(hasil.getString("telepon"));
                    txtJenis.setSelectedItem(hasil.getString("JenisBarang"));
                    txtHarga.setText(hasil.getString("HargaSatuan"));
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

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tabelSatuan.getModel();
        if(tabelSatuan.getSelectedRow() == -1 ){
            if(tabelSatuan.getRowCount() == 0 ){
                IMessage.setText("Tabel masih kosong");
            } else {
                IMessage.setText("Kamu Harus Select Kolomnya");
            }
        } else {
            model.removeRow(tabelSatuan.getSelectedRow());
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tabelSatuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSatuanMouseClicked
        // TODO add your handling code here:
        int selectedRow = tabelSatuan.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabelSatuan.getModel();
        txtKodeBon.setText(model.getValueAt(selectedRow, 0).toString());
        txtNama.setText(model.getValueAt(selectedRow, 1).toString());
        txtTelepon.setText(model.getValueAt(selectedRow, 3).toString());
        Date start = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse((String)tabelSatuan.getValueAt(selectedRow, 4).toString());
        } catch (ParseException ex) {
            Logger.getLogger(FormCuciSatuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtTanggal.setDate(start);
        txtAlamat.setText(model.getValueAt(selectedRow, 2).toString());
        txtJenis.setSelectedItem(model.getValueAt(selectedRow, 5).toString());
        txtHarga.setText(model.getValueAt(selectedRow, 6).toString());
        txtBanyakBarang.setText(model.getValueAt(selectedRow, 8).toString());
        txtTotalHarga.setText(model.getValueAt(selectedRow, 7).toString());
    }//GEN-LAST:event_tabelSatuanMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Tanggal = dateFormat.format(txtTanggal.getDate());
        int i = tabelSatuan.getSelectedRow();
        DefaultTableModel Model = (DefaultTableModel)tabelSatuan.getModel();
        if (i >= 0)
        {
            Model.setValueAt(txtKodeBon.getText(), i ,0);
            Model.setValueAt(txtNama.getText(), i ,1);
            Model.setValueAt(txtTelepon.getText(), i ,3);
            Model.setValueAt(txtAlamat.getText(), i ,2);
            Model.setValueAt(Tanggal, i, 4);
            Model.setValueAt(txtJenis.getSelectedItem(), i ,5);
            Model.setValueAt(txtHarga.getText(), i ,6);
            Model.setValueAt(txtBanyakBarang.getText(),i , 8);
            Model.setValueAt(txtTotalHarga.getText(), i ,7);
        }
        else {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
            kosong();
            Date start = new Date();
            txtCari.setText("");
            txtTanggal.setDate(start);
            auto_number();
            for (int i = 0; i < tabelSatuan.getRowCount(); i++)
            for(int j = 0; j < tabelSatuan.getColumnCount(); j++) {
            tabelSatuan.setValueAt("", i, j);
      }
            
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtUangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUangActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtUangActionPerformed

    private void txtTotalSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalSemuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalSemuaActionPerformed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        // TODO add your handling code here:
        int z = 0;
        for(int i = 0; i < tabelSatuan.getRowCount();i++){
            z+=Integer.parseInt(tabelSatuan.getValueAt(i, 7).toString());
        }
        txtTotalSemua.setText(z+"");
    }//GEN-LAST:event_btnTotalActionPerformed

    private void txtBanyakBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBanyakBarangKeyReleased
        // TODO add your handling code here:
        int i ,j,k;
        j = Integer.parseInt(txtHarga.getText());
        i = Integer.parseInt(txtBanyakBarang.getText());
        k = (i * j);
        txtTotalHarga.setText(k+"");
    }//GEN-LAST:event_txtBanyakBarangKeyReleased

    private void txtUangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUangKeyReleased
        // TODO add your handling code here:
        int t, j , k;
        t = Integer.parseInt(txtTotalSemua.getText());
        j = Integer.parseInt(txtUang.getText());
        k = j-t;
        txtKembalian.setText(""+k);
    }//GEN-LAST:event_txtUangKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
         if (txtUang.getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "SILAHKAN KLIK TOTAL DAN MASUKKAN UANG PELANGGAN!");
        }else{
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new FormCuciSatuan.BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
      }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtJenisMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJenisMouseReleased
        // TODO add your handling code here:
        txtTotalHarga.setText("");
        txtBanyakBarang.setText("");
    }//GEN-LAST:event_txtJenisMouseReleased

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
            java.util.logging.Logger.getLogger(FormCuciSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCuciSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCuciSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCuciSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCuciSatuan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTotal;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelSatuan;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtBanyakBarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JComboBox<String> txtJenis;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtKodeBon;
    private javax.swing.JTextField txtNama;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTelepon;
    private javax.swing.JTextField txtTotalHarga;
    private javax.swing.JTextField txtTotalSemua;
    private javax.swing.JTextField txtUang;
    // End of variables declaration//GEN-END:variables
}
