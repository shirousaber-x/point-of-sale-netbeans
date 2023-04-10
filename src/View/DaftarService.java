package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.*;

public class DaftarService extends javax.swing.JFrame {
    DefaultTableModel tblModel;
    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;

    public DaftarService() {
        initComponents();
        
        tblModel = (DefaultTableModel)Tabel.getModel();
        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost/uas", "root", ""))
                
        {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("select * from daftar_service");
            
            while (rs.next())
            {
                String harga = String.format("%s%n", kursIndonesia.format(rs.getInt("Harga")));

                String tblData[] = {rs.getString("kode service"), 
                    rs.getString("nama service"), rs.getString("jenis kendaraan"), 
                    harga};
                DefaultTableModel tblModel = (DefaultTableModel)Tabel.getModel();

                tblModel.addRow(tblData);
            }
        } 
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        judulLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jenisKendaraanLabel = new javax.swing.JLabel();
        hargaLabel = new javax.swing.JLabel();
        kodeServiceText = new javax.swing.JTextField();
        hargaText = new javax.swing.JTextField();
        namaServiceText = new javax.swing.JTextField();
        jenisKendaraanBox = new javax.swing.JComboBox<>();
        kodeServiceLabel = new javax.swing.JLabel();
        namaServiceLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        kembaliButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deselectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Daftar Service");

        judulLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        judulLabel.setForeground(new java.awt.Color(102, 153, 255));
        judulLabel.setText("Daftar Service");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.white, java.awt.Color.gray));

        jenisKendaraanLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jenisKendaraanLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jenisKendaraanLabel.setText("Jenis Kendaraan :");

        hargaLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hargaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        hargaLabel.setText("Harga :");

        kodeServiceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeServiceTextActionPerformed(evt);
            }
        });

        jenisKendaraanBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor", "Mobil" }));

        kodeServiceLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kodeServiceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        kodeServiceLabel.setText("Kode Service :");

        namaServiceLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        namaServiceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        namaServiceLabel.setText("Nama Service :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(kodeServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kodeServiceText, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jenisKendaraanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jenisKendaraanBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(hargaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(hargaText, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(namaServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(namaServiceText, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kodeServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kodeServiceText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaServiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaServiceText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jenisKendaraanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenisKendaraanBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hargaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hargaText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Service", "Nama Service", "Jenis Kendaraan", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabel);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        kembaliButton.setText("Kembali");
        kembaliButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deselectButton.setText("Deselect");
        deselectButton.setEnabled(false);
        deselectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(judulLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deselectButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(kembaliButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(judulLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addButton)
                            .addComponent(deleteButton)
                            .addComponent(editButton)
                            .addComponent(deselectButton))
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kembaliButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kodeServiceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeServiceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeServiceTextActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // Cek jika ada TextField yang kosong
        if (isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Harap Isi Semua Data");
            return;
        }

        // Masukkan ke database
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost/uas", "root", ""))
                
        {
            Statement stat = con.createStatement();
            stat.executeUpdate("INSERT INTO `daftar_service` (`Kode Service`, "
                    + "`Nama Service`, `Jenis Kendaraan`, `Harga`) VALUES "
                    + "('" + kodeServiceText.getText() + "', "
                    + "'" + namaServiceText.getText() + "', "
                    + "'" + (String)jenisKendaraanBox.getSelectedItem() + "', "
                    + "'" + hargaText.getText() + "');");
            
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Kode Service \"" 
                    + kodeServiceText.getText() + "\" Sudah Ada");
            System.out.println(e);
            return;
        }
        
        // Masukkan ke tabel
        String harga = String.format("%s%n", kursIndonesia
                .format(Integer.parseInt(hargaText.getText())));
        String data[] = {kodeServiceText.getText(), namaServiceText.getText(), 
            (String)jenisKendaraanBox.getSelectedItem(), harga};
        tblModel.addRow(data);
        JOptionPane.showMessageDialog(null, "Berhasil Menambah Data");
        
        reset();
    }//GEN-LAST:event_addButtonActionPerformed

    private void kembaliButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliButtonActionPerformed
        dispose();
        new MainMenu().setVisible(true);
    }//GEN-LAST:event_kembaliButtonActionPerformed

    private void TabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMouseClicked
        int i = Tabel.getSelectedRow();
        //System.out.println(i);
        if (i == -1)    // -1 untuk deselect row
        {
            reset();
        }
        else
        {
            deselectButton.setEnabled(true);
            kodeServiceText.setText(tblModel.getValueAt(i, 0).toString());
            namaServiceText.setText(tblModel.getValueAt(i, 1).toString());
            jenisKendaraanBox.setSelectedItem(tblModel.getValueAt(i, 2).toString());
            
            // Dapatkan harga dari database karena yang di table sudah dalam bentuk Rp.
            try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost/uas", "root", ""))
                
            {
                Statement stat = con.createStatement();
                ResultSet rs = stat.executeQuery("select * from daftar_service "
                        + "where `kode service` = '" + tblModel.getValueAt(i, 0) + "'");

                if (rs.next())
                {
                    hargaText.setText(Integer.toString(rs.getInt("harga")));
                }
            } 
            catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_TabelMouseClicked

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // Jika ada TextField yang kosong, beritahu pengguna
        if (isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Harap Isi Semua Data");
            return;
        }
        
        int i = Tabel.getSelectedRow();
        if (i == -1)
        {
            JOptionPane.showMessageDialog(null, "Harap Pilih Salah Satu Baris!");
        }
        else
        {
            try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost/uas", "root", ""))
                
            {
                Statement stat = con.createStatement();
                stat.executeUpdate("UPDATE `daftar_service` SET "
                        + "`Kode Service` = '" + kodeServiceText.getText() + "', "
                        + "`Nama Service` = '" + namaServiceText.getText() + "', "
                        + "`Jenis Kendaraan` = '" + (String)jenisKendaraanBox.getSelectedItem() + "', "
                        + "`Harga` = '" + hargaText.getText() + "' "
                        + "WHERE `daftar_service`.`Kode Service` = '" 
                        + tblModel.getValueAt(i, 0) + "';");
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
                
                // Sekarang update juga data pada tabel
                tblModel.setValueAt(kodeServiceText.getText(), i, 0);
                tblModel.setValueAt(namaServiceText.getText(), i, 1);
                tblModel.setValueAt((String)jenisKendaraanBox.getSelectedItem(), i, 2);
                tblModel.setValueAt(String.format("%s%n", 
                        kursIndonesia.format(Integer.parseInt(hargaText.getText()))), i, 3);
                reset();
            } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Kode Service \"" 
                        + kodeServiceText.getText() + "\" Sudah Ada");
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void deselectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectButtonActionPerformed
        reset();
    }//GEN-LAST:event_deselectButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int i = Tabel.getSelectedRow();
        if (i == -1)    // Tidak ada baris yang dipilih
        {
            JOptionPane.showMessageDialog(null, "Harap Pilih Salah Satu Baris");
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(null, 
                "Anda Yakin Ingin Menghapus Data?", "Delete", JOptionPane.YES_NO_OPTION);
        if (choice == 0)    // Hapus data
        {
            String kodeService = (String)tblModel.getValueAt(i, 0);
            
            try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost/uas", "root", ""))
                
            {
                // Hapus di database
                Statement stat = con.createStatement();
                stat.executeUpdate("DELETE FROM `daftar_service` WHERE "
                        + "`daftar_service`.`Kode Service` = '" + kodeService + "'");
                
                // Hapus di JTable
                tblModel.removeRow(i);
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                reset();
            } 
            catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    // Cek jika TextField ada yang kosong
    private boolean isEmpty()
    {
        if (kodeServiceText.getText().equals("") ||
                namaServiceText.getText().equals("") ||
                hargaText.getText().equals(""))
        {
            return true;
        }
        return false;
    }
    
    // clear row selection dan kosongkan textfield
    private void reset()
    {
        kodeServiceText.setText("");
        namaServiceText.setText("");
        jenisKendaraanBox.setSelectedIndex(0);
        hargaText.setText("");
        Tabel.getSelectionModel().clearSelection();
        deselectButton.setEnabled(false);
    }
    
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
            java.util.logging.Logger.getLogger(DaftarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DaftarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DaftarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DaftarService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DaftarService().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabel;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton deselectButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel hargaLabel;
    private javax.swing.JTextField hargaText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jenisKendaraanBox;
    private javax.swing.JLabel jenisKendaraanLabel;
    private javax.swing.JLabel judulLabel;
    private javax.swing.JButton kembaliButton;
    private javax.swing.JLabel kodeServiceLabel;
    private javax.swing.JTextField kodeServiceText;
    private javax.swing.JLabel namaServiceLabel;
    private javax.swing.JTextField namaServiceText;
    // End of variables declaration//GEN-END:variables
}
