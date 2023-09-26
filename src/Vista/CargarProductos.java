package Vista;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vito
 */
public class CargarProductos extends javax.swing.JFrame {

    DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
    PreparedStatement ps;
    ResultSet rs;
    int admi;
    String u;

    public CargarProductos(int admi, String u) {
        this.admi = admi;
        this.u = u;
        initComponents();
        setTitle("Carga de productos");
        setLocationRelativeTo(null);
        cbx();
        conectar();
        admin();
        System.out.println("El número de cargar es: "+ admi);

    }

    /*
    Inicio de las funciones
     */
    private void admin() {
        if (admi == 1) {
            setTitle("Bienvenido "+u+" tiene permisos para hacer ABM");
            btnAceptar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);

            txtCodigo.setEnabled(true);
            txtNombre.setEnabled(true);
            txtPrecio.setEnabled(true);
            txtCantidad.setEnabled(true);
        } else if (admi == 0) {
            setTitle("Bienvenido "+u+", no tiene permiso para modificar");
            btnAceptar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);

            txtCodigo.setEnabled(false);
            txtNombre.setEnabled(false);
            txtPrecio.setEnabled(false);
            txtCantidad.setEnabled(false);
        } else {
            setTitle("Bienvenido "+u+" ,no tiene permiso para modificar");
            btnAceptar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);

            txtCodigo.setEnabled(false);
            txtNombre.setEnabled(false);
            txtPrecio.setEnabled(false);
            txtCantidad.setEnabled(false);
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

    private void insertar() {

        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;

        try {
            ps = conexion.prepareStatement("insert into producto (codigo,nombre,precio,cantidad) values(?,?,?,?)");
            ps.setString(1, txtCodigo.getText());
            ps.setString(2, txtNombre.getText());
            ps.setDouble(3, Double.parseDouble(txtCodigo.getText()));
            ps.setInt(4, Integer.parseInt(txtCodigo.getText()));

            ps.executeUpdate();
            limpiar();

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

    private void modificar() {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;

        try {
            ps = conexion.prepareStatement("update producto set nombre=?,precio=?,cantidad=? where codigo=?");
            ps.setString(1, txtNombre.getText());
            ps.setDouble(2, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(3, Integer.parseInt(txtCantidad.getText()));
            ps.setString(4, txtCodigo.getText());

            ps.executeUpdate();
            limpiar();

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

    private void eliminar() {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        ps = null;

        try {
            ps = conexion.prepareStatement("delete from producto where codigo=?");
            ps.setString(1, txtCodigo.getText());
            ps.executeUpdate();
            limpiar();

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

    private void limpiar() {
        cbxBuscar.setSelectedIndex(0);
        txtBuscar.setText("");
        txtNombre.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }

    /*
    Fin de las funciones
     */
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
        btnAceptar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        mnbPrincipal = new javax.swing.JMenuBar();
        mnOpciones = new javax.swing.JMenu();
        mniCompras = new javax.swing.JMenuItem();
        mniCerrarSesion = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(550, 550));
        setMinimumSize(new java.awt.Dimension(550, 550));
        setPreferredSize(new java.awt.Dimension(550, 550));
        setResizable(false);

        pnlBuscar.setBackground(new java.awt.Color(0, 102, 102));
        pnlBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        pnlTable.setBackground(new java.awt.Color(0, 102, 102));
        pnlTable.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));

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

        pnlTXT.setBackground(new java.awt.Color(0, 102, 102));
        pnlTXT.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));

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

        pnlBotones.setBackground(new java.awt.Color(0, 102, 102));
        pnlBotones.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 51)));

        btnAceptar.setBackground(new java.awt.Color(0, 204, 51));
        btnAceptar.setForeground(new java.awt.Color(0, 0, 0));
        btnAceptar.setText("Aceptar");
        btnAceptar.setMaximumSize(new java.awt.Dimension(90, 23));
        btnAceptar.setMinimumSize(new java.awt.Dimension(90, 23));
        btnAceptar.setPreferredSize(new java.awt.Dimension(90, 23));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 255, 0));
        btnModificar.setForeground(new java.awt.Color(0, 0, 0));
        btnModificar.setText("Modificar");
        btnModificar.setMaximumSize(new java.awt.Dimension(90, 23));
        btnModificar.setMinimumSize(new java.awt.Dimension(90, 23));
        btnModificar.setPreferredSize(new java.awt.Dimension(90, 23));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setMaximumSize(new java.awt.Dimension(90, 23));
        btnEliminar.setMinimumSize(new java.awt.Dimension(90, 23));
        btnEliminar.setPreferredSize(new java.awt.Dimension(90, 23));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(51, 51, 255));
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
                .addGap(14, 14, 14)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        mnOpciones.setText("Más opciones");

        mniCompras.setText("Compras");
        mniCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniComprasActionPerformed(evt);
            }
        });
        mnOpciones.add(mniCompras);

        mniCerrarSesion.setText("Cerrar sesión");
        mniCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCerrarSesionActionPerformed(evt);
            }
        });
        mnOpciones.add(mniCerrarSesion);

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

    /*
    Inicio de los botones
     */

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        conectar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        insertar();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            ps = conexion.prepareStatement("Select id,codigo,nombre,precio,cantidad from producto where codigo=?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                txtCodigo.setText(rs.getString("codigo"));
                txtNombre.setText(rs.getString("nombre"));
                txtPrecio.setText(String.valueOf(rs.getDouble("precio")));
                txtCantidad.setText(String.valueOf(rs.getInt("cantidad")));
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void mniComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniComprasActionPerformed

        Compras cm = new Compras(admi,u);
        cm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniComprasActionPerformed

    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed

        System.exit(0);

    }//GEN-LAST:event_mniSalirActionPerformed

    private void mniCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarSesionActionPerformed
      /*
          Usuario us = new Usuario();
        us.setVisible(true);
        this.dispose();
        */
    }//GEN-LAST:event_mniCerrarSesionActionPerformed

    /*
    Fin de los botones
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxBuscar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JMenu mnOpciones;
    private javax.swing.JMenuBar mnbPrincipal;
    private javax.swing.JMenuItem mniCerrarSesion;
    private javax.swing.JMenuItem mniCompras;
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
