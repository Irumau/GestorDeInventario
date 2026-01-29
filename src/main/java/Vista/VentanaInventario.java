package Vista;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class VentanaInventario extends JFrame {

    // --- PALETA DE COLORES CYBERPUNK (Basada en tu imagen 1) ---
    private final Color COLOR_FONDO_APP = new Color(10, 10, 20);     // Casi negro
    private final Color COLOR_FONDO_PANEL = new Color(15, 15, 25);   // Un poco más claro
    private final Color COLOR_NEON = new Color(0, 255, 255);         // Cian Eléctrico
    private final Color COLOR_TEXTO_BLANCO = Color.WHITE;
    private final Color COLOR_FONDO_LABELS = new Color(44, 44, 44);
    private final Font FUENTE_TITULO = new Font("Impact", Font.PLAIN, 36); // Fuente gruesa
    private final Font FUENTE_GENERAL = new Font("Consolas", Font.PLAIN, 16); // Fuente tipo código

    // Componentes que necesitarán acceso desde el Controlador (públicos o con getters)
    public JTextField txtNombre, txtCantidad, txtPrecio;
    public JTextField txtBuscarNombre, txtMinPrecio, txtMaxPrecio, txtBuscarID;
    public JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnBuscar;
    public JTable tablaProductos;
    public DefaultTableModel modeloTabla;

   
    
    public VentanaInventario() {
        configurarVentana();
        initUI();
    }

    
//    public void cargarSearchIcon(JButton btn, String rutaImg){
//        try{
//            
//            BufferedImage imgOriginal = ImageIO.read(new File(rutaImg));
//            if(imgOriginal == null){
//                System.err.println("No se encontró el icono: " + rutaImg);
//                btn.setText("?");
//                return;
//            }
//            
//            
//            
//            Image imgRedim = imgOriginal.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//            
//            btn.setIcon(new ImageIcon(imgRedim));
//            btn.setText("");
//            
//        }catch(IOException err){
//            System.out.println("Error al cargar la imagen: " + err.getMessage());
//        }
//    }
    
    private void configurarVentana() {
        setTitle("Gestión de Inventario");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO_APP);
        setLayout(new BorderLayout(10, 10)); // Espacio entre paneles principales
        // Margen externo para toda la ventana
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void initUI() {
        // 1. TÍTULO SUPERIOR
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(COLOR_FONDO_PANEL);
        // Borde neón + Margen interno
        panelTitulo.setBorder(crearBordeNeon(2, 10));

        JLabel lblTitulo = new JLabel("Gestión de Inventario");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO_BLANCO);
        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);

        // 2. PANEL CENTRAL (Divide Izquierda y Derecha)
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas, espacio 10
        panelCentral.setOpaque(false); // Transparente para ver el fondo

        // --- ZONA IZQUIERDA: FORMULARIO ---
        panelCentral.add(crearPanelIzquierdo());

        // --- ZONA DERECHA: BUSCADOR Y TABLA ---
        panelCentral.add(crearPanelDerecho());

        add(panelCentral, BorderLayout.CENTER);
    }

    // ==========================================================================
    //                   CREACIÓN DEL PANEL IZQUIERDO (Formulario)
    // ==========================================================================
    private JPanel crearPanelIzquierdo() {
        JPanel panelIzq = new JPanel(new GridBagLayout());
        panelIzq.setBackground(COLOR_FONDO_PANEL);
        panelIzq.setBorder(crearBordeNeon(2, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // -- Fila 0: Nombre --
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelIzq.add(crearEtiqueta("Nombre"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtNombre = crearInput("");
        panelIzq.add(txtNombre, gbc);

        // -- Fila 1: Cantidad --
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelIzq.add(crearEtiqueta("Cantidad"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtCantidad = crearInput("");
        panelIzq.add(txtCantidad, gbc);

        // -- Fila 2: Precio --
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelIzq.add(crearEtiqueta("Precio"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPrecio = crearInput("");
        panelIzq.add(txtPrecio, gbc);

        // -- Fila 3: Espacio vacío --
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelIzq.add(Box.createVerticalStrut(30), gbc);

        // -- Fila 4: Panel de Botones (Anidado para el borde propio) --
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 5, 15));
        panelBotones.setBackground(COLOR_FONDO_PANEL);
        panelBotones.setBorder(crearBordeNeon(1, 15)); // Borde interno para botones

        btnGuardar = crearBotonNeon("Guardar");
        btnActualizar = crearBotonNeon("Actualizar");
        btnEliminar = crearBotonNeon("Eliminar");
        btnLimpiar = crearBotonNeon("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        // Hacemos que el panel de botones no se estire al 100% horizontalmente
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 50; // Padding interno horizontal extra
        panelIzq.add(panelBotones, gbc);

        // Empujar todo hacia arriba
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        panelIzq.add(Box.createVerticalGlue(), gbc);

        return panelIzq;
    }

    // ==========================================================================
    //                   CREACIÓN DEL PANEL DERECHO (Buscador + Tabla)
    // ==========================================================================
    private JPanel crearPanelDerecho() {
        JPanel panelDer = new JPanel(new BorderLayout(10, 10));
        panelDer.setBackground(COLOR_FONDO_PANEL);
        panelDer.setBorder(crearBordeNeon(2, 20));

        // --- SUB-PANEL SUPERIOR: BUSCADORES ---
        JPanel panelBuscadores = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBuscadores.setOpaque(false);

        // Fila 1: Buscador Nombre + Min/Max
        JPanel fila1 = new JPanel(new GridLayout(1, 3, 10, 0));
        fila1.setOpaque(false);
        fila1.add(crearPanelInputPeque("Buscador Por Nombre", txtBuscarNombre = crearInput("")));
        fila1.add(crearPanelInputPeque("Min $", txtMinPrecio = crearInput("")));
        fila1.add(crearPanelInputPeque("Max $", txtMaxPrecio = crearInput("")));
        panelBuscadores.add(fila1);

        // Fila 2: Buscador ID + Botón Lupa
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        fila2.setOpaque(false);
        // Usamos un tamaño preferido para que el input de ID no sea gigante
        JPanel pnlId = crearPanelInputPeque("Buscador Por ID", txtBuscarID = crearInput(""));
        pnlId.setPreferredSize(new Dimension(200, 60));
        fila2.add(pnlId);

        //btnBuscar = crearBotonNeon("");
        
        //btnBuscar.setPreferredSize(new Dimension(40, 35));
//        cargarSearchIcon(btnBuscar, "src/main/java/Assets/search_icon.png");
        // Alineamos el botón un poco abajo para que coincida con el input
        JPanel pnlBtnContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBtnContainer.setOpaque(false);
        pnlBtnContainer.setBorder(new EmptyBorder(20, 0, 0, 0));
        //pnlBtnContainer.add(btnBuscar);
        fila2.add(pnlBtnContainer);

        panelBuscadores.add(fila2);

        panelDer.add(panelBuscadores, BorderLayout.NORTH);

        // --- ZONA CENTRAL: LA TABLA ---
        String[] columnas = {"ID", "Nombre", "Stock", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        estilizarTabla(tablaProductos);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.getViewport().setBackground(COLOR_FONDO_PANEL); // Fondo del hueco de la tabla
        scroll.setBorder(crearBordeNeon(1, 0)); // Borde del scroll

        panelDer.add(scroll, BorderLayout.CENTER);

        return panelDer;
    }

    // ==========================================================================
    //                   MÉTODOS AUXILIARES DE ESTILO (LA MAGIA)
    // ==========================================================================
    // 1. Fábrica de bordes Neón Compuestos (Borde + Margen)
    private Border crearBordeNeon(int grosorLinea, int margenInterno) {
        Border lineaNeon = new LineBorder(COLOR_NEON, grosorLinea);
        Border margen = new EmptyBorder(margenInterno, margenInterno, margenInterno, margenInterno);
        return new CompoundBorder(lineaNeon, margen);
    }

    // 2. Fábrica de Etiquetas (Labels)
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(COLOR_TEXTO_BLANCO);
        lbl.setFont(FUENTE_TITULO.deriveFont(Font.BOLD, 20f));
        return lbl;
    }

    // 3. Fábrica de Inputs (TextFields)
    private JTextField crearInput(String textoPlaceholder) {
        JTextField txt = new JTextField(textoPlaceholder);
        txt.setBackground(COLOR_FONDO_LABELS);
        txt.setForeground(COLOR_TEXTO_BLANCO);
        txt.setCaretColor(COLOR_NEON); // Color del cursor parpadeante
        // Borde neón más fino para inputs
        txt.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_NEON, 1),
                new EmptyBorder(5, 10, 5, 10) // Padding interno del texto
        ));
        txt.setFont(FUENTE_GENERAL);
        return txt;
    }

    // 4. Fábrica de Botones Neón (EL TRUCO PARA QUE FUNCIONE)
    private JButton crearBotonNeon(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_GENERAL);

        btn.setForeground(COLOR_TEXTO_BLANCO);
        btn.setBackground(COLOR_FONDO_PANEL.brighter()); // Un poco más claro que el fondo
        btn.setBorder(crearBordeNeon(1, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- LOS TRUCOS PARA WINDOWS ---
        btn.setContentAreaFilled(false); // Desactiva el pintado nativo del fondo
        btn.setOpaque(true);             // Permite que se pinte nuestro color de fondo
        btn.setFocusPainted(false);      // Quita el recuadro de foco feo

        // Efecto Hover simple (cambia color al pasar el mouse)
        // esto se puede mover a la view
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_NEON.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_FONDO_PANEL.brighter());
                btn.setBorder(crearBordeNeon(1, 10));

            }

        });
        return btn;
    }

    // 5. Helper para los inputs pequeños con título arriba (lado derecho)
    private JPanel crearPanelInputPeque(String titulo, JTextField input) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(COLOR_TEXTO_BLANCO);
        lbl.setFont(FUENTE_GENERAL.deriveFont(12f));
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        p.add(lbl, BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    // 6. Estilizar la Tabla
    private void estilizarTabla(JTable table) {
        // --- ESTILO DEL CUERPO DE LA TABLA (IGUAL QUE ANTES) ---
        table.setBackground(COLOR_FONDO_PANEL);
        table.setForeground(COLOR_NEON);
        table.setGridColor(COLOR_NEON);
        table.setSelectionBackground(COLOR_NEON);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(FUENTE_GENERAL);
        table.setRowHeight(30);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);

        // Centrar datos
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // --- AQUÍ ESTÁ LA MAGIA PARA LA CABECERA (HEADER) ---
        JTableHeader header = table.getTableHeader();

        header.setReorderingAllowed(false);
        
        // Desactivamos el "look" por defecto y creamos uno propio celda por celda
        header.setDefaultRenderer((t, value, isSelected, hasFocus, row, column) -> {

            // Creamos una etiqueta nueva para cada columna
            JLabel etiqueta = new JLabel(value.toString());

            // 1. OBLIGATORIO: setOpaque(true) para que el color de fondo se vea
            etiqueta.setOpaque(true);

            // 2. Colores Cyberpunk
            etiqueta.setBackground(COLOR_FONDO_APP); // Fondo oscuro (negro/azulado)
            etiqueta.setForeground(COLOR_TEXTO_BLANCO); // Texto blanco

            // 3. Fuente y Alineación
            etiqueta.setFont(FUENTE_GENERAL);
            etiqueta.setHorizontalAlignment(JLabel.CENTER);

            // 4. Borde inferior y derecho de neón para separar columnas
            etiqueta.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, COLOR_NEON));

            return etiqueta;
        });

        header.setBorder(null);

        // Importante: forzar que la tabla ocupe todo el alto si hay pocas filas
        table.setFillsViewportHeight(true);
    }

}
