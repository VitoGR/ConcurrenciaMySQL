package Vista;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Vito
 */
public class Crear extends javax.swing.JFrame {

    PreparedStatement ps;
    ResultSet rs;
    int pasar = 0;

    public Crear() {
        initComponents();
        setTitle("¡Bienvenido al menú de creación!");
        setLocationRelativeTo(null);
        lblCodigo.setVisible(false);
        txtCodigo.setVisible(false);
        radio();

    }

    /*
    Inicio de las funciones
    */
    
   private void crearCuenta() {
        rs = null;
        ps = null;
        String zeneca = "No hay viento favorable para el que no sabe hacia donde va";
        String diogenes = "He aqui un hombre";

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        rs = null;

        String cod = txtCodigo.getText();
        int tipo = 0;
        if (cod.equals(zeneca) | cod.equals(diogenes)) {
            tipo = 1;
        } else {
            tipo = 0;
        }
        String p1, p2;
        p1 = String.valueOf(txtPass.getPassword());
        p2 = String.valueOf(txtPass.getPassword());
        if (p1.equals(p2)) {
            try {
                ps = conexion.prepareStatement("insert into usuario (usu,pass,tipo_usuario) values (?,?,?)");
                ps.setString(1, txtUSU.getText());
                ps.setString(2, p1);
                ps.setInt(3, tipo);
                int resultado = ps.executeUpdate();
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario " + txtUSU.getText() + " creado exitosamente.");
                    int salida = JOptionPane.showConfirmDialog(null, "Quiere ingresar?", "Confirmar Ingreso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (salida == 0) {
                        limpiar();
                        Usuario u = new Usuario();
                        u.setVisible(true);
                        this.dispose();
                    } else {
                        limpiar();
                    }
                }

                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los Passwords no coinciden");

        }

    }

    private void comprobar() {

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        rs = null;

        try {
            ps = conexion.prepareStatement("select usu from usuario where usu=?");
            ps.setString(1, txtUSU.getText());

            rs = ps.executeQuery();

            if (rs.next()) {
                pasar = 1;
            } else {
                pasar = 0;
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

    }

    private void limpiar() {
        txtCodigo.setText("");
        txtPass.setText("");
        txtUSU.setText("");
        txtRepitaPass.setText("");
    }

    private void radio() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdAdministrador);
        bg.add(rdCliente);
    }
    
    /*
    Fin de las funciones
    */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtUSU = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblRepitaPass = new javax.swing.JLabel();
        txtRepitaPass = new javax.swing.JPasswordField();
        rdAdministrador = new javax.swing.JRadioButton();
        rdCliente = new javax.swing.JRadioButton();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        bntAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnbPrincipal = new javax.swing.JMenuBar();
        mnOpciones = new javax.swing.JMenu();
        mniIniciarSesion = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Nombre de usuario");

        txtUSU.setMaximumSize(new java.awt.Dimension(70, 22));
        txtUSU.setMinimumSize(new java.awt.Dimension(70, 22));
        txtUSU.setPreferredSize(new java.awt.Dimension(70, 22));

        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password");

        txtPass.setMaximumSize(new java.awt.Dimension(70, 22));
        txtPass.setMinimumSize(new java.awt.Dimension(70, 22));
        txtPass.setPreferredSize(new java.awt.Dimension(70, 22));

        lblRepitaPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepitaPass.setText("Repita su password");

        txtRepitaPass.setMaximumSize(new java.awt.Dimension(70, 22));
        txtRepitaPass.setMinimumSize(new java.awt.Dimension(70, 22));
        txtRepitaPass.setPreferredSize(new java.awt.Dimension(70, 22));

        rdAdministrador.setText("¿Administrador?");
        rdAdministrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdAdministradorMouseClicked(evt);
            }
        });

        rdCliente.setText("¿Cliente?");
        rdCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdClienteMouseClicked(evt);
            }
        });

        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("Ingrese el código de administrador");

        txtCodigo.setPreferredSize(new java.awt.Dimension(70, 22));

        bntAceptar.setText("Aceptar");
        bntAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addGap(0, 60, Short.MAX_VALUE)
                .addComponent(bntAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(rdAdministrador)
                            .addGap(86, 86, 86)
                            .addComponent(rdCliente))
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                            .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblRepitaPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                            .addGap(14, 14, 14)
                            .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtRepitaPass, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUSU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUSU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRepitaPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRepitaPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdAdministrador)
                    .addComponent(rdCliente))
                .addGap(5, 5, 5)
                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(bntAceptar))
                .addGap(52, 52, 52))
        );

        mnOpciones.setText("Más Opciones");

        mniIniciarSesion.setText("Iniciar Sesión");
        mniIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniIniciarSesionActionPerformed(evt);
            }
        });
        mnOpciones.add(mniIniciarSesion);

        mniSalir.setText("Salir");
        mniSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSalirActionPerformed(evt);
            }
        });
        mnOpciones.add(mniSalir);

        mnbPrincipal.add(mnOpciones);

        setJMenuBar(mnbPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*
    Inicio de los botones
    */
    
    private void rdAdministradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdAdministradorMouseClicked
        lblCodigo.setVisible(true);
        txtCodigo.setVisible(true);
    }//GEN-LAST:event_rdAdministradorMouseClicked

    private void rdClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdClienteMouseClicked
        lblCodigo.setVisible(false);
        txtCodigo.setVisible(false);
    }//GEN-LAST:event_rdClienteMouseClicked

    private void bntAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAceptarActionPerformed
        comprobar();
        if (pasar == 1) {
            JOptionPane.showMessageDialog(null, "El usuario " + txtUSU.getText() + " ya existe, pruebe con otro");
        } else {
            crearCuenta();
        }
    }//GEN-LAST:event_bntAceptarActionPerformed

    private void mniIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniIniciarSesionActionPerformed

        Usuario us = new Usuario();
        us.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_mniIniciarSesionActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed

        System.exit(0);

    }//GEN-LAST:event_mniSalirActionPerformed

    /*
    Inicio de los botones
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRepitaPass;
    private javax.swing.JMenu mnOpciones;
    private javax.swing.JMenuBar mnbPrincipal;
    private javax.swing.JMenuItem mniIniciarSesion;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JRadioButton rdAdministrador;
    private javax.swing.JRadioButton rdCliente;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtRepitaPass;
    private javax.swing.JTextField txtUSU;
    // End of variables declaration//GEN-END:variables
}
