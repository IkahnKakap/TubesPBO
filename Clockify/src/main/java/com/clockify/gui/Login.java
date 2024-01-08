package com.clockify.gui;

import com.clockify.clockify.Database;
import com.clockify.model.Administrasi;
import com.clockify.model.Karyawan;
import com.clockify.model.Manager;
import com.clockify.model.PekerjaBiasa;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import com.clockify.model.Departemen;
import com.clockify.model.Teknisi;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author khusyasy
 */
public class Login extends javax.swing.JFrame {
    Database db = new Database();

    /**
     * Creates new form Main
     */
    public Login() {
        initComponents();
        // for testing
        TFemail.setText("admin@admin.com");
        TFpassword.setText("password");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TFemail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        BLogin = new javax.swing.JButton();
        TFpassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Email");

        jLabel3.setText("Password");

        TFemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFemailActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Clockify App");

        BLogin.setText("Login");
        BLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLoginActionPerformed(evt);
            }
        });

        TFpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFpasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BLogin)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TFemail)
                            .addComponent(TFpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TFemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TFpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BLogin)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TFemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFemailActionPerformed
        BLoginActionPerformed(evt);
    }//GEN-LAST:event_TFemailActionPerformed

    private void BLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLoginActionPerformed
        String email = TFemail.getText();
        String password = TFpassword.getText();
        
        if (email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank()) {
            return;
        }

        try {
            ResultSet rs = db.executeQuery("SELECT * FROM karyawan WHERE email=?", email);
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Email tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String storedPassword = rs.getString("password");
            if (!BCrypt.checkpw(password, storedPassword)) {
                JOptionPane.showMessageDialog(this, "Password salah", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String posisi = rs.getString("posisi");

            ResultSet rsD = db.executeQuery("SELECT d.id, d.nama FROM karyawan k JOIN departemen d ON d.id = k.id_departemen WHERE k.id = ?", rs.getInt("id"));
            if (!rsD.next()) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Karyawan k;
            Departemen d = new Departemen(rsD.getInt("d.id"), rsD.getString("d.nama"));
            if (posisi.equalsIgnoreCase("administrasi")) {
                k =  new Administrasi(rs.getInt("id"), rs.getString("nama"), rs.getString("jenisKelamin"), rs.getString("noHP"), rs.getString("email"), d);
            } else if (posisi.equalsIgnoreCase("teknisi")) {
                k =  new Teknisi(rs.getInt("id"), rs.getString("nama"), rs.getString("jenisKelamin"), rs.getString("noHP"), rs.getString("email"), d);
            } else if (posisi.equalsIgnoreCase("manager")) {
                k =  new Manager(rs.getInt("id"), rs.getString("nama"), rs.getString("jenisKelamin"), rs.getString("noHP"), rs.getString("email"), d);
            } else {
                k =  new PekerjaBiasa(rs.getInt("id"), rs.getString("nama"), rs.getString("jenisKelamin"), rs.getString("noHP"), rs.getString("email"), d);
            }
            
            Menu frame = new Menu(k);
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BLoginActionPerformed

    private void TFpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFpasswordActionPerformed
        BLoginActionPerformed(evt);
    }//GEN-LAST:event_TFpasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        FlatLightLaf.setup();
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login m = new Login();
                m.setLocationRelativeTo(null);
                m.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BLogin;
    private javax.swing.JTextField TFemail;
    private javax.swing.JPasswordField TFpassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
