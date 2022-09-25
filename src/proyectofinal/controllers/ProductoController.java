/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectofinal.dao.ProductoDao;
import proyectofinal.models.Aseo;
import proyectofinal.models.Mascotas;
import proyectofinal.models.Otro;
import proyectofinal.models.Papeleria;
import proyectofinal.models.Producto;
import proyectofinal.models.Viveres;
import proyectofinal.utils.ICrud;
import proyectofinal.view.VistaProductos;

/**
 *
 * @author Juan Ruiz
 */
public class ProductoController implements ActionListener{

    private VistaProductos vista;
    
    private ICrud<Producto> productoDao = new ProductoDao();
    
    private Producto producto;
    
    private DefaultTableModel modelo = new DefaultTableModel();
    
    private static int cantidadParaAseo = 0;
    private static int cantidadParaMascota = 0;
    private static int cantidadParaViveres = 0;
    private static int cantidadParaOtros = 0;
    private static int cantidadParaPapeleria = 0;
    
    //public static int 
    
    public ProductoController(VistaProductos vista){
        this.vista= vista;
        desactivarOActivarCampos(false);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.txtNombre.addActionListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listarProductos(this.vista.tablaProductos);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnNuevo){
            vista.btnGuardar.setEnabled(true);
            vista.btnCancelar.setEnabled(true);
            vista.btnNuevo.setEnabled(false);
            desactivarOActivarCampos(true);
            vista.txtNombre.setFocusable(true);
            vista.txtNombre.requestFocus();
        }
        
        if(e.getSource() == vista.btnGuardar){
            if(!validarCampos()){
                JOptionPane.showMessageDialog(vista, "Hay errores en los inputs, por favor revisa", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                agregarProducto();
                limpiarCampos();
                desactivarOActivarCampos(false);
                vista.btnCancelar.setEnabled(false);
                vista.btnGuardar.setEnabled(false);
                vista.btnNuevo.setEnabled(true);
                listarProductos(vista.tablaProductos);
            }
        }
        
        if(e.getSource() == vista.btnCancelar){
            if(!vista.btnEditar.isEnabled() && !vista.btnEliminar.isEnabled()){
                desactivarOActivarCampos(false);
                limpiarCampos();
                vista.btnGuardar.setEnabled(false);
                vista.btnNuevo.setEnabled(true);
            }
            if(vista.btnEditar.isEnabled() && vista.btnEliminar.isEnabled()){
                desactivarOActivarCampos(false);
                limpiarCampos();
                vista.btnNuevo.setEnabled(true);
                vista.btnEditar.setEnabled(false);
                vista.btnEliminar.setEnabled(false);
            }
            vista.btnCancelar.setEnabled(false);
        }
        
        if(e.getSource() == vista.btnSeleccionar){
            int fila = vista.tablaProductos.getSelectedRow();
            System.out.println(fila);
            if(fila >= 0){
                obtenerElSeleccionado(fila);
                desactivarOActivarCampos(true);
                vista.btnNuevo.setEnabled(false);
                vista.btnGuardar.setEnabled(false);
                vista.btnEditar.setEnabled(true);
                vista.btnEliminar.setEnabled(true);
                vista.btnCancelar.setEnabled(true);
            }else{
                JOptionPane.showMessageDialog(vista, "No seleccionaste ninguna fila", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if(e.getSource() == vista.btnEditar){
            validarCampos();
            editarProducto();
            limpiarCampos();
            desactivarOActivarCampos(false);
            vista.btnCancelar.setEnabled(false);
            vista.btnEditar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
            vista.btnNuevo.setEnabled(true);
            listarProductos(vista.tablaProductos);
        }
        
        if(e.getSource() == vista.btnEliminar){
            eliminarProducto();
            limpiarCampos();
            desactivarOActivarCampos(false);
            vista.btnCancelar.setEnabled(false);
            vista.btnEditar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
            vista.btnNuevo.setEnabled(true);
            listarProductos(vista.tablaProductos);
        }
    }
    
    private void listarProductos(JTable tabla){
        cantidadParaAseo = 0;
        cantidadParaMascota = 0;
        cantidadParaViveres = 0;
        cantidadParaOtros = 0;
        cantidadParaPapeleria = 0;
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        List<Producto> lista = productoDao.leerTodos();
        Object[] object = new Object[6];
        DecimalFormat df = new DecimalFormat("0.00");
        lista.forEach(producto -> {
            String tipo = producto.getClass().getName().substring(producto.getClass().getName().lastIndexOf(".") + 1);
            calcularCantidadPorTipo(tipo);
            object[0] = producto.getNombre();
            object[1] = tipo;
            object[2] = producto.getNumeroDeUnidades();
            object[3] = producto.getValorUnitario();
            object[4] = df.format(producto.getValorConIVA());
            object[5] = df.format(producto.getValorTotal());
            modelo.addRow(object);
        });
        this.vista.repaint();
        vista.tablaProductos.setModel(modelo);
    }
    
    private void calcularCantidadPorTipo(String tipo){
        switch(tipo){
            case "Aseo" -> cantidadParaAseo++;
            case "Mascotas" -> cantidadParaMascota++;
            case "Otro" -> cantidadParaOtros++;
            case "Papeleria" -> cantidadParaPapeleria++;
            case "Viveres" -> cantidadParaViveres++;
        }
    }
    
    private void desactivarOActivarCampos(boolean estatus){
        this.vista.txtNombre.setEnabled(estatus);
        this.vista.txtNumeroUnidades.setEnabled(estatus);
        this.vista.txtValorUnitario.setEnabled(estatus);
        this.vista.cbTipo.setEnabled(estatus);
    }
    
    private void obtenerElSeleccionado(int fila){
        producto = productoDao.obtener(fila);
        this.vista.txtId.setText(String.valueOf(fila));
        this.vista.txtNombre.setText(producto.getNombre());
        this.vista.txtNumeroUnidades.setText(Integer.toString(producto.getNumeroDeUnidades()));
        this.vista.txtValorUnitario.setText(Double.toString(producto.getValorUnitario()));
        this.vista.cbTipo.setSelectedItem(producto.getClass().getName().substring(producto.getClass().getName().lastIndexOf(".") + 1));
    }
    
    private void limpiarCampos(){
        this.vista.txtNombre.setText("");
        this.vista.txtNumeroUnidades.setText("");
        this.vista.txtValorUnitario.setText("");
    }
    
    private void setearElTipoDeProducto(String tipo){
        switch(tipo){
            case "Aseo" -> producto = new Aseo();
            case "Mascotas" -> producto = new Mascotas();
            case "Otro" -> producto = new Otro();
            case "Papeleria" -> producto = new Papeleria();
            case "Viveres" -> producto = new Viveres();
        }
    }
    
    private void setearElProducto(){
        String nombre = this.vista.txtNombre.getText();
        int numeroDeUnidades = Integer.parseInt(this.vista.txtNumeroUnidades.getText());
        double valorUnitario = Double.parseDouble(this.vista.txtValorUnitario.getText());
        String tipo = this.vista.cbTipo.getSelectedItem().toString();
        setearElTipoDeProducto(tipo);
        producto.setNombre(nombre);
        producto.setNumeroDeUnidades(numeroDeUnidades);
        producto.setValorUnitario(valorUnitario);
    }
    
    private boolean validarCampos(){
        String nombre = this.vista.txtNombre.getText();
        String numeroDeUnidades = this.vista.txtNumeroUnidades.getText();
        String valorUnitario = this.vista.txtValorUnitario.getText();
        
        if (nombre.isBlank() || nombre.isEmpty()) {
            return false;
        }
        if(!numeroDeUnidades.matches("\\d*$") || numeroDeUnidades.isEmpty()){
            return false;
        }
        if(!valorUnitario.matches("^\\d+(.\\d+)?$") || valorUnitario.isEmpty()){
            return false;
        }
        if(Double.parseDouble(valorUnitario) < 0.0){
            return false;
        }
        if(Integer.parseInt(numeroDeUnidades) < 0){
            return false;
        }
        return true;
    }
    
    private void agregarProducto(){
        setearElProducto();
        productoDao.agregar(producto);
    }
    
    private void editarProducto(){
        setearElProducto();
        productoDao.editar(Integer.parseInt(vista.txtId.getText()), producto);
    }
    
    private void eliminarProducto(){
        productoDao.eliminar(Integer.parseInt(vista.txtId.getText()));
    }

    public static int getCantidadParaAseo() {
        return cantidadParaAseo;
    }

    public static int getCantidadParaMascota() {
        return cantidadParaMascota;
    }

    public static int getCantidadParaViveres() {
        return cantidadParaViveres;
    }

    public static int getCantidadParaOtros() {
        return cantidadParaOtros;
    }

    public static int getCantidadParaPapeleria() {
        return cantidadParaPapeleria;
    }
}
