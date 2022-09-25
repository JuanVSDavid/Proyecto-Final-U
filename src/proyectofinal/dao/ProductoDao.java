/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.dao;

import java.util.ArrayList;
import java.util.List;
import proyectofinal.models.Producto;
import proyectofinal.utils.ICrud;

/**
 *
 * @author Juan Ruiz
 */
public class ProductoDao implements ICrud<Producto> {
    
    private List<Producto> productos;
    
    public ProductoDao(){
        productos = new ArrayList<>();
    }

    @Override
    public void eliminar(int id) {
        productos.remove(id);
    }

    @Override
    public List<Producto> leerTodos() {
        return productos;
    }

    @Override
    public void agregar(Producto item) {
        productos.add(item);
    }

    @Override
    public void editar(Integer id, Producto item) {
        productos.set(id, item);
    }

    @Override
    public Producto obtener(Integer id) {
        return productos.get(id);
    }
    
}
