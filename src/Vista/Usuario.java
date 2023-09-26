package Vista;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Vito
 */
public class Usuario extends javax.swing.JFrame {

    ResultSet rs;
    PreparedStatement ps;

    public static int tipo;

    public Usuario() {
        initComponents();
        setTitle("Bienvenido");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")

    /*
    Inicio de las funciones
     */
    private void conectar() {

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        rs = null;

        try {
            ps = conexion.prepareStatement("Select usu,pass,tipo_usuario from usuario where usu=? and pass =?");
            ps.setString(1, txtUSU.getText());
            ps.setString(2, String.valueOf(txtPass.getPassword()));
            rs = ps.executeQuery();

            if (rs.next()) {
                tipo = rs.getInt("tipo_usuario");
                JOptionPane.showMessageDialog(null, "¡Bienvenido! "+ txtUSU.getText());
                if (tipo == 1) {
                    CargarProductos ca = new CargarProductos(1,txtUSU.getText());
                    ca.setVisible(true);
                    this.dispose();

                } else {
                    CargarProductos ca = new CargarProductos(0,txtUSU.getText());
                    ca.setVisible(true);
                    this.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuario o password incorrecto");
            }
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

    }

    private void limpiar() {

        txtPass.setText("");
        txtUSU.setText("");

    }

    /*
    Fin de los botones
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtUSU = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        bntIngresar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnbPrincipal = new javax.swing.JMenuBar();
        mnOpciones = new javax.swing.JMenu();
        mniCrear = new javax.swing.JMenuItem();
        mniInvitado = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/usuario.png"))); // NOI18N

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

        bntIngresar.setText("Ingresar");
        bntIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntIngresarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUSU, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addGap(0, 60, Short.MAX_VALUE)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(bntIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(lblImage)
                        .addGap(104, 104, 104))))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(lblImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUSU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(bntIngresar))
                .addGap(52, 52, 52))
        );

        mnOpciones.setText("Más Opciones");

        mniCrear.setText("Crear Cuenta");
        mniCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCrearActionPerformed(evt);
            }
        });
        mnOpciones.add(mniCrear);

        mniInvitado.setText("Ingresar como invitado");
        mniInvitado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniInvitadoActionPerformed(evt);
            }
        });
        mnOpciones.add(mniInvitado);

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

    private void bntIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntIngresarActionPerformed

        conectar();

    }//GEN-LAST:event_bntIngresarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        limpiar();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void mniCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCrearActionPerformed

        Crear cr = new Crear();
        cr.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_mniCrearActionPerformed

    private void mniInvitadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniInvitadoActionPerformed

        tipo = 3;
        CargarProductos cargar = new CargarProductos(tipo,"invitado");
        cargar.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_mniInvitadoActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed

        System.exit(0);

    }//GEN-LAST:event_mniSalirActionPerformed

    /*
    Fin de los botones
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntIngresar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JMenu mnOpciones;
    private javax.swing.JMenuBar mnbPrincipal;
    private javax.swing.JMenuItem mniCrear;
    private javax.swing.JMenuItem mniInvitado;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JPanel pnlPrincipal;
    public javax.swing.JPasswordField txtPass;
    public javax.swing.JTextField txtUSU;
    // End of variables declaration//GEN-END:variables
}
