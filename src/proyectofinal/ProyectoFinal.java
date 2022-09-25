/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import proyectofinal.controllers.EmpleadoController;
import proyectofinal.controllers.ProductoController;
import proyectofinal.controllers.TiendaController;
import proyectofinal.view.VistaEmpleados;
import proyectofinal.view.VistaProductos;
import proyectofinal.view.VistaTiendaCompensar;

/**
 *
 * @author Juan Ruiz
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        VistaTiendaCompensar vTC = new VistaTiendaCompensar();
        VistaProductos vP = new VistaProductos();
        VistaEmpleados vE = new VistaEmpleados();
        TiendaController tC = new TiendaController(vTC, vE, vP);
        EmpleadoController eC = new EmpleadoController(vE);
        ProductoController pC = new ProductoController(vP);
        vTC.setVisible(true);
        vTC.setLocationRelativeTo(null);
    }
    
}
