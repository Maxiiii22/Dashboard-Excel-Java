# 📋 **Sistema de Gestión de Empleados en Excel**

Este proyecto es una aplicación de escritorio para la gestión de personas basada en una hoja de cálculo de Excel, desarrollada en Java utilizando Swing para la interfaz gráfica y Apache POI para el manejo de archivos Excel (.xlsx). Permite realizar operaciones básicas de ABM (Alta, Baja, Modificación) directamente sobre un archivo Excel como si fuera una base de datos.

![Vista previa de la aplicación](Vista-previa-1.jpg)
![Vista previa de la aplicación](Vista-previa-2.jpg)

---

## 🧰 **Funcionalidades Principales**

- **Agregar registros:** Permite ingresar nombre, apellido, ID y correo electrónico, que se guardan automáticamente en el archivo Excel.
- **Modificar registros existentes:** Al buscar el id de un empleado en la tabla, los datos se cargan en los campos para su modificacion.
- **Eliminar registros:** Borrado de la fila seleccionada tanto de la tabla como del archivo Excel.
- **Exportación con estilo personalizado:** Utiliza Apache POI para exportar los datos aplicando formato a celdas y filas.
- **Interfaz gráfica amigable:** Diseñada con NetBeans GUI Builder, permite usar pestañas, paneles y botones fácilmente.

---

## 🧠 **Tecnologías utilizadas**

- **Java 8+**
- **Swing:** Biblioteca nativa de Java para interfaces gráficas.
- **Apache POI:** API para leer y escribir archivos Excel (.xlsx).
- **NetBeans GUI Builder:** Herramienta visual para diseñar formularios Swing.
- **JTable:** Componente de Swing para mostrar datos en forma tabular.

---

## 🗃️ Formato de Datos en el Excel
El archivo `Base_De_Datos_Excel.xlsx` utilizado actúa como base de datos y sigue la siguiente estructura:

| ID | Nombre | Apellido | Correo |
|----|--------|----------|--------|

Cada registro creado o editado desde la interfaz se guarda directamente en este archivo.


