# 🌃 Cyberpunk Inventory Manager

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-ff00ff?style=for-the-badge)

> Un sistema de gestión de inventario robusto con una interfaz gráfica personalizada estilo Cyberpunk/Synthwave, construido bajo el patrón MVC.

![Screenshot del Proyecto]<img width="1075" height="679" alt="image" src="https://github.com/user-attachments/assets/38fbd8bb-09ab-4c96-81fd-9e0e2fb92291" />

## 🚀 Descripción

Este proyecto es una aplicación de escritorio desarrollada en **Java** que permite gestionar el inventario de una tienda de productos tecnológicos. 

A diferencia de las aplicaciones tradicionales de gestión, este proyecto implementa una **UI (Interfaz de Usuario) personalizada** desde cero, utilizando componentes `Swing` modificados para ofrecer una experiencia visual inmersiva con temática futurista, bordes de neón y modo oscuro, sin sacrificar la funcionalidad.

## 🛠️ Características Principales

### 🎨 Interfaz de Usuario (Cyberpunk UI)
* **Diseño Personalizado:** Botones con efectos hover de neón, tablas con cabeceras estilizadas y campos de texto con bordes brillantes.
* **Feedback Visual:** Validaciones de campos en tiempo real y mensajes de estado integrados.
* **Responsive:** Diseño adaptable utilizando `BorderLayout` y `GridBagLayout`.

### ⚙️ Funcionalidad Técnica (Backend)
* **CRUD Completo:** Crear, Leer, Actualizar y Eliminar productos.
* **Búsqueda Avanzada:** * Búsqueda por **ID** (Prioridad alta).
    * Filtrado en tiempo real por **Nombre**.
    * Filtrado por **Rango de Precios** (Min - Max).
* **Base de Datos SQLite:** Persistencia de datos local sin necesidad de servidores externos.
* **Validaciones:** Control de errores para datos numéricos y campos vacíos.

## 🏗️ Arquitectura del Proyecto

El sistema sigue estrictamente el patrón de diseño **MVC (Modelo - Vista - Controlador)** junto con el patrón **DAO (Data Access Object)** para una separación limpia de responsabilidades.

* **📂 Modelo:** Clases POJO (`Producto`) que representan los datos.
* **📂 DAO:** Clase `ProductosDao` encargada de todas las sentencias SQL (`INSERT`, `SELECT`, `UPDATE`, `DELETE`) y la conexión a SQLite.
* **📂 Vista:** Clases `JFrame` (`VentanaInventario`) que contienen solo la lógica visual y los componentes Swing personalizados.
* **📂 Controlador:** Clase `ProductoController` que actúa como intermediario, gestionando los eventos de los botones y llamando al DAO.

## 💻 Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 17+ recomendado).
* **Interfaz Gráfica:** Java Swing & AWT.
* **Base de Datos:** SQLite.
* **Driver:** `sqlite-jdbc`.
* **IDE:** Apache NetBeans / IntelliJ IDEA

## 🔧 Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone [[https://github.com/tu-usuario/cyberpunk-inventory.git](https://github.com/tu-usuario/cyberpunk-inventory.git)](https://github.com/Irumau/GestorDeInventario)
    ```
2.  **Abrir en tu IDE:** Importa el proyecto en NetBeans o IntelliJ.
3.  **Dependencias:** Asegúrate de agregar la librería `sqlite-jdbc-x.x.x.jar` a tu proyecto.
4.  **Base de Datos:** La aplicación creará automáticamente el archivo `inventario.db` y la tabla necesaria si no existen en la primera ejecución.
5.  **Ejecutar:** Corre el archivo `Main.java`.
