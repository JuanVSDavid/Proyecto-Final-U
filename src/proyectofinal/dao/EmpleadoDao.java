/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.dao;

import java.util.ArrayList;
import java.util.List;
import proyectofinal.models.Empleado;
import proyectofinal.utils.ICrud;

/**
 *
 * @author Juan Ruiz
 */
public class EmpleadoDao implements ICrud<Empleado> {
    
    private List<Empleado> empleados;
    
    public EmpleadoDao(){
       empleados = new ArrayList<>();
    }

    @Override
    public void eliminar(int id) {
        empleados.remove(id);
    }

    @Override
    public List<Empleado> leerTodos() {
        return empleados;
    }

    @Override
    public void agregar(Empleado item) {
        empleados.add(item);
    }

    @Override
    public void editar(Integer id, Empleado item) {
        empleados.set(id, item);
    }

    @Override
    public Empleado obtener(Integer id) {
        return empleados.get(id);
    }
    
}
