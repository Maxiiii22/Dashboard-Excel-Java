/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*Agregar en Libraries los siguientes .jar :
poi-3.10-FINAL-20140208.jar
poi-example-3.10-FINAL-20140208.jar
poi-excelant-3.10-FINAL-20140208.jar
poi-ooxml-3.10-FINAL-20140208.jar
poi-ooxml-schemas-3.10-FINAL-20140208.jar
poi-scratchpad-3.10-FINAL-20140208.jar
xmlbeans-2.3.0.jar
dom4j-1.6.1.jar

*/

/**
 *
 * @author Usuario
 */
public class Intefaz_Principal extends javax.swing.JFrame {
   XSSFWorkbook libro;
    XSSFSheet hoja;
    private int filaActual;
    File file = new File("Base_De_Datos_Excel.xlsx");

    public Intefaz_Principal() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        cargarLibro();
    }
    
    public void limpiarCampos(){
     txtNombre.setText("");
     txtApellido.setText("");
     txtId.setText("");
     txtCorreo.setText("");
    }    
    
    private void cargarLibro() {
        try {
            if (file.exists()) {
                FileInputStream archivo = new FileInputStream(file);
                libro = new XSSFWorkbook(archivo);
                hoja = libro.getSheet("Empleados"); //Nos referimos a la hoja "Empleados"
                
                // Encontrar la última fila ocupada
                filaActual = hoja.getLastRowNum() + 1; // Suma 1 para comenzar desde la siguiente fila
                archivo.close();
            } 
            else {  // Si el archivo no existe, crea un nuevo libro y hoja
                libro = new XSSFWorkbook();
                hoja = libro.createSheet("Empleados");
                filaActual = 0; // Comienza desde la primera fila
                
                 //Creó la cabecera:
                XSSFRow cabecera = hoja.createRow(0);
                  
                  
                //Creo las columnas de la cabecera:
                XSSFCell nombre = cabecera.createCell(1);
                XSSFCell apellido = cabecera.createCell(2);
                XSSFCell id = cabecera.createCell(3);
                XSSFCell correo = cabecera.createCell(4);
                XSSFCellStyle estiloCabecera = libro.createCellStyle();
                  
                
                //Estilos para las letras de la cabecera:
                XSSFFont fuente = libro.createFont();  // Creo objeto fuente
                fuente.setFontName("Franklin Gothic Book");  
                fuente.setBold(true);  
                fuente.setFontHeightInPoints((short) 14);                   
                  
                // Configuracion de estilos para la cabecera :
                estiloCabecera.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());  
                estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);   
                estiloCabecera.setBorderBottom(BorderStyle.THIN);
                estiloCabecera.setBorderLeft(BorderStyle.THIN);
                estiloCabecera.setBorderRight(BorderStyle.THIN);
                estiloCabecera.setBorderTop(BorderStyle.THIN);
                estiloCabecera.setFont(fuente); 
                estiloCabecera.setAlignment(HorizontalAlignment.CENTER);  
                estiloCabecera.setVerticalAlignment(VerticalAlignment.CENTER);  
                
                //Configuracion de celda:
                nombre.setCellValue("Nombre");
                apellido.setCellValue("Apellido");
                id.setCellValue("Identificador");
                correo.setCellValue("Correo");

                nombre.setCellStyle(estiloCabecera);  //Le agrego los estilos definidos a las columnas
                apellido.setCellStyle(estiloCabecera);
                id.setCellStyle(estiloCabecera);
                correo.setCellStyle(estiloCabecera);
                
                //Configuracion en hoja:
                hoja.autoSizeColumn(1);  //Para que la columna se ajuste al contenido.
                hoja.autoSizeColumn(2);
                hoja.autoSizeColumn(3);
                hoja.autoSizeColumn(4); 
                
                try{
                    OutputStream output = new FileOutputStream("Base_De_Datos_Excel.xlsx");
                    libro.write(output);
                    output.close();
                    
                } catch (Exception e ) {
                    e.printStackTrace();
                }
                
                if (file.exists()) {
                FileInputStream archivo = new FileInputStream(file);
                libro = new XSSFWorkbook(archivo);
                hoja = libro.getSheet("Empleados");

                filaActual = hoja.getLastRowNum() + 1; 
                archivo.close();
                }

            }
        }catch (IOException e) {
         e.printStackTrace();
        }
    }
    
    
    
    private void crearEmpleado(){
        boolean encontrado = true;
        if(txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtId.getText().equals("") || txtCorreo.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Complete los campos");
        }
        else{
          try{
                FileInputStream archivo = new FileInputStream(file);

                //Defino en que columna quiero buscar algo:
                int posicionColumna  = 3; //Columna 3

                for(int i = 1 ; i<= hoja.getLastRowNum() ; i++){  //Recorro todas las filas de hoja
                    Row fila = hoja.getRow(i);
                    Cell columnaId = fila.getCell(posicionColumna);  //Devuelve la columna de la indicada.
                  

                    if(columnaId != null && columnaId.getNumericCellValue() == Double.parseDouble( txtId.getText() ) ) {
                        JOptionPane.showMessageDialog(rootPane, "Este numero de ID ya se esta utilizando");
                        encontrado = false;
                    }
                }
            }
            catch(FileNotFoundException  e){
             e.printStackTrace();
            }
            catch(IOException e){
             e.printStackTrace();
            }
           
               if(encontrado==true){
                        try{

                            //Creó la fila para el nuevo empleado:
                            XSSFRow filaEmpleado = hoja.createRow(filaActual++);

                            //Estilos fila:
                            XSSFCellStyle estiloFilaEmpleados = libro.createCellStyle();
                            XSSFCellStyle formatoColumnaId = libro.createCellStyle();


                            estiloFilaEmpleados.setBorderBottom(BorderStyle.THIN);
                            estiloFilaEmpleados.setBorderLeft(BorderStyle.THIN);
                            estiloFilaEmpleados.setBorderRight(BorderStyle.THIN);
                            estiloFilaEmpleados.setBorderTop(BorderStyle.THIN);   
                            estiloFilaEmpleados.setAlignment(HorizontalAlignment.CENTER);  
                            estiloFilaEmpleados.setVerticalAlignment(VerticalAlignment.CENTER);            
                            formatoColumnaId.setDataFormat((short) 1);
                            formatoColumnaId.setAlignment(HorizontalAlignment.CENTER); 
                            formatoColumnaId.setVerticalAlignment(VerticalAlignment.CENTER);
                            formatoColumnaId.setBorderBottom(BorderStyle.THIN);
                            formatoColumnaId.setBorderLeft(BorderStyle.THIN);
                            formatoColumnaId.setBorderRight(BorderStyle.THIN);
                            formatoColumnaId.setBorderTop(BorderStyle.THIN);   

                            XSSFCell filaNombre = filaEmpleado.createCell(1);
                            XSSFCell filaApellido = filaEmpleado.createCell(2);
                            XSSFCell filaId = filaEmpleado.createCell(3);
                            XSSFCell filaCorreo = filaEmpleado.createCell(4);

                            //Le agrego los valores del txt a las columnas:
                            filaNombre.setCellValue(txtNombre.getText());
                            filaApellido.setCellValue(txtApellido.getText());
                            filaId.setCellValue(Integer.parseInt(txtId.getText()));
                            filaCorreo.setCellValue(txtCorreo.getText());


                            /*Le agrego el estilo a la columnas de la fila: */
                            filaNombre.setCellStyle(estiloFilaEmpleados);
                            filaApellido.setCellStyle(estiloFilaEmpleados);
                            filaId.setCellStyle(estiloFilaEmpleados);
                            filaId.setCellStyle(formatoColumnaId);
                            filaCorreo.setCellStyle(estiloFilaEmpleados);

                            //Configuracion en hoja:
                            hoja.autoSizeColumn(1);  //Para que la columna se ajuste al contenido.
                            hoja.autoSizeColumn(2);
                            hoja.autoSizeColumn(3);
                            hoja.autoSizeColumn(4); 


                            OutputStream output = new FileOutputStream("Base_De_Datos_Excel.xlsx");  //Guardo el libro
                            libro.write(output);
                            output.close();

                            //Limpio los campos:
                            limpiarCampos();

                            JOptionPane.showMessageDialog(rootPane,"Registro Exitoso");    
                        
                        }
                        catch(Exception e){
                          e.printStackTrace();
                          JOptionPane.showMessageDialog(rootPane,"Error en el registro");   
                        }              
                    }
        }
    }
    
  private void editarEmpleado() {
    if (txtId.getText().isEmpty()) {  
        JOptionPane.showMessageDialog(rootPane, "Digite el ID para poder editar al Empleado");
        tblBuscar.setSelected(false);
        tblBuscar.setFocusable(false);
        return;
    }

    try {
        int posicionColumna = 3; // Columna 3

        for (int i = 1; i <= hoja.getLastRowNum(); i++) {
            XSSFRow fila = hoja.getRow(i);
            XSSFCell columnaId = fila.getCell(posicionColumna);

            if (columnaId != null && columnaId.getNumericCellValue() == Double.parseDouble(txtId.getText())) {
                XSSFCell columnaNombre = fila.getCell(1);
                XSSFCell columnaApellido = fila.getCell(2);
                XSSFCell columnaCorreo = fila.getCell(4);

                // Establecer nuevos valores
                columnaNombre.setCellValue(txtNombre.getText());
                columnaApellido.setCellValue(txtApellido.getText());
                columnaCorreo.setCellValue(txtCorreo.getText());

                // Guardar los cambios en el archivo
                FileOutputStream cerrarArchivo = new FileOutputStream(file);
                libro.write(cerrarArchivo);
                
                //Configuracion en hoja:
                hoja.autoSizeColumn(1); 
                hoja.autoSizeColumn(2);
                hoja.autoSizeColumn(3);
                hoja.autoSizeColumn(4); 

                // Cerrar el archivo
                cerrarArchivo.close();

                limpiarCampos();
                btnEditar.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnGuardar.setEnabled(true);
                JOptionPane.showMessageDialog(rootPane, "Empleado editado exitosamente.");
                return; 
            }
        }

        // Si no se encontro el ID durante el for :
        JOptionPane.showMessageDialog(rootPane, "No se encontró ningún empleado con ese ID.");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
private void buscarEmpleado(){
    boolean encontrado=true;
        if(txtId.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Digite el ID para poder buscar al Empleado");
            tblBuscar.setSelected(false);
            tblBuscar.setFocusable(false);
        }
        else{
            try{
                FileInputStream archivo = new FileInputStream(file);

                //Defino en que columna quiero buscar algo:
                int posicionColumna  = 3; //Columna 3

                for(int i = 1 ; i<=hoja.getLastRowNum() ; i++){  //Recorro todas las filas de hoja
                    Row fila = hoja.getRow(i);
                    Cell columnaId = fila.getCell(posicionColumna);  //Devuelve la columna de la indicada.
                    Cell columnaNombre = fila.getCell(1);
                    Cell columnaApellido = fila.getCell(2);
                    Cell columnaCorreo = fila.getCell(4);

                    if(columnaId != null /*&& columnaId.getCellType() == CellType.NUMERIC*/ && columnaId.getNumericCellValue() == Double.parseDouble(txtId.getText())) {
                        txtId.setEditable(false);
                        txtNombre.setText(columnaNombre.getStringCellValue());
                        txtApellido.setText(columnaApellido.getStringCellValue());
                        txtId.setText(String.valueOf((int) columnaId.getNumericCellValue()));
                        txtCorreo.setText(columnaCorreo.getStringCellValue());
                        btnGuardar.setEnabled(false);
                        btnEditar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        encontrado = false;
                        tblBuscar.setText("Cancelar");
                    }
                }
                
                if(encontrado==true){
                    JOptionPane.showMessageDialog(rootPane, "Este usuario no existe.");
                    tblBuscar.setSelected(false);
                    tblBuscar.setFocusable(false);
                }
                archivo.close();
            }
            catch(FileNotFoundException  e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
}
       
       
private void eliminarEmpleado() {
    String botones[] = {"ELIMINAR", "CANCELAR"};

    if (txtId.getText().isEmpty()) {  
        JOptionPane.showMessageDialog(rootPane, "Digite el ID para poder Eliminar al Empleado");
        tblBuscar.setSelected(false);
        tblBuscar.setFocusable(false);
        return;
    }

    try {
        int posicionColumna = 3; // Columna 3
        boolean empleadoEncontrado = false;

        for (int i = 1; i <= hoja.getLastRowNum(); i++) {  //Recorro todas las filas que tengan valores en la hoja de el excel.
            XSSFRow fila = hoja.getRow(i);
            XSSFCell columnaId = fila.getCell(posicionColumna);  // Devuelve la columna 3 de la fila que esta recorriendo

            if (columnaId != null && columnaId.getNumericCellValue() == Double.parseDouble(txtId.getText())) {
                int confirmacion = JOptionPane.showOptionDialog(rootPane, "Seguro que quiere eliminar el usuario con el ID: " + txtId.getText(), "Eliminar", 0, 0, null, botones, null);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    hoja.removeRow(fila); // Eliminar fila
                    empleadoEncontrado = true;
                    break; 
                } else {
                    return;    
                }
            }
        }

        if (empleadoEncontrado == true) {
            reorganizarFilas();  // Reorganizar filas después de eliminar
            
            
            FileOutputStream cerrarArchivo = new FileOutputStream(file);   // Guardar los cambios en el archivo
            libro.write(cerrarArchivo);

            // Cerrar el archivo
            cerrarArchivo.close();

            limpiarCampos();
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(true);
            JOptionPane.showMessageDialog(rootPane, "Empleado eliminado exitosamente.");
        } else {
            // Si el ID no se encuentra
            JOptionPane.showMessageDialog(rootPane, "No se encontró ningún empleado con ese ID.");
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void reorganizarFilas() {
    for (int i = 1; i <= hoja.getLastRowNum(); i++) {
        if (hoja.getRow(i) == null) {  //Si la fila esta vacia 
            hoja.shiftRows(i + 1, hoja.getLastRowNum(), -1);  //Todas las filas que estan debajo de la fila vacia , suben una posicion.
        }
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

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lblPrincipal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblEmpleados = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        tblBuscar = new javax.swing.JToggleButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.darkGray);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Empleados");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 120, 30));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 140, -1));

        jPanel2.setBackground(java.awt.Color.darkGray);
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
        });

        lblPrincipal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        lblPrincipal.setText("Principal");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/home.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lblPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblPrincipal)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 300, 70));

        jPanel5.setBackground(java.awt.Color.darkGray);
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel5MouseMoved(evt);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });

        lblEmpleados.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpleados.setText("Empleados");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lblEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblEmpleados)
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, -1, -1));

        btnSalir.setBackground(java.awt.Color.darkGray);
        btnSalir.setFont(new java.awt.Font("Serif", 0, 36)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnSalir.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnSalirMouseMoved(evt);
            }
        });
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirMouseExited(evt);
            }
        });
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 280, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 670));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText("Interfaz Grafica");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(393, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 910, -1));

        jPanel6.setBackground(new java.awt.Color(69, 69, 70));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Empresarial");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 47, -1, 53));
        jPanel6.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 87, 219, 13));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 910, 150));

        jTabbedPane2.setBackground(new java.awt.Color(102, 102, 102));

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));

        jPanel11.setBackground(new java.awt.Color(102, 102, 102));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empresa.png"))); // NOI18N
        jPanel11.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 130, 170));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Principal");
        jPanel11.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, -1, -1));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab1", jPanel7);

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));

        jPanel12.setBackground(new java.awt.Color(102, 102, 102));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/donacion2.png"))); // NOI18N
        jPanel12.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 140, 140));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Donaciones");
        jPanel12.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 190, 50));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab2", jPanel8);

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));

        jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));

        jPanel13.setBackground(new java.awt.Color(102, 102, 102));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/formEmpleados.png"))); // NOI18N
        jPanel13.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Empleados");
        jPanel13.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

        jPanel20.setBackground(new java.awt.Color(39, 39, 39));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Formulario Empleados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Nombre*");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Apellido*");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Identificacion*");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Correo*");

        txtNombre.setBackground(new java.awt.Color(39, 39, 39));
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrejTextField5ActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellido.setBackground(new java.awt.Color(39, 39, 39));
        txtApellido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.setBorder(null);
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        txtId.setBackground(new java.awt.Color(39, 39, 39));
        txtId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtId.setForeground(new java.awt.Color(255, 255, 255));
        txtId.setBorder(null);
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });

        txtCorreo.setBackground(new java.awt.Color(39, 39, 39));
        txtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.setBorder(null);
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel53)
                    .addComponent(jLabel52)
                    .addComponent(jLabel51)
                    .addComponent(jLabel50)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(txtApellido)
                    .addComponent(txtId)
                    .addComponent(txtCorreo)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator6))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel52)
                .addGap(7, 7, 7)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, -1, -1));

        btnGuardar.setBackground(new java.awt.Color(39, 39, 39));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icon_guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel13.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, 60));

        btnEditar.setBackground(new java.awt.Color(39, 39, 39));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icon_modificar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel13.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 128, 60));

        btnEliminar.setBackground(new java.awt.Color(39, 39, 39));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icon_eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel13.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, -1, 60));

        tblBuscar.setBackground(new java.awt.Color(39, 39, 39));
        tblBuscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblBuscar.setForeground(new java.awt.Color(255, 255, 255));
        tblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icon_buscar.png"))); // NOI18N
        tblBuscar.setText("Buscar");
        tblBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblBuscarActionPerformed(evt);
            }
        });
        jPanel13.add(tblBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 310, 130, 60));

        jScrollPane1.setViewportView(jPanel13);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab3", jPanel10);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 910, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
     jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
     jTabbedPane2.setSelectedIndex(2);

        
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved
     jPanel2.setBackground(Color.white);
     lblPrincipal.setForeground(Color.black);
    }//GEN-LAST:event_jPanel2MouseMoved

    private void jPanel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseMoved
     jPanel5.setBackground(Color.white);
     lblEmpleados.setForeground(Color.black);
    }//GEN-LAST:event_jPanel5MouseMoved

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
     jPanel2.setBackground(Color.darkGray);
        lblPrincipal.setForeground(Color.white);    }//GEN-LAST:event_jPanel2MouseExited

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        jPanel5.setBackground(Color.darkGray);
        lblEmpleados.setForeground(Color.white);    }//GEN-LAST:event_jPanel5MouseExited

    private void txtNombrejTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrejTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrejTextField5ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        crearEmpleado();
        btnGuardar.setFocusable(false);

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarEmpleado();
        tblBuscar.setSelected(false);
        tblBuscar.setText("Buscar");        
        txtId.setEditable(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
     this.dispose();

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        tblBuscar.setSelected(false);
        tblBuscar.setText("Buscar");
        txtId.setEditable(true);
        eliminarEmpleado();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblBuscarActionPerformed
       if(tblBuscar.isSelected()){
           buscarEmpleado();
           tblBuscar.setFocusable(true);
       }
       else if(!tblBuscar.isSelected()){
           txtId.setEditable(true);
           btnGuardar.setEnabled(true);
           btnEditar.setEnabled(false);
           btnEliminar.setEnabled(false);
           limpiarCampos();
           tblBuscar.setSelected(false);
           tblBuscar.setFocusable(false);
           tblBuscar.setText("Buscar");

       }
       
        

        
    }//GEN-LAST:event_tblBuscarActionPerformed

    private void btnSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseMoved
        btnSalir.setBackground(Color.white);
        btnSalir.setForeground(Color.black);
    }//GEN-LAST:event_btnSalirMouseMoved

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        btnSalir.setBackground(Color.darkGray);
        btnSalir.setForeground(Color.white);
    }//GEN-LAST:event_btnSalirMouseExited

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        int tecla = evt.getKeyChar();
        boolean numero = (tecla>=48 && tecla<=57);
        
        if(!numero){
            evt.consume();
        }
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
    char tecla = evt.getKeyChar();
    boolean esLetra = Character.isLetter(tecla); //Solo letras

    if (!esLetra) {
        evt.consume();
    }
    
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped
    char tecla = evt.getKeyChar();
    String caracter = String.valueOf(tecla);
    boolean esValido = caracter.matches("[a-zA-Z0-9._@-]");  //Permite el ingreso solamente de esos caracteres.

    if (!esValido) {
        evt.consume();
    }

    }//GEN-LAST:event_txtCorreoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
    char tecla = evt.getKeyChar();
    boolean esLetra = Character.isLetter(tecla);  //Solo letras

    if (!esLetra) {
        evt.consume();
    }

    }//GEN-LAST:event_txtNombreKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Intefaz_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Intefaz_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Intefaz_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Intefaz_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Intefaz_Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblEmpleados;
    private javax.swing.JLabel lblPrincipal;
    private javax.swing.JToggleButton tblBuscar;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
