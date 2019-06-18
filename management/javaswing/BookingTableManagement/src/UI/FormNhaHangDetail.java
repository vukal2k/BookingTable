/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import bussiness.MenuBUS;
import bussiness.NhaHangBUS;
import commond.ApiHelper;
import commond.ApiUrl;
import commond.FileItemToPhp;
import commond.ImageRenderer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MenuModel;
import model.MenuViewModel;
import model.NhaHangModel;

/**
 *
 * @author trang
 */
public class FormNhaHangDetail extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Propeties">
    private final NhaHangModel _nhaHangViewModel;
    private FileItemToPhp fileItemToPhp;
    @SuppressWarnings("FieldMayBeFinal")
    private List<FileItemToPhp> listOfFileItemFromPhp= new ArrayList<>();
    private final DefaultTableModel defaultTableMenu;
    private final FormMain _formMail;
    
    // </editor-fold>  
    public FormNhaHangDetail(NhaHangModel nhaHangViewModel, FormMain formMain) {
        setUndecorated(true);
        initComponents();
        
        
        defaultTableMenu=(DefaultTableModel)jTableMenu.getModel();
        jTableMenu.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        jLabelNhaHangTen.setText(nhaHangViewModel.getTennhahang());
        
        
        _nhaHangViewModel=nhaHangViewModel;
        _formMail=formMain;
        
        fileItemToPhp=null;
        
        LoadMenu();
    }
    
    private void BackToMain(){
        this.setVisible(false);
        _formMail.setVisible(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Menu">
    private void LoadMenu(){
        listOfFileItemFromPhp.clear();
        
        ArrayList listMenu = MenuBUS.getAll(_nhaHangViewModel.getIdnhahang());
        MenuViewModel menu;
        defaultTableMenu.setRowCount(0);
        
            URL url;
            BufferedImage img;
            @SuppressWarnings("UnusedAssignment")
            ImageIcon objectHinhAnh=null;
            
            for (int i = 0; i < listMenu.size(); i++) {
                try {
                    menu= (MenuViewModel)listMenu.get(i);
                    
                    if(ApiHelper.checkImageExsists(ApiUrl.HostImage+menu.getHinhanh())==false){
                        img = ImageIO.read(getClass().getClassLoader().getResource("resources/no_img.png"));
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(720,
                                1080,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ""));
                    }else{
                        url = new URL(ApiUrl.HostImage+menu.getHinhanh());
                        //url=new URL("http://sleeping.somee.com/Asset/img/roi-vi-vua-chuong-1.jpg");
                        //url = new URL(ApiUrl.HostImage+"/img/20190609155635_1.jpg");
                        img = ImageIO.read(url);
                        objectHinhAnh =new ImageIcon(img.getScaledInstance(720,1080,
                                Image.SCALE_DEFAULT));
                        listOfFileItemFromPhp.add(new FileItemToPhp("", ApiUrl.HostImage+menu.getHinhanh()));
                    }
                    defaultTableMenu.insertRow(i, new Object[]{
                        menu.getIdmenu(),objectHinhAnh 
                    });
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    private void UploadHinhAnhNhaHang(){
        try {
            JFileChooser fileChoose = new JFileChooser();
            fileChoose.showOpenDialog(null);
            
            File menuHinhAnh = fileChoose.getSelectedFile();
            Image img = ImageIO.read(menuHinhAnh);
            Image resizedImage = img.getScaledInstance(jLabelMenuHinhAnhShow.getWidth(), jLabelMenuHinhAnhShow.getHeight(), 0);
            
            jLabelMenuHinhAnhShow.setIcon(new ImageIcon(resizedImage));
            
            fileItemToPhp= new FileItemToPhp(menuHinhAnh.getName(), menuHinhAnh.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ChonMenu(){
        int selectedRowIndex=jTableMenu.getSelectedRow();
        
        //set hinh anh
        fileItemToPhp=null; //set curent file to php í null if update do not change hinh anh
        
        URL url;
        BufferedImage img;
        @SuppressWarnings("UnusedAssignment")
        ImageIcon objectHinhAnh=null;
        FileItemToPhp fileFromPhp = listOfFileItemFromPhp.get(selectedRowIndex);
        try {
            if(fileFromPhp.getFilePath().equals("")==true){
                img = ImageIO.read(getClass().getClassLoader().getResource("resources/no_img.png"));
                objectHinhAnh =new ImageIcon(img.getScaledInstance(jLabelMenuHinhAnhShow.getWidth(), 
                                                                   jLabelMenuHinhAnhShow.getHeight(), 
                                                                    Image.SCALE_DEFAULT));
            }else{
                url = new URL(fileFromPhp.getFilePath());
                img = ImageIO.read(url);
                objectHinhAnh =new ImageIcon(img.getScaledInstance(jLabelMenuHinhAnhShow.getWidth(), 
                                                                    jLabelMenuHinhAnhShow.getHeight(), 
                                                                    Image.SCALE_DEFAULT));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        jLabelMenuHinhAnhShow.setIcon(objectHinhAnh);
    }
    
    private void ThemMenu(){
        MenuModel menuModel = new MenuModel();
        menuModel.setIdnhahang(_nhaHangViewModel.getIdnhahang());
        JOptionPane.showMessageDialog(null, MenuBUS.Them(menuModel,fileItemToPhp));
        LoadMenu();
    }
    
    private void XoaMenu(){
        int idMenu = (int) defaultTableMenu.getValueAt(jTableMenu.getSelectedRow(), 0);
        JOptionPane.showMessageDialog(null, MenuBUS.Xoa(idMenu));
        LoadMenu();
    }
    // </editor-fold>   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabMain = new javax.swing.JTabbedPane();
        jPanelMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabelMenuHinhAnhShow = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMenu = new javax.swing.JTable();
        jPanelDonDatBan = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableKhuVuc = new javax.swing.JTable();
        jTextFieldTimKhuVuc = new javax.swing.JTextField();
        jToggleButtonTimKhuVuc = new javax.swing.JToggleButton();
        jButtonResetKhuVuc = new javax.swing.JButton();
        jTextFieldKhuVucTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonThemKhuVuc = new javax.swing.JButton();
        jButtonSuaKhuVuc = new javax.swing.JButton();
        jButtonXoaKhuVuc = new javax.swing.JButton();
        jComboBoxKhuVuc_ThanhPho = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanelComment = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableTaiKhoan = new javax.swing.JTable();
        jTextFieldTimTaiKhoan = new javax.swing.JTextField();
        jToggleButtonTimTaiKhoan = new javax.swing.JToggleButton();
        jButtonResetTaiKhoan = new javax.swing.JButton();
        jTextFieldTaiKhoanUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButtonThemTaiKhoan = new javax.swing.JButton();
        jButtonSuaTaiKhoan = new javax.swing.JButton();
        jButtonXoaTaiKhoan = new javax.swing.JButton();
        jTextFieldTaiKhoanPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTaiKhoanTen = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTaiKhoanSdt = new javax.swing.JTextField();
        jTextFieldTaiKhoanEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelNhaHangTen = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Chi Tiết Nhà Hàng");
        setBackground(new java.awt.Color(255, 51, 51));
        setForeground(new java.awt.Color(255, 51, 51));

        jLabel1.setText("Hình ảnh menu");

        jButton1.setText("Chọn ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTableMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Hình Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMenu.setRowHeight(1080);
        jTableMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMenu);
        if (jTableMenu.getColumnModel().getColumnCount() > 0) {
            jTableMenu.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMenuLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jLabelMenuHinhAnhShow, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(29, 29, 29))))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelMenuHinhAnhShow, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addGap(0, 164, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        tabMain.addTab("Menu", jPanelMenu);

        jTableKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tên Khu Vực", "Tên Thành Phố"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableKhuVucChonThanhPho(evt);
            }
        });
        jScrollPane2.setViewportView(jTableKhuVuc);

        jToggleButtonTimKhuVuc.setText("Tìm Kiếm");
        jToggleButtonTimKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimKhuVucActionPerformed(evt);
            }
        });

        jButtonResetKhuVuc.setText("Refresh");
        jButtonResetKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetKhuVucActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên Khu Vực");

        jButtonThemKhuVuc.setText("Thêm");
        jButtonThemKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemKhuVucActionPerformed(evt);
            }
        });

        jButtonSuaKhuVuc.setText("Sửa");
        jButtonSuaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaKhuVucActionPerformed(evt);
            }
        });

        jButtonXoaKhuVuc.setText("Xóa");
        jButtonXoaKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaKhuVucActionPerformed(evt);
            }
        });

        jLabel4.setText("Thành Phố");

        javax.swing.GroupLayout jPanelDonDatBanLayout = new javax.swing.GroupLayout(jPanelDonDatBan);
        jPanelDonDatBan.setLayout(jPanelDonDatBanLayout);
        jPanelDonDatBanLayout.setHorizontalGroup(
            jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDonDatBanLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jTextFieldTimKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButtonTimKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetKhuVuc))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jButtonThemKhuVuc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSuaKhuVuc)
                        .addGap(69, 69, 69)
                        .addComponent(jButtonXoaKhuVuc))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldKhuVucTen, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxKhuVuc_ThanhPho, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelDonDatBanLayout.setVerticalGroup(
            jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimKhuVuc)
                    .addComponent(jButtonResetKhuVuc))
                .addGap(104, 104, 104)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldKhuVucTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxKhuVuc_ThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemKhuVuc)
                    .addComponent(jButtonSuaKhuVuc)
                    .addComponent(jButtonXoaKhuVuc))
                .addGap(83, 83, 83))
        );

        tabMain.addTab("Đơn Đặt Bàn", jPanelDonDatBan);

        jTableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Username", "Password", "Tên Khách Hàng", "Sđt", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTaiKhoanChonThanhPho(evt);
            }
        });
        jScrollPane3.setViewportView(jTableTaiKhoan);

        jToggleButtonTimTaiKhoan.setText("Tìm Kiếm");
        jToggleButtonTimTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimTaiKhoanActionPerformed(evt);
            }
        });

        jButtonResetTaiKhoan.setText("Refresh");
        jButtonResetTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetTaiKhoanActionPerformed(evt);
            }
        });

        jLabel5.setText("Username");

        jButtonThemTaiKhoan.setText("Thêm");
        jButtonThemTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemTaiKhoanActionPerformed(evt);
            }
        });

        jButtonSuaTaiKhoan.setText("Sửa");
        jButtonSuaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaTaiKhoanActionPerformed(evt);
            }
        });

        jButtonXoaTaiKhoan.setText("Xóa");
        jButtonXoaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaTaiKhoanActionPerformed(evt);
            }
        });

        jLabel6.setText("Password");

        jLabel7.setText("Tên Khách Hàng");

        jLabel8.setText("Số điện thoại");

        jLabel9.setText("Email");

        javax.swing.GroupLayout jPanelCommentLayout = new javax.swing.GroupLayout(jPanelComment);
        jPanelComment.setLayout(jPanelCommentLayout);
        jPanelCommentLayout.setHorizontalGroup(
            jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommentLayout.createSequentialGroup()
                            .addComponent(jTextFieldTimTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jToggleButtonTimTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonResetTaiKhoan))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldTaiKhoanPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelCommentLayout.createSequentialGroup()
                            .addComponent(jButtonSuaTaiKhoan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonXoaTaiKhoan)
                            .addGap(95, 95, 95)
                            .addComponent(jButtonThemTaiKhoan)))
                    .addComponent(jTextFieldTaiKhoanUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanTen, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTaiKhoanEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCommentLayout.setVerticalGroup(
            jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimTaiKhoan)
                    .addComponent(jButtonResetTaiKhoan))
                .addGap(38, 38, 38)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTaiKhoanEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemTaiKhoan)
                    .addComponent(jButtonSuaTaiKhoan)
                    .addComponent(jButtonXoaTaiKhoan))
                .addGap(0, 184, Short.MAX_VALUE))
            .addGroup(jPanelCommentLayout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        tabMain.addTab("Comment", jPanelComment);

        jPanel1.setBackground(new java.awt.Color(255, 51, 0));

        jLabelNhaHangTen.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabelNhaHangTen.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNhaHangTen.setText("Booking Table");

        jButtonBack.setText("<< Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelNhaHangTen, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBack)
                    .addComponent(jLabelNhaHangTen, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabMain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonXoaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaTaiKhoanActionPerformed

    }//GEN-LAST:event_jButtonXoaTaiKhoanActionPerformed

    private void jButtonSuaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaTaiKhoanActionPerformed

    }//GEN-LAST:event_jButtonSuaTaiKhoanActionPerformed

    private void jButtonThemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemTaiKhoanActionPerformed

    }//GEN-LAST:event_jButtonThemTaiKhoanActionPerformed

    private void jButtonResetTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetTaiKhoanActionPerformed

    }//GEN-LAST:event_jButtonResetTaiKhoanActionPerformed

    private void jToggleButtonTimTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimTaiKhoanActionPerformed

    }//GEN-LAST:event_jToggleButtonTimTaiKhoanActionPerformed

    private void jTableTaiKhoanChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTaiKhoanChonThanhPho

    }//GEN-LAST:event_jTableTaiKhoanChonThanhPho

    private void jButtonXoaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaKhuVucActionPerformed
  
    }//GEN-LAST:event_jButtonXoaKhuVucActionPerformed

    private void jButtonSuaKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaKhuVucActionPerformed
 
    }//GEN-LAST:event_jButtonSuaKhuVucActionPerformed

    private void jButtonThemKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemKhuVucActionPerformed
 
    }//GEN-LAST:event_jButtonThemKhuVucActionPerformed

    private void jButtonResetKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetKhuVucActionPerformed
 
    }//GEN-LAST:event_jButtonResetKhuVucActionPerformed

    private void jToggleButtonTimKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimKhuVucActionPerformed

    }//GEN-LAST:event_jToggleButtonTimKhuVucActionPerformed

    private void jTableKhuVucChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKhuVucChonThanhPho

    }//GEN-LAST:event_jTableKhuVucChonThanhPho

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        UploadHinhAnhNhaHang();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTableMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMenuMouseClicked
        ChonMenu();
    }//GEN-LAST:event_jTableMenuMouseClicked

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        BackToMain();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ThemMenu();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        XoaMenu();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(FormNhaHangDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormNhaHangDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormNhaHangDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormNhaHangDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormNhaHangDetail(new NhaHangModel(), new FormMain()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonResetKhuVuc;
    private javax.swing.JButton jButtonResetTaiKhoan;
    private javax.swing.JButton jButtonSuaKhuVuc;
    private javax.swing.JButton jButtonSuaTaiKhoan;
    private javax.swing.JButton jButtonThemKhuVuc;
    private javax.swing.JButton jButtonThemTaiKhoan;
    private javax.swing.JButton jButtonXoaKhuVuc;
    private javax.swing.JButton jButtonXoaTaiKhoan;
    private javax.swing.JComboBox jComboBoxKhuVuc_ThanhPho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMenuHinhAnhShow;
    private javax.swing.JLabel jLabelNhaHangTen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelComment;
    private javax.swing.JPanel jPanelDonDatBan;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableKhuVuc;
    private javax.swing.JTable jTableMenu;
    private javax.swing.JTable jTableTaiKhoan;
    private javax.swing.JTextField jTextFieldKhuVucTen;
    private javax.swing.JTextField jTextFieldTaiKhoanEmail;
    private javax.swing.JTextField jTextFieldTaiKhoanPassword;
    private javax.swing.JTextField jTextFieldTaiKhoanSdt;
    private javax.swing.JTextField jTextFieldTaiKhoanTen;
    private javax.swing.JTextField jTextFieldTaiKhoanUsername;
    private javax.swing.JTextField jTextFieldTimKhuVuc;
    private javax.swing.JTextField jTextFieldTimTaiKhoan;
    private javax.swing.JToggleButton jToggleButtonTimKhuVuc;
    private javax.swing.JToggleButton jToggleButtonTimTaiKhoan;
    private javax.swing.JTabbedPane tabMain;
    // End of variables declaration//GEN-END:variables
}
