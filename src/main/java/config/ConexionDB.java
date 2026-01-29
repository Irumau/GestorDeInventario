/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author Mauricio
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Solo cambiamos esto si movemos el archivo de lugar
    private static final String URL = "jdbc:sqlite:inventario.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

}
