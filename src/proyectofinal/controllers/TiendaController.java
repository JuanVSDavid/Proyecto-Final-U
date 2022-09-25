/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyectofinal.view.VistaEmpleados;
import proyectofinal.view.VistaProductos;
import proyectofinal.view.VistaTiendaCompensar;

/**
 *
 * @author Juan Ruiz
 */
public class TiendaController implements ActionListener{
    
    private VistaTiendaCompensar vistaTC;
    private VistaEmpleados vE;
    private VistaProductos vP;

    public TiendaController(VistaTiendaCompensar vistaTC, VistaEmpleados vE, VistaProductos vP){
        this.vistaTC = vistaTC;
        this.vE = vE;
        this.vP = vP;
        this.vistaTC.menuItemEmpleados.addActionListener(this);
        this.vistaTC.menuEditar.addActionListener(this);
        this.vistaTC.menuItemProductos.addActionListener(this);
        this.vistaTC.desktop.add(vE);
        this.vistaTC.desktop.add(vP);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaTC.menuItemEmpleados){
            if(vP.isVisible()){
                vP.setVisible(false);
            }
            if(!vE.isVisible()){
                vE.setVisible(true);
            }
        }
        if(e.getSource() == vistaTC.menuItemProductos){
            if(vE.isVisible()){
                vE.setVisible(false);
            }
            if(!vP.isVisible()){
                vP.setVisible(true);
            }
        }
    }
    
}
