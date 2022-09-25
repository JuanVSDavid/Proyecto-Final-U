/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.utils;

import java.util.List;

/**
 *
 * @author Juan Ruiz
 */
public interface ICrud<T> {
    
    public void eliminar(int id);
    
    public List<T> leerTodos();
    
    public void agregar(T item);
    
    public void editar(Integer id, T item);
    
    public T obtener(Integer id);
}
