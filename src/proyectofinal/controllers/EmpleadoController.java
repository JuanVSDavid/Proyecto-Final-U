/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.controllers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectofinal.dao.EmpleadoDao;
import proyectofinal.models.Empleado;
import proyectofinal.utils.ICrud;
import proyectofinal.view.VistaEmpleados;

/**
 *
 * @author Juan Ruiz
 */
public class EmpleadoController implements ActionListener {
    
    private VistaEmpleados vista;
    
    private ICrud<Empleado> empleadoDao = new EmpleadoDao();
    
    private Empleado empleado = new Empleado();
    
    private DefaultTableModel modelo = new DefaultTableModel();
    
    private static int cantidadDeDiurnos = 0;
    
    private static int cantidadDeNocturnos = 0;
    
    public EmpleadoController(VistaEmpleados vista){
        this.vista = vista;
        desactivarOActivarCampos(false);
        listarEmpleados(vista.tablaEmpleados);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.txtNombre.addActionListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
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
                agregarEmpleado();
                limpiarCampos();
                desactivarOActivarCampos(false);
                vista.btnCancelar.setEnabled(false);
                vista.btnGuardar.setEnabled(false);
                vista.btnNuevo.setEnabled(true);
                listarEmpleados(vista.tablaEmpleados);
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
            int fila = vista.tablaEmpleados.getSelectedRow();
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
            editarEmpleado();
            limpiarCampos();
            desactivarOActivarCampos(false);
            vista.btnCancelar.setEnabled(false);
            vista.btnEditar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
            vista.btnNuevo.setEnabled(true);
            listarEmpleados(vista.tablaEmpleados);
        }
        
        if(e.getSource() == vista.btnEliminar){
            eliminarEmpleado();
            limpiarCampos();
            desactivarOActivarCampos(false);
            vista.btnCancelar.setEnabled(false);
            vista.btnEditar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
            vista.btnNuevo.setEnabled(true);
            listarEmpleados(vista.tablaEmpleados);
        }
    }
    
    private void editarEmpleado(){
        setearElEmpleado();
        empleadoDao.editar(Integer.parseInt(vista.txtId.getText()), empleado);
    }
    
    private void eliminarEmpleado(){
        empleadoDao.eliminar(Integer.parseInt(vista.txtId.getText()));
    }
    
    private void listarEmpleados(JTable tabla){
        cantidadDeDiurnos = 0;
        cantidadDeNocturnos = 0;
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.getDataVector().removeAllElements();
        modelo.fireTableDataChanged();
        List<Empleado> lista = empleadoDao.leerTodos();
        Object[] object = new Object[8];
        lista.forEach(empleado -> {
            System.out.println(empleado);
            Map<String, Float> descuentos = empleado.obtenerDescuentos();
            object[0] = empleado.getNombres();
            object[1] = empleado.getApellidos();
            object[2] = empleado.getNumeroDeIdentificacion();
            object[3] = empleado.getEdad();
            object[4] = empleado.getJornada();
            calcularCantidadJornada(empleado.getJornada());
            object[5] = empleado.getTiempoTrabajadoEnYears();
            object[6] = Math.round(descuentos.get("tiendaCompensar") * 100) + "%";
            object[7] = Math.round(descuentos.get("centrosRecreacionales") * 100) + "%";
            modelo.addRow(object);
        });
        this.vista.repaint();
        vista.tablaEmpleados.setModel(modelo);
    }
    
    private void calcularCantidadJornada(String jornada){
        if("diurno".equals(jornada)){
            cantidadDeDiurnos++;
            return;
        }
        cantidadDeNocturnos++;
    }
    
    private void setearElEmpleado(){
        empleado = new Empleado();
        String nombre = vista.txtNombre.getText();
        String edad = vista.txtEdad.getText();
        String identificacion = vista.txtIdentificacion.getText();
        String apellidos = vista.txtApellidos.getText();
        String TiempoEnLaEmpresa = vista.txtTiempoEnEmpresa.getText();
        String jornada = vista.cbJornada.getSelectedItem().toString();
        empleado.setNombres(nombre);
        empleado.setApellidos(apellidos);
        empleado.setEdad(Integer.parseInt(edad));
        empleado.setNumeroDeIdentificacion(identificacion);
        empleado.setTiempoTrabajadoEnYears(Integer.parseInt(TiempoEnLaEmpresa));
        empleado.setJornada(jornada);
    }
    
    private void agregarEmpleado(){
        setearElEmpleado();
        empleadoDao.agregar(empleado);
    }
    
    private void limpiarCampos(){
        vista.txtApellidos.setText("");
        vista.txtEdad.setText("");
        vista.txtIdentificacion.setText("");
        vista.txtTiempoEnEmpresa.setText("");
        vista.txtNombre.setText("");
        vista.txtId.setText("");
    }
    
    private void desactivarOActivarCampos(boolean estatus){
        vista.txtApellidos.setEnabled(estatus);
        vista.txtEdad.setEnabled(estatus);
        vista.txtIdentificacion.setEnabled(estatus);
        vista.txtTiempoEnEmpresa.setEnabled(estatus);
        vista.txtNombre.setEnabled(estatus);
        vista.cbJornada.setEnabled(estatus);
    }
    
    private boolean validarCampos(){
        String nombre = vista.txtNombre.getText();
        String edad = vista.txtEdad.getText();
        String identificacion = vista.txtIdentificacion.getText();
        String apellidos = vista.txtApellidos.getText();
        String TiempoEnLaEmpresa = vista.txtTiempoEnEmpresa.getText();
        
        if (nombre.isBlank() || nombre.isEmpty()) {
            return false;
        }
        if (apellidos.isBlank() || apellidos.isEmpty()) {
            return false;
        }
        if (!edad.matches("\\d*$") || edad.isEmpty()) {
            return false;
        }
        if (!(0 < Integer.parseInt(edad) && Integer.parseInt(edad) <= 100)) {
            return false;
        }
        if (!identificacion.matches("\\d*$") || identificacion.isEmpty()) {
            return false;
        }
        if(!TiempoEnLaEmpresa.matches("\\d*$") || TiempoEnLaEmpresa.isEmpty()){
            return false;
        }
        if(Integer.parseInt(TiempoEnLaEmpresa) < 0){
            return false;
        } 
        return true;
    }
    
    private void obtenerElSeleccionado(Integer fila){
        empleado = empleadoDao.obtener(fila);
        vista.txtId.setText(fila.toString());
        vista.txtApellidos.setText(empleado.getApellidos());
        vista.txtEdad.setText(empleado.getEdad().toString());
        vista.txtIdentificacion.setText(empleado.getNumeroDeIdentificacion());
        vista.txtTiempoEnEmpresa.setText(String.valueOf(empleado.getTiempoTrabajadoEnYears()));
        vista.txtNombre.setText(empleado.getNombres());
        vista.cbJornada.setSelectedItem(empleado.getJornada());
    }

    public static int getCantidadDeDiurnos() {
        return cantidadDeDiurnos;
    }

    public static int getCantidadDeNocturnos() {
        return cantidadDeNocturnos;
    }
    
}
