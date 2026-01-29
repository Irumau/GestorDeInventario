/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelos.Producto;

import Dao.ProductosDao;

import Vista.VentanaInventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mauricio
 */
public class Controller implements ActionListener {

    private Producto producto;
    private ProductosDao dao;
    private VentanaInventario view;

    public Controller(VentanaInventario view, ProductosDao dao) {
        this.view = view;
        this.dao = dao;
        this.producto = new Producto();

        this.view.btnGuardar.addActionListener(this);
        this.view.btnActualizar.addActionListener(this);
        this.view.btnEliminar.addActionListener(this);
        this.view.btnLimpiar.addActionListener(this);
        //this.view.btnBuscar.addActionListener(this);

        this.listarProductos();

        this.view.tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
        KeyListener buscadorListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarProductos();
            }
        };

        view.txtBuscarNombre.addKeyListener(buscadorListener);
        view.txtBuscarID.addKeyListener(buscadorListener);
        view.txtMinPrecio.addKeyListener(buscadorListener);
        view.txtMaxPrecio.addKeyListener(buscadorListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btnGuardar) {
            guardarProducto();
        } else if (e.getSource() == view.btnActualizar) {
            actualizarProducto();
        } else if (e.getSource() == view.btnEliminar) {
            eliminarProducto();
        } else if (e.getSource() == view.btnLimpiar) {
            limpiarCampos();
        } else if (e.getSource() == view.btnBuscar) {
            buscarProductos();
        }

    }

    private void limpiarCampos() {
        view.txtNombre.setText("");
        view.txtCantidad.setText("");
        view.txtPrecio.setText("");
        view.txtBuscarID.setText("");
        view.tablaProductos.clearSelection();
    }

    private void eliminarProducto() {
        int fila = view.tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Debes seleccionar un producto");
        } else {
            int id = Integer.parseInt(view.tablaProductos.getValueAt(fila, 0).toString());
            dao.deleteProduct(dao.getProductById(id));
            JOptionPane.showMessageDialog(view, "Producto Eliminado");
            listarProductos();
        }

    }

    private void llenarCamposDesdeTabla() {
        int fila = view.tablaProductos.getSelectedRow();
        if (fila != -1) {
            // La columna 0 es el ID, la 1 Nombre, 2 Stock, 3 Precio (según tu orden)
            view.txtNombre.setText(view.tablaProductos.getValueAt(fila, 1).toString());
            view.txtCantidad.setText(view.tablaProductos.getValueAt(fila, 2).toString());
            view.txtPrecio.setText(view.tablaProductos.getValueAt(fila, 3).toString());

            // OPCIONAL: Si tienes un campo oculto o label para el ID, guárdalo ahí.
            // Si no, lo sacaremos de la tabla al momento de actualizar.
        }
    }

    private void actualizarProducto() {
        int fila = view.tablaProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Primero selecciona un producto de la tabla.");
            return;
        }

        try {
            int id = Integer.parseInt(view.tablaProductos.getValueAt(fila, 0).toString());
            String nuevoNombre = view.txtNombre.getText();
            int nuevaCantidad = Integer.parseInt(view.txtCantidad.getText());
            double nuevoPrecio = Double.parseDouble(view.txtPrecio.getText());

            // 4. Crear objeto con todo mezclado (ID viejo + Datos nuevos)
            Producto productoEditado = new Producto();
            productoEditado.setId(id);
            productoEditado.setNombre(nuevoNombre);
            productoEditado.setCantidad(nuevaCantidad);
            productoEditado.setPrecio(nuevoPrecio);

            if (dao.updatProduct(productoEditado)) {
                JOptionPane.showMessageDialog(view, "Producto actualizado");
                listarProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(view, "Error al actualizar.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Revisa que Precio y Cantidad sean números.");
        }

    }

    private void guardarProducto() {
        try {
            String nombre = view.txtNombre.getText();
            int cantidad = Integer.parseInt(view.txtCantidad.getText());
            double precio = Double.parseDouble(view.txtPrecio.getText());

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setCantidad(cantidad);
            p.setPrecio(precio);

            if (dao.insertProduct(p)) {
                JOptionPane.showMessageDialog(view, "Producto registrado!!");
                limpiarCampos();
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(view, "Error al guardar el producto!!");
            }

        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(view, "Error: Cantidad y Precio deben ser números.");
        }
    }

    private void listarProductos() {
        ArrayList<Producto> lista = dao.getProducts();

        DefaultTableModel modelo = view.modeloTabla;

        modelo.setRowCount(0);

        for (Producto p : lista) {
            Object[] fila = new Object[4];
            fila[0] = p.getId();
            fila[1] = p.getNombre();
            fila[2] = p.getCantidad();
            fila[3] = p.getPrecio();

            modelo.addRow(fila);
        }
    }

    private void limpiarTabla() {
        DefaultTableModel modelo = view.modeloTabla;

    }

    private void buscarProductos() {
        String nombreTexto = view.txtBuscarNombre.getText().trim();
        String idTexto = view.txtBuscarID.getText().trim();
        String minTexto = view.txtMinPrecio.getText().trim();
        String maxTexto = view.txtMaxPrecio.getText().trim();
        ArrayList<Producto> lista = new ArrayList<>();

        try {

            if (!idTexto.isEmpty()) {
                try {
                    int id = Integer.parseInt(idTexto);

                    Producto p = dao.getProductById(id);
                    System.out.println("produtct id: " + p.getId());
                    if (p != null) {
                        lista.add(p);
                        System.out.println("produtct id: " + p.getId());
                    }
                    
                    
                   
                    for(Producto pe : lista){
                        System.out.println(pe.getId());
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "El ID debe ser un valor numerico");
                }
            } else if (!nombreTexto.isEmpty()) {
                lista = dao.getProductsByName(nombreTexto);
                
                for(Producto duc : lista){
                    System.out.println("Productos" + duc.getNombre());
                }

            } else if (!minTexto.isEmpty() && !maxTexto.isEmpty()) {
                try {
                    double min = Double.parseDouble(minTexto);
                    double max = Double.parseDouble(maxTexto);

                    lista = dao.getProductByPrice(min, max);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Min y Max deben ser valores numericos");
                }
            } else {
                lista = dao.getProducts();
            }

            llenarTabla(lista);

        } catch (Exception e) {
            System.err.println("Error en busqueda: " + e.getMessage());
        }
    }

    private void llenarTabla(ArrayList<Producto> lista) {
        DefaultTableModel modelo = view.modeloTabla;

        modelo.setRowCount(0);

        for (Producto p : lista) {
            Object[] fila = new Object[4];
            fila[0] = p.getId();
            fila[1] = p.getNombre();
            fila[2] = p.getCantidad();
            fila[3] = p.getPrecio();

            modelo.addRow(fila);
        }
    }
}
