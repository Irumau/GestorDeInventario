/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Modelos.Producto;
import config.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Mauricio
 */
public class ProductosDao {

    private final String URL = "jdbc:sqlite:inventario.db";

    public ProductosDao(){
        this.createTable();
    }
    
    // CREA LA TABLA, SIEMPRE Y CUANDO NO ALLA SIDO CREADA. 
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" 
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nombre TEXT NOT NULL, " 
                + "precio REAL CHECK(precio > 0), "
                + "cantidad INTEGER CHECK(cantidad >= 0)"
                +");";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.execute();

        } catch (SQLException err) {
            System.err.println("Se a producido un error: " + err.getMessage());

        }
    }

    // DEVUELVE EL PRODUCTO POR EL ID, SERA DE UTILIDAD PARA ACTUALIZAR UN PRODUCTO COMPLETO
    public Producto getProductById(int id) {

        Producto productoEncontrado = null;
        String sql = "SELECT * FROM productos WHERE id = ? LIMIT 1";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    productoEncontrado = new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio")
                    );
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al obtener el producto: " + err.getMessage());
        }

        return productoEncontrado;
    }

    
    //DEVUELVE PRODUCTOS POR EL NOMBRE, DEVUELVE TODOS LOS PRODUCTOS QUE TENGAN UNA COINCIDENCIA EN EL NOMBRE 
    public ArrayList<Producto> getProductsByName(String nombre) {

        ArrayList productosEncontrados = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    productosEncontrados.add(new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio")
                    ));
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al obtener el producto: " + err.getMessage());
        }

        
        System.out.println("Arreglo productos encontrado " + productosEncontrados);
        
        return productosEncontrados;
    }

    public boolean insertProduct(Producto producto) {
        // 1. El SQL tiene 'huecos' (?) para los datos
        String sql = "INSERT INTO productos (nombre, precio, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 2. Rellenamos los huecos con los datos del objeto que recibimos
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getCantidad());

            // 3. ¡IMPORTANTE! 
            // Para INSERT, UPDATE o DELETE se usa 'executeUpdate()'
            // NO se usa 'executeQuery()' (ese es solo para SELECT)
            pstmt.executeUpdate();

            System.out.println("¡Producto guardado correctamente!");
            return true;
        } catch (SQLException ex) {
            System.err.println("Error al guardar: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteProduct(Producto producto){
        String sql = "DELETE FROM productos WHERE id = ? ";
        
        
        
        try(Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0;
            
            
            
        }catch(SQLException err){
            System.err.println("Error al eliminar el producto de id: " + producto.getId() + " " + err.getMessage());
            return false;
        }
        
    }

    public boolean updatProduct(Producto producto) {

        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ? WHERE id = ? ";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDouble(3, producto.getPrecio());
            
            
            
            pstmt.setDouble(4, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            
            return filasAfectadas > 0;

        } catch (SQLException err) {
            System.err.println("Ocurrio un error al actualizar el producto: " + err.getMessage());
            return false;
        }

    }

    //ACTUALIZAR SOLO EL PRECIO DE UN PRODUCTO RECIBIENDO EL ID
    public boolean updateProductPrice(Producto producto, double nuevoPrecio){
        
        String sql = "UPDATE productos SET precio = ? WHERE id = ?";
        
        try(Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            
            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setInt(2, producto.getId());
            
            
            
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0;
            
        }catch(SQLException err ){
            System.err.println("No se pudo actualizar el producto con id: " + producto.getId() + " " + err.getMessage());
            return false;
        }
    }
    
    //ACTUALIZA SOLO EL NOMBRE DE UN PRODUCTO RECIBIENDO EL ID
    public boolean updateProductName(Producto producto, String nuevoNombre){
        
        String sql = "UPDATE productos SET nombre = ? WHERE id = ?";
        
        try(Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0 ;
            
            
            
        }catch(SQLException err){
            System.err.println("Ocurreio unn error al actualizar: "+producto.getNombre() + err.getMessage() );
            return false;
        }
        
        
        
    }
    
    //devuelve todo los productos
    public ArrayList<Producto> getProducts() {
        String sql = "SELECT * FROM productos";

        ArrayList listaProductos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );

                listaProductos.add(p);
            }

        } catch (SQLException ex) {
            System.getLogger(ProductosDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return listaProductos;

    }

    //FILTRO PARA LOS PRODUCTOS ENTRE UN RANNGO MINIMO Y MAXIMO DE PRECIO
    public ArrayList<Producto> getProductByPrice(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM productos WHERE precio BETWEEN ? AND ? ORDER BY precio ASC";

        ArrayList<Producto> listaProductos = new ArrayList<>();

        try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                );

                listaProductos.add(p);
            }

        } catch (SQLException err) {
            System.err.println("Ocurrio un error al obtener los productos " + err.getMessage());
        }

        return listaProductos;
    }
    
    
    
    //OBTENER CANTIDAD TOTAL DE PRODUCTOS
    public int getAmountOfProducts(){
        String sql = "SELECT COUNT(*) FROM productos";
        
        
        try(Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                return rs.getInt(1);
            }
                
            
        }catch(SQLException err){
            System.err.println("Ocurrio un error inesperado al obtener la cantidad de productos" + err.getMessage());
        }
        
        
        return 0;
        
    }
    
}
