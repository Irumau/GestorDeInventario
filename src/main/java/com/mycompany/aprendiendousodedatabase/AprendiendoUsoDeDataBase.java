/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.aprendiendousodedatabase;

import Controlador.Controller;
import Dao.ProductosDao;
import Vista.VentanaInventario;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Mauricio
 */
public class AprendiendoUsoDeDataBase {

    public static void main(String[] args) {

        
        
        ProductosDao dao = new ProductosDao();
        
        
        VentanaInventario view = new VentanaInventario();
        Controller ctrl = new Controller(view, dao);
        
        
        
        view.setVisible(true);
        
    }
}
