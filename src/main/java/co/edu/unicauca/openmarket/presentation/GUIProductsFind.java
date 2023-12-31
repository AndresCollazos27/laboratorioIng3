/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.openmarket.presentation;

import co.edu.unicauca.openmarket.domain.Product;
import co.edu.unicauca.openmarket.domain.service.ProductService;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import co.edu.unicauca.openmarket.infra.Messages;
/**
 *
 * @author Libardo Pantoja
 */
public class GUIProductsFind extends javax.swing.JDialog {

    private final ProductService productService;

    /**
     * Creates new form GUIProductsFind
     * @param parent
     * @param modal
     * @param productService
     */
    public GUIProductsFind(java.awt.Frame parent, boolean modal, ProductService productService) {
        super(parent, modal);
        initComponents();
        initializeTable();
        this.productService = productService;
        setLocationRelativeTo(null); //centrar al ventana
    }

    private void initializeTable() {
        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Id", "Name", "Description","Categoria"
                }
        ));
    }

    private void fillTable(List<Product> listProducts) {
        initializeTable();
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();

        Object rowData[] = new Object[4];//No columnas
        for (int i = 0; i < listProducts.size(); i++) {
            rowData[0] = listProducts.get(i).getProductId();
            rowData[1] = listProducts.get(i).getName();
            rowData[2] = listProducts.get(i).getDescription();
            rowData[3] = listProducts.get(i).getCategory().getName();

            model.addRow(rowData);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlCenter = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        pnlNorth = new javax.swing.JPanel();
        rdoCategoriaId = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        rdoId = new javax.swing.JRadioButton();
        rdoName = new javax.swing.JRadioButton();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnSearchAll = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnCloser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Búsqueda de productos");

        pnlCenter.setLayout(new java.awt.BorderLayout());

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblProducts);

        pnlCenter.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlCenter, java.awt.BorderLayout.CENTER);

        buttonGroup1.add(rdoCategoriaId);
        rdoCategoriaId.setText("ID Categoria");
        pnlNorth.add(rdoCategoriaId);

        jLabel1.setText("Buscar por:");
        pnlNorth.add(jLabel1);

        buttonGroup1.add(rdoId);
        rdoId.setSelected(true);
        rdoId.setText("Id");
        rdoId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoIdActionPerformed(evt);
            }
        });
        pnlNorth.add(rdoId);

        buttonGroup1.add(rdoName);
        rdoName.setText("Nombre del producto");
        rdoName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNameActionPerformed(evt);
            }
        });
        pnlNorth.add(rdoName);

        txtSearch.setPreferredSize(new java.awt.Dimension(62, 32));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        pnlNorth.add(txtSearch);

        btnSearch.setText("Buscar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnlNorth.add(btnSearch);

        btnSearchAll.setText("Buscar Todos");
        btnSearchAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchAllActionPerformed(evt);
            }
        });
        pnlNorth.add(btnSearchAll);

        getContentPane().add(pnlNorth, java.awt.BorderLayout.PAGE_START);

        btnCloser.setText("Cerrar");
        btnCloser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloserActionPerformed(evt);
            }
        });
        jPanel1.add(btnCloser);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAllActionPerformed
        fillTable(productService.findAllProducts());
    }//GEN-LAST:event_btnSearchAllActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void rdoIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoIdActionPerformed

    private void rdoNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNameActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (rdoId.isSelected()) {
            // Realizar búsqueda por ID
            String idText = txtSearch.getText().trim();
            if (!idText.isEmpty()) {
                try {
                    Long id = Long.valueOf(idText);
                    List<Product> prods = productService.findProductById(id);
                    Product product = prods.get(0);
                    if (product != null) {
                        // Rellenar la tabla o mostrar el resultado de la búsqueda
                        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
                        model.setRowCount(0); // Limpiar la tabla
                        Object[] rowData = {product.getProductId(), product.getName(), product.getDescription(),product.getCategory().getName()};
                        model.addRow(rowData);
                    } else {
                        Messages.showMessageDialog("Producto no encontrado", "ERROR");
                    }
                } catch (NumberFormatException e) {
                    Messages.showMessageDialog("FORMATO NO VALIDO", "ERROR DE FORMATO");
                }
            } else {
                Messages.showMessageDialog("No ingreso nada", "ERROR");
            }
        } else if (rdoName.isSelected()) {
            // Realizar búsqueda por nombre
            String name = txtSearch.getText().trim();
            if (!name.isEmpty()) {
                List<Product> prods = productService.findProductByName(name);
                    Product product = prods.get(0);
                if (product != null) {
                    // Rellenar la tabla o mostrar el resultado de la búsqueda
                    DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
                    model.setRowCount(0); // Limpiar la tabla
                    Object[] rowData = {product.getProductId(), product.getName(), product.getDescription(),product.getCategory().getName()};
                    model.addRow(rowData);
                } else {
                     Messages.showMessageDialog("Producto no encontrado", "ERROR");
                }
            } else {
                 Messages.showMessageDialog("No ingreso nada", "ERROR");
            }
        }else if (rdoCategoriaId.isSelected()) {
            // Realizar búsqueda por nombre
            String text = txtSearch.getText();
            if (!text.isEmpty()) {
                Long categoryId = Long.valueOf(text);
                List<Product> prods = productService.findProductByCategoryId(categoryId);
                if (prods != null) {
                    // Rellenar la tabla o mostrar el resultado de la búsqueda
                    DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
                    model.setRowCount(0); // Limpiar la tabla
                    Object rowData[] = new Object[4];//No columnas
                    for (int i = 0; i < prods.size(); i++) {
                            rowData[0] = prods.get(i).getProductId();
                            rowData[1] = prods.get(i).getName();
                            rowData[2] = prods.get(i).getDescription();
                            rowData[3] = prods.get(i).getCategory().getName();
                            model.addRow(rowData);
                    }
                } else {
                     Messages.showMessageDialog("Producto no encontrado", "ERROR");
                }
            } else {
                 Messages.showMessageDialog("No ingreso nada", "ERROR");
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnCloserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloserActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloser;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchAll;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlCenter;
    private javax.swing.JPanel pnlNorth;
    private javax.swing.JRadioButton rdoCategoriaId;
    private javax.swing.JRadioButton rdoId;
    private javax.swing.JRadioButton rdoName;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
