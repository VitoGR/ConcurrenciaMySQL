package Vista;

import Conexion.Conexion;
import com.sun.source.tree.BreakTree;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vito
 */
public class Compras extends javax.swing.JFrame {

    DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
    PreparedStatement ps;
    ResultSet rs;
    int admi;
    String u;
    int comp;
    int resultado;

    public Compras(int admi, String u) {
        this.admi = admi;
        this.u = u;
        initComponents();
        setLocationRelativeTo(null);
        invitado();
        cbx();
        conectar();
        deshabilitar();

        System.out.println("El número de compras es: " + admi);

    }

    private void deshabilitar() {
        txtCodigo.setEditable(false);
        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
    }

    private void invitado() {
        if (admi == 0) {
            setTitle("Bienvenido " + u + " al formulario de compras");
            txtCantidad.enable(false);
            btnComprar.enable(false);
            setTitle("Bienvenido " + u);
        }
        if (admi == 3) {
            setTitle("Bienvenido " + u + ", usted no puede comprar");
            txtCantidad.enable(false);
            btnComprar.setEnabled(false);
        }
        if (admi == 1) {
            setTitle("Bienvenido " + u + " al formulario de compras");
            txtCantidad.setEditable(true);
            btnComprar.enable(true);
        }
    }

    private void cbx() {
        cbxModel.addElement("Seleccione tipo de búsqueda");
        cbxModel.addElement("ID:");
        cbxModel.addElement("Código:");
        cbxModel.addElement("Nombre:");
    }

    private void conectar() {
        DefaultTableModel tblModel = new DefaultTableModel();
        tblProductos.setModel(tblModel);

        String campo = txtBuscar.getText();
        String where = "";
        if (cbxBuscar.getSelectedIndex() == 1 & !"".equals(campo)) {
            where = "where id = " + campo;
        }
        if (cbxBuscar.getSelectedIndex() == 2 & !"".equals(campo)) {
            where = "where codigo = " + campo;
        }
        if (cbxBuscar.getSelectedIndex() == 3 & !"".equals(campo)) {
            where = "where nombre = '" + campo + "'";
        }
        System.out.println(where);
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        rs = null;

        try {
            ps = conexion.prepareStatement("Select id,codigo,nombre,precio,cantidad from producto " + where);
            rs = ps.executeQuery();

            tblModel.addColumn("ID");
            tblModel.addColumn("Código");
            tblModel.addColumn("Nombre");
            tblModel.addColumn("Precio");
            tblModel.addColumn("Cantidad");
            Object[] fila = new Object[5];
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);
                }

                tblModel.addRow(fila);

            }
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

    }

    private void comprobar() {

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        int txt = Integer.parseInt(txtCantidad.getText());

        try {
            conexion.setAutoCommit(false);
            ps = conexion.prepareStatement("select cantidad,nombre from producto where codigo =?");
            ps.setString(1, txtCodigo.getText());

            rs = ps.executeQuery();

            if (rs.next()) {
                int cant = rs.getInt("cantidad");
                String pro = rs.getString("nombre");
                if (txt > 0) {
                    if (cant > 0) {
                        if (cant >= txt) {
                            resultado = cant - txt;
                            comp = 1;
                        } else if (txt == 0 | cant < txt) {
                            JOptionPane.showMessageDialog(null, "la cantidad ingresada es mayor a la disponible en stock");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay stock disponible de " + pro);
                        comp = 0;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada ("+txt+") no puede ser menor o igual a 0");
                }

            }

            ///Segunda parte
            if (comp == 1) {
                try {
                    ps = conexion.prepareStatement("update producto set nombre=?,precio=?,cantidad=? where codigo=?");
                    ps.setString(1, txtNombre.getText());
                    ps.setDouble(2, Double.parseDouble(txtCodigo.getText()));
                    ps.setInt(3, resultado);
                    ps.setString(4, txtCodigo.getText());

                    ps.executeUpdate();

//                    JOptionPane.showMessageDialog(null, "Gracias por su compra");
                } catch (Exception e) {
                    conexion.rollback();
                    System.err.println("Error al insertar " + e);
                }
                conexion.commit();
                JOptionPane.showMessageDialog(null, "Gracias por su compra.");

            }
            ///Fin segunda parte    

        } catch (Exception e) {
            System.err.println("Error al Comprobar " + e);
            try {
                conexion.rollback();
            } catch (Exception e1) {
                System.err.println("Error al Comprobar " + e1);
            }
        } finally {
            try {
                conexion.close();

            } catch (Exception e) {
                System.err.println("Error al Comprobar " + e);
            }
        }
        
        comp =0;

        conectar();

    }

    private void limpiar() {
        cbxBuscar.setSelectedIndex(0);
        txtBuscar.setText("");
        txtNombre.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }

    private void modificar() {

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;

        try {
            ps = conexion.prepareStatement("update producto set nombre=?,precio=?,cantidad=? where codigo=?");
            ps.setString(1, txtNombre.getText());
            ps.setDouble(2, Double.parseDouble(txtCodigo.getText()));
            ps.setInt(3, resultado);
            ps.setString(4, txtCodigo.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Gracias por su compra");

        } catch (Exception e) {
            System.err.println("Error al insertar " + e);
        } finally {
            try {
                conexion.close();

            } catch (Exception e) {
                System.err.println("Error al insertar " + e);
            }
        }

        conectar();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBuscar = new javax.swing.JPanel();
        cbxBuscar = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        pnlTXT = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        pnlBotones = new javax.swing.JPanel();
        btnComprar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnPrincipal = new javax.swing.JMenuBar();
        mnCargarProductos = new javax.swing.JMenu();
        mniCargarProductos = new javax.swing.JMenuItem();
        mniCerrarSesion = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(550, 550));
        setMinimumSize(new java.awt.Dimension(550, 550));
        setPreferredSize(new java.awt.Dimension(550, 550));
        setResizable(false);

        pnlBuscar.setBackground(new java.awt.Color(0, 153, 0));
        pnlBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 153)));

        cbxBuscar.setModel(cbxModel);

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setForeground(new java.awt.Color(255, 51, 0));
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 0)));

        btnBuscar.setBackground(new java.awt.Color(255, 51, 0));
        btnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBuscarLayout = new javax.swing.GroupLayout(pnlBuscar);
        pnlBuscar.setLayout(pnlBuscarLayout);
        pnlBuscarLayout.setHorizontalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlBuscarLayout.setVerticalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pnlTable.setBackground(new java.awt.Color(0, 153, 0));
        pnlTable.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 153)));

        tblProductos.setBackground(new java.awt.Color(51, 51, 51));
        tblProductos.setForeground(new java.awt.Color(255, 51, 51));
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Código", "Nombre", "Precio", "Cantidad"
            }
        ));
        tblProductos.setGridColor(new java.awt.Color(0, 153, 153));
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlTXT.setBackground(new java.awt.Color(0, 153, 0));
        pnlTXT.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 153)));

        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setText("Código:");

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setForeground(new java.awt.Color(255, 51, 0));
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 0)));
        txtCodigo.setMinimumSize(new java.awt.Dimension(64, 20));
        txtCodigo.setPreferredSize(new java.awt.Dimension(64, 20));

        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre:");

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setForeground(new java.awt.Color(255, 51, 0));
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 0)));
        txtNombre.setMinimumSize(new java.awt.Dimension(64, 20));
        txtNombre.setPreferredSize(new java.awt.Dimension(64, 20));

        lblPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecio.setText("Precio:");

        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setForeground(new java.awt.Color(255, 51, 0));
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 0)));
        txtPrecio.setMinimumSize(new java.awt.Dimension(64, 20));
        txtPrecio.setPreferredSize(new java.awt.Dimension(64, 20));

        lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidad.setText("Cantidad:");

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setForeground(new java.awt.Color(255, 51, 0));
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 0)));
        txtCantidad.setMinimumSize(new java.awt.Dimension(64, 20));
        txtCantidad.setPreferredSize(new java.awt.Dimension(64, 20));

        javax.swing.GroupLayout pnlTXTLayout = new javax.swing.GroupLayout(pnlTXT);
        pnlTXT.setLayout(pnlTXTLayout);
        pnlTXTLayout.setHorizontalGroup(
            pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTXTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(lblCodigo))
                .addGap(18, 18, 18)
                .addGroup(pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTXTLayout.createSequentialGroup()
                        .addComponent(lblPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTXTLayout.createSequentialGroup()
                        .addComponent(lblCantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        pnlTXTLayout.setVerticalGroup(
            pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTXTLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblPrecio)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlTXTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblCantidad)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pnlBotones.setBackground(new java.awt.Color(0, 153, 0));
        pnlBotones.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 153)));

        btnComprar.setBackground(new java.awt.Color(0, 153, 153));
        btnComprar.setForeground(new java.awt.Color(0, 0, 0));
        btnComprar.setText("Compar");
        btnComprar.setMaximumSize(new java.awt.Dimension(90, 23));
        btnComprar.setMinimumSize(new java.awt.Dimension(90, 23));
        btnComprar.setPreferredSize(new java.awt.Dimension(90, 23));
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 0));
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.setMaximumSize(new java.awt.Dimension(90, 23));
        btnCancelar.setMinimumSize(new java.awt.Dimension(90, 23));
        btnCancelar.setPreferredSize(new java.awt.Dimension(90, 23));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        mnCargarProductos.setText("Más opciones");

        mniCargarProductos.setText("Cargar productos");
        mniCargarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCargarProductosActionPerformed(evt);
            }
        });
        mnCargarProductos.add(mniCargarProductos);

        mniCerrarSesion.setText("Cerrar sesión");
        mniCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCerrarSesionActionPerformed(evt);
            }
        });
        mnCargarProductos.add(mniCerrarSesion);

        mniSalir.setText("Salir");
        mniSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSalirActionPerformed(evt);
            }
        });
        mnCargarProductos.add(mniSalir);

        mnPrincipal.add(mnCargarProductos);

        setJMenuBar(mnPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTXT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlTXT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        conectar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        comprobar();
//        if (comp == 1) {
//            System.out.println("Ejecuta el update");
//            modificar();
//        }
    }//GEN-LAST:event_btnComprarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;
        rs = null;

        try {
            int fila = tblProductos.getSelectedRow();
            String codigo = tblProductos.getValueAt(fila, 1).toString();
            ps = conexion.prepareStatement("Select id,codigo,nombre,precio from producto where codigo=?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                txtCodigo.setText(rs.getString("codigo"));
                txtNombre.setText(rs.getString("nombre"));
                txtPrecio.setText(String.valueOf(rs.getDouble("precio")));
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void mniCargarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCargarProductosActionPerformed

        CargarProductos cargar = new CargarProductos(admi, u);
        cargar.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_mniCargarProductosActionPerformed

    private void mniCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarSesionActionPerformed
        /*
        
        Usuario us = new Usuario();
        us.setVisible(true);
        this.dispose();
        */

    }//GEN-LAST:event_mniCerrarSesionActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed

        System.exit(0);

    }//GEN-LAST:event_mniSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnComprar;
    private javax.swing.JComboBox<String> cbxBuscar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JMenu mnCargarProductos;
    private javax.swing.JMenuBar mnPrincipal;
    private javax.swing.JMenuItem mniCargarProductos;
    private javax.swing.JMenuItem mniCerrarSesion;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBuscar;
    private javax.swing.JPanel pnlTXT;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
