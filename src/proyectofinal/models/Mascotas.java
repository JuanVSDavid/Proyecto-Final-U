/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.models;

/**
 *
 * @author Juan Ruiz
 */
public class Mascotas extends Producto {

    @Override
    public double getIVA() {
        return super.getValorUnitario() * 0.16F;
    }
    
}
