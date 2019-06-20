/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import bussiness.DanhGiaBUS;
import bussiness.DatBanBUS;
import bussiness.KhuVucBUS;
import bussiness.MenuBUS;
import bussiness.TaiKhoanBUS;
import bussiness.ThanhPhoBUS;
import commond.ApiHelper;
import commond.ApiUrl;
import commond.ComboboxItem;
import commond.ComboboxItemRender;
import commond.ConvertHelper;
import commond.FileItemToPhp;
import commond.ImageRenderer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.DanhGiaViewModel;
import model.DatBanModel;
import model.DatBanViewModel;
import model.KhuVucModel;
import model.KhuVucViewModel;
import model.MenuModel;
import model.MenuViewModel;
import model.NhaHangModel;
import model.TaiKhoanModel;
import model.ThanhPhoModel;

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
    private final DefaultTableModel defaultTableDatBan;
    private final DefaultTableModel defaultTableDanhGia;
    private final FormMain _formMail;
    
    // </editor-fold>  
    public FormNhaHangDetail(NhaHangModel nhaHangViewModel, FormMain formMain) {
        setUndecorated(true);
        initComponents();
        
        
        defaultTableMenu=(DefaultTableModel)jTableMenu.getModel();
        defaultTableDatBan=(DefaultTableModel)jTableDatBan.getModel();
        defaultTableDanhGia=(DefaultTableModel)jTableDanhGia.getModel();
        jTableMenu.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
        jLabelNhaHangTen.setText(nhaHangViewModel.getTennhahang());
        jComboBoxDatBan_KhachHang.setRenderer( new ComboboxItemRender() );
        
        
        _nhaHangViewModel=nhaHangViewModel;
        _formMail=formMain;
        
        fileItemToPhp=null;
        
        LoadMenu();
        LoadDatBan();
        LoadDanhGia();
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
    
    // <editor-fold defaultstate="collapsed" desc="Don dat ban">
    private void LoadDatBan(){
        ArrayList listDatBan = DatBanBUS.getAll(_nhaHangViewModel.getIdnhahang());
        DatBanViewModel datban;
        defaultTableDatBan.setRowCount(0);
        
        for (int i = 0; i < listDatBan.size(); i++) {
            try {
                datban= (DatBanViewModel)listDatBan.get(i);
                
                defaultTableDatBan.insertRow(i, new Object[]{
                    datban.getIddatban(),datban.getUsername(),ConvertHelper.DateToVNString(datban.getNgayden()),
                    datban.getGioden(),datban.getSoluongnguoilon(),
                    datban.getSoluongtreem(),datban.getGhichu(),
                    datban.getTongtien()
                });
            } catch (ParseException ex) {
                Logger.getLogger(FormNhaHangDetail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        LoadComboboxDatBan_KhachHang();
    }
    
    private void TimDatBan(){
        ArrayList listDatBan = DatBanBUS.timKiem(_nhaHangViewModel.getIdnhahang(),jTextFieldTimDonDatBan.getText());
        DatBanViewModel datban;
        defaultTableDatBan.setRowCount(0);
        
        for (int i = 0; i < listDatBan.size(); i++) {
            try {
                datban= (DatBanViewModel)listDatBan.get(i);
                
                defaultTableDatBan.insertRow(i, new Object[]{
                    datban.getIddatban(),datban.getUsername(),ConvertHelper.DateToVNString(datban.getNgayden()),
                    datban.getGioden(),datban.getSoluongnguoilon(),
                    datban.getSoluongtreem(),datban.getGhichu(),
                    datban.getTongtien()
                });
            } catch (ParseException ex) {
                Logger.getLogger(FormNhaHangDetail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void ChonDatBan(){
        int selectedRowIndex = jTableDatBan.getSelectedRow();
        
        //set ngay den
        String[] selectedNgayDen = defaultTableDatBan.getValueAt(selectedRowIndex, 2).toString().split("/");
        jSpinnerDatBanNgayDen_Ngay.setValue(Integer.parseInt(selectedNgayDen[0]));
        jSpinnerDatBanNgayDen_Thang.setValue(Integer.parseInt(selectedNgayDen[1]));
        jSpinnerDatBanNgayDen_Nam.setValue(Integer.parseInt(selectedNgayDen[2]));
        
        //set gio den
        String[] selectedGioDen = defaultTableDatBan.getValueAt(selectedRowIndex, 3).toString().split(":");
        jSpinnerDatBanGioDen_Gio.setValue(Integer.parseInt(selectedGioDen[0]));
        jSpinnerDatBanGioDen_Phut.setValue(Integer.parseInt(selectedGioDen[1]));
        
        jSpinnerDatBan_SLNguoiLon.setValue(Integer.parseInt(defaultTableDatBan.getValueAt(selectedRowIndex, 4).toString()));
        jSpinnerDatBan_SLTreEm.setValue(Integer.parseInt(defaultTableDatBan.getValueAt(selectedRowIndex, 5).toString()));
        
        jTextAreaDatBan_GhiChu.setText((String) defaultTableDatBan.getValueAt(selectedRowIndex, 6));
        
        jLabelDonDatBan_TongTien.setText((String) defaultTableDatBan.getValueAt(selectedRowIndex, 7));
        
        String selectedUsername = defaultTableDatBan.getValueAt(selectedRowIndex, 1).toString();
        ComboboxItem item;
        for (int i = 0; i < jComboBoxDatBan_KhachHang.getItemCount(); i++)
        {
            item = (ComboboxItem)jComboBoxDatBan_KhachHang.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(selectedUsername))
            {
                jComboBoxDatBan_KhachHang.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void ThemDatBan(){
        DatBanModel datban = new DatBanModel();
        datban.setGhichu(jTextAreaDatBan_GhiChu.getText());
        
        String gioDen = jSpinnerDatBanGioDen_Gio.getValue()+":"+jSpinnerDatBanGioDen_Phut;
        datban.setGioden(gioDen);
        
        String ngayDen = jSpinnerDatBanNgayDen_Nam.getValue()+"-"+jSpinnerDatBanNgayDen_Thang+"-"+jSpinnerDatBanNgayDen_Ngay.getValue();
        datban.setNgayden(ngayDen);
        
        datban.setIdnhahang(_nhaHangViewModel.getIdnhahang());
        
        ComboboxItem selectedUsernameKhach = (ComboboxItem)jComboBoxDatBan_KhachHang.getSelectedItem();
        datban.setIdtaikhoan(selectedUsernameKhach.getKey());
        
        datban.setSoluongnguoilon((int) jSpinnerDatBan_SLNguoiLon.getValue());
        
        datban.setSoluongtreem((int) jSpinnerDatBan_SLTreEm.getValue());
        
        datban.setTongtien(0+"");
        
        JOptionPane.showMessageDialog(null, DatBanBUS.Them(datban));
        LoadDatBan();
    }
    
    private void SuaDatBan(){
        try {
            DatBanModel datban = new DatBanModel();
            datban.setGhichu(jTextAreaDatBan_GhiChu.getText());

            String gioDen = jSpinnerDatBanGioDen_Gio.getValue()+":"+jSpinnerDatBanGioDen_Phut;
            datban.setGioden(gioDen);

            String ngayDen = jSpinnerDatBanNgayDen_Nam.getValue()+"-"+jSpinnerDatBanNgayDen_Thang+"-"+jSpinnerDatBanNgayDen_Ngay.getValue();
            datban.setNgayden(ngayDen);

            datban.setIdnhahang(_nhaHangViewModel.getIdnhahang());

            ComboboxItem selectedUsernameKhach = (ComboboxItem)jComboBoxDatBan_KhachHang.getSelectedItem();
            datban.setIdtaikhoan(selectedUsernameKhach.getKey());

            datban.setSoluongnguoilon((int) jSpinnerDatBan_SLNguoiLon.getValue());

            datban.setSoluongtreem((int) jSpinnerDatBan_SLTreEm.getValue());

            datban.setTongtien(jLabelDonDatBan_TongTien.getText());
            
            datban.setIddatban((int) defaultTableDatBan.getValueAt(jTableDatBan.getSelectedRow(), 0));

            JOptionPane.showMessageDialog(null, DatBanBUS.Sua(datban));
            
            LoadDatBan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn đơn đặt bàn cần sửa");
        }
    }
    
    private void XoaDatBan(){
        try {
            int idDatBan = (int) defaultTableDatBan.getValueAt(jTableDatBan.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, DatBanBUS.Xoa(idDatBan));
            
            LoadDatBan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn khu vực cần xóa");
        }
    }
    
    private void LoadComboboxDatBan_KhachHang(){
        ArrayList listKhachHangItem = TaiKhoanBUS.getAll();
        TaiKhoanModel taiKhoan;
      
        jComboBoxDatBan_KhachHang.removeAllItems();
        ComboboxItem fistComboboxItem = new ComboboxItem(0, "Chọn khách hàng");
        jComboBoxDatBan_KhachHang.addItem(fistComboboxItem);
          for (int i = 0; i < listKhachHangItem.size(); i++) {
              taiKhoan = (TaiKhoanModel)listKhachHangItem.get(i);

              jComboBoxDatBan_KhachHang.addItem(new ComboboxItem(taiKhoan.getIdtaikhoan(), taiKhoan.getUsername()));
          }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Danh gia">
    private void LoadDanhGia(){
        ArrayList listDanhGia = DanhGiaBUS.getAll(_nhaHangViewModel.getIdnhahang());
        DanhGiaViewModel danhGia;
        defaultTableDanhGia.setRowCount(0);
        
        for (int i = 0; i < listDanhGia.size(); i++) {
            danhGia= (DanhGiaViewModel)listDanhGia.get(i);
            defaultTableDanhGia.insertRow(i, new Object[]{
                danhGia.getIddanhgia(),danhGia.getUsername(),danhGia.getNoidung(),danhGia.getDanhgiasao()
            });
        }
        
        LoadJLabelSaoTrungBinh(listDanhGia);
    }
    
    private void TimDanhGia(){
        ArrayList listDanhGia = DanhGiaBUS.timKiem(_nhaHangViewModel.getIdnhahang(),jTextFieldTimDanhGia.getText());
        DanhGiaViewModel danhGia;
        defaultTableDanhGia.setRowCount(0);
        
        for (int i = 0; i < listDanhGia.size(); i++) {
            danhGia= (DanhGiaViewModel)listDanhGia.get(i);
            defaultTableDanhGia.insertRow(i, new Object[]{
                danhGia.getIddanhgia(),danhGia.getUsername(),danhGia.getNoidung(),danhGia.getDanhgiasao()
            });
        }
        LoadJLabelSaoTrungBinh(listDanhGia);
    }
    
    
    
    private void XoaDanhGia(){
        try {
            int idDanhGia = (int) defaultTableDanhGia.getValueAt(jTableDanhGia.getSelectedRow(), 0);
        
            JOptionPane.showMessageDialog(null, DanhGiaBUS.Xoa(idDanhGia));
            
            LoadDanhGia();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn đánh giá cần xóa");
        }
    }
    
    private void LoadJLabelSaoTrungBinh(List<DanhGiaViewModel>listDanhGia){
        float tong=0;
        for (int i = 0; i < listDanhGia.size(); i++) {
            tong+=listDanhGia.get(i).getDanhgiasao();
        }
        
        if(tong>0){
            Float trungBinh = (Float)(tong/listDanhGia.size());
            jLabelDanhGia_DoHaiLong.setText(trungBinh.toString());
        }else{
            jLabelDanhGia_DoHaiLong.setText(0+"");
        }
        
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
        jTableDatBan = new javax.swing.JTable();
        jTextFieldTimDonDatBan = new javax.swing.JTextField();
        jToggleButtonTimDonDatBan = new javax.swing.JToggleButton();
        jButtonResetDonDatBan = new javax.swing.JButton();
        jButtonThemDatBan = new javax.swing.JButton();
        jButtonSuaDatBan = new javax.swing.JButton();
        jButtonXoaDatBan = new javax.swing.JButton();
        jComboBoxDatBan_KhachHang = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerDatBanNgayDen_Ngay = new javax.swing.JSpinner();
        jSpinnerDatBanNgayDen_Thang = new javax.swing.JSpinner();
        jSpinnerDatBanNgayDen_Nam = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSpinnerDatBanGioDen_Gio = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jSpinnerDatBanGioDen_Phut = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jSpinnerDatBan_SLNguoiLon = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jSpinnerDatBan_SLTreEm = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jLabelDonDatBan_TongTien = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaDatBan_GhiChu = new javax.swing.JTextArea();
        jPanelComment = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDanhGia = new javax.swing.JTable();
        jTextFieldTimDanhGia = new javax.swing.JTextField();
        jToggleButtonTimTaiKhoan = new javax.swing.JToggleButton();
        jButtonResetDanhGia = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabelDanhGia_DoHaiLong = new javax.swing.JLabel();
        jButtonXoaDanhGia = new javax.swing.JButton();
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
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
                        .addGap(0, 199, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        tabMain.addTab("Menu", jPanelMenu);

        jTableDatBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Username Khách Hàng", "Ngày Đến", "Giờ Đến", "SL Người Lớn", "SL Trẻ Em", "Ghi Chú", "Tổng Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDatBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDatBanChonThanhPho(evt);
            }
        });
        jScrollPane2.setViewportView(jTableDatBan);
        if (jTableDatBan.getColumnModel().getColumnCount() > 0) {
            jTableDatBan.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jToggleButtonTimDonDatBan.setText("Tìm Kiếm");
        jToggleButtonTimDonDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimDonDatBanActionPerformed(evt);
            }
        });

        jButtonResetDonDatBan.setText("Refresh");
        jButtonResetDonDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetDonDatBanActionPerformed(evt);
            }
        });

        jButtonThemDatBan.setText("Thêm");
        jButtonThemDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemDatBanActionPerformed(evt);
            }
        });

        jButtonSuaDatBan.setText("Sửa");
        jButtonSuaDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaDatBanActionPerformed(evt);
            }
        });

        jButtonXoaDatBan.setText("Xóa");
        jButtonXoaDatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaDatBanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Username Khách Hàng");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Ngày Đến");

        jSpinnerDatBanNgayDen_Ngay.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jSpinnerDatBanNgayDen_Thang.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        jSpinnerDatBanNgayDen_Nam.setModel(new javax.swing.SpinnerNumberModel(2019, 1996, 9999, 1));

        jLabel11.setText("/");

        jLabel12.setText("/");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Giờ Đến");

        jSpinnerDatBanGioDen_Gio.setModel(new javax.swing.SpinnerNumberModel(0, 0, 24, 1));

        jLabel14.setText(":");

        jSpinnerDatBanGioDen_Phut.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Số Lượng Người Lớn");

        jSpinnerDatBan_SLNguoiLon.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Số Lượng Trẻ Em");

        jSpinnerDatBan_SLTreEm.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Tổng Tiền");

        jLabelDonDatBan_TongTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelDonDatBan_TongTien.setForeground(new java.awt.Color(255, 0, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Ghi Chú");

        jTextAreaDatBan_GhiChu.setColumns(20);
        jTextAreaDatBan_GhiChu.setRows(5);
        jScrollPane4.setViewportView(jTextAreaDatBan_GhiChu);

        javax.swing.GroupLayout jPanelDonDatBanLayout = new javax.swing.GroupLayout(jPanelDonDatBan);
        jPanelDonDatBan.setLayout(jPanelDonDatBanLayout);
        jPanelDonDatBanLayout.setHorizontalGroup(
            jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDonDatBanLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jTextFieldTimDonDatBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButtonTimDonDatBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonResetDonDatBan))
                    .addComponent(jComboBoxDatBan_KhachHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jButtonThemDatBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(jButtonSuaDatBan)
                        .addGap(130, 130, 130)
                        .addComponent(jButtonXoaDatBan))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(85, 85, 85)
                                .addComponent(jSpinnerDatBanGioDen_Gio, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinnerDatBanNgayDen_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinnerDatBanNgayDen_Thang, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinnerDatBanGioDen_Phut)
                            .addComponent(jSpinnerDatBanNgayDen_Nam, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(138, 138, 138)
                        .addComponent(jSpinnerDatBan_SLNguoiLon))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(154, 154, 154)
                        .addComponent(jSpinnerDatBan_SLTreEm))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
                        .addComponent(jLabelDonDatBan_TongTien))
                    .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4)))
                .addContainerGap())
        );
        jPanelDonDatBanLayout.setVerticalGroup(
            jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanelDonDatBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimDonDatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimDonDatBan)
                    .addComponent(jButtonResetDonDatBan))
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxDatBan_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinnerDatBanNgayDen_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jSpinnerDatBanNgayDen_Thang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinnerDatBanNgayDen_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jSpinnerDatBanGioDen_Gio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerDatBanGioDen_Phut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jSpinnerDatBan_SLNguoiLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jSpinnerDatBan_SLTreEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabelDonDatBan_TongTien))
                .addGap(68, 68, 68)
                .addGroup(jPanelDonDatBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonThemDatBan)
                    .addComponent(jButtonSuaDatBan)
                    .addComponent(jButtonXoaDatBan))
                .addGap(83, 83, 83))
        );

        tabMain.addTab("Đơn Đặt Bàn", jPanelDonDatBan);

        jTableDanhGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Username", "Nội Dung", "Độ Hài Lòng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDanhGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDanhGiaChonThanhPho(evt);
            }
        });
        jScrollPane3.setViewportView(jTableDanhGia);
        if (jTableDanhGia.getColumnModel().getColumnCount() > 0) {
            jTableDanhGia.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableDanhGia.getColumnModel().getColumn(2).setMinWidth(250);
        }

        jToggleButtonTimTaiKhoan.setText("Tìm Kiếm");
        jToggleButtonTimTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTimTaiKhoanActionPerformed(evt);
            }
        });

        jButtonResetDanhGia.setText("Refresh");
        jButtonResetDanhGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetDanhGiaActionPerformed(evt);
            }
        });

        jLabel3.setText("Độ Hài Lòng Trung Bình");

        jLabelDanhGia_DoHaiLong.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabelDanhGia_DoHaiLong.setForeground(new java.awt.Color(255, 204, 0));
        jLabelDanhGia_DoHaiLong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDanhGia_DoHaiLong.setText("0");

        jButtonXoaDanhGia.setText("Xóa Đánh Giá Đang Chọn");
        jButtonXoaDanhGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaDanhGiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCommentLayout = new javax.swing.GroupLayout(jPanelComment);
        jPanelComment.setLayout(jPanelCommentLayout);
        jPanelCommentLayout.setHorizontalGroup(
            jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommentLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCommentLayout.createSequentialGroup()
                                .addComponent(jTextFieldTimDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToggleButtonTimTaiKhoan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonResetDanhGia))
                            .addComponent(jLabel3)
                            .addComponent(jLabelDanhGia_DoHaiLong, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCommentLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonXoaDanhGia)
                        .addContainerGap())))
        );
        jPanelCommentLayout.setVerticalGroup(
            jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimDanhGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonTimTaiKhoan)
                    .addComponent(jButtonResetDanhGia))
                .addGap(107, 107, 107)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabelDanhGia_DoHaiLong, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonXoaDanhGia)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelCommentLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
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

    private void jButtonResetDanhGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetDanhGiaActionPerformed
        LoadDanhGia();
    }//GEN-LAST:event_jButtonResetDanhGiaActionPerformed

    private void jToggleButtonTimTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimTaiKhoanActionPerformed
        TimDanhGia();
    }//GEN-LAST:event_jToggleButtonTimTaiKhoanActionPerformed

    private void jTableDanhGiaChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDanhGiaChonThanhPho

    }//GEN-LAST:event_jTableDanhGiaChonThanhPho

    private void jButtonXoaDatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaDatBanActionPerformed
        XoaDatBan();
    }//GEN-LAST:event_jButtonXoaDatBanActionPerformed

    private void jButtonSuaDatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaDatBanActionPerformed
        SuaDatBan();
    }//GEN-LAST:event_jButtonSuaDatBanActionPerformed

    private void jButtonThemDatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemDatBanActionPerformed
        ThemDatBan();
    }//GEN-LAST:event_jButtonThemDatBanActionPerformed

    private void jButtonResetDonDatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetDonDatBanActionPerformed
        LoadDatBan();
    }//GEN-LAST:event_jButtonResetDonDatBanActionPerformed

    private void jToggleButtonTimDonDatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTimDonDatBanActionPerformed
        TimDatBan();
    }//GEN-LAST:event_jToggleButtonTimDonDatBanActionPerformed

    private void jTableDatBanChonThanhPho(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDatBanChonThanhPho
        ChonDatBan();
    }//GEN-LAST:event_jTableDatBanChonThanhPho

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

    private void jButtonXoaDanhGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaDanhGiaActionPerformed
        XoaDanhGia();
    }//GEN-LAST:event_jButtonXoaDanhGiaActionPerformed

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
    private javax.swing.JButton jButtonResetDanhGia;
    private javax.swing.JButton jButtonResetDonDatBan;
    private javax.swing.JButton jButtonSuaDatBan;
    private javax.swing.JButton jButtonThemDatBan;
    private javax.swing.JButton jButtonXoaDanhGia;
    private javax.swing.JButton jButtonXoaDatBan;
    private javax.swing.JComboBox jComboBoxDatBan_KhachHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelDanhGia_DoHaiLong;
    private javax.swing.JLabel jLabelDonDatBan_TongTien;
    private javax.swing.JLabel jLabelMenuHinhAnhShow;
    private javax.swing.JLabel jLabelNhaHangTen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelComment;
    private javax.swing.JPanel jPanelDonDatBan;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinnerDatBanGioDen_Gio;
    private javax.swing.JSpinner jSpinnerDatBanGioDen_Phut;
    private javax.swing.JSpinner jSpinnerDatBanNgayDen_Nam;
    private javax.swing.JSpinner jSpinnerDatBanNgayDen_Ngay;
    private javax.swing.JSpinner jSpinnerDatBanNgayDen_Thang;
    private javax.swing.JSpinner jSpinnerDatBan_SLNguoiLon;
    private javax.swing.JSpinner jSpinnerDatBan_SLTreEm;
    private javax.swing.JTable jTableDanhGia;
    private javax.swing.JTable jTableDatBan;
    private javax.swing.JTable jTableMenu;
    private javax.swing.JTextArea jTextAreaDatBan_GhiChu;
    private javax.swing.JTextField jTextFieldTimDanhGia;
    private javax.swing.JTextField jTextFieldTimDonDatBan;
    private javax.swing.JToggleButton jToggleButtonTimDonDatBan;
    private javax.swing.JToggleButton jToggleButtonTimTaiKhoan;
    private javax.swing.JTabbedPane tabMain;
    // End of variables declaration//GEN-END:variables
}
