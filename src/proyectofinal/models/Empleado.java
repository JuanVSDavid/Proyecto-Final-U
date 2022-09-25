/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan Ruiz
 */
public class Empleado {
    private String nombres;
    private String apellidos;
    private String numeroDeIdentificacion;
    private Integer edad;
    private String jornada;
    private int tiempoTrabajadoEnYears;
    
    public static int cantidadDiurnos;
    public static int cantidadNocturna;

    public Empleado() {
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDeIdentificacion() {
        return numeroDeIdentificacion;
    }

    public void setNumeroDeIdentificacion(String numeroDeIdentificacion) {
        this.numeroDeIdentificacion = numeroDeIdentificacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public int getTiempoTrabajadoEnYears() {
        return tiempoTrabajadoEnYears;
    }

    public void setTiempoTrabajadoEnYears(int tiempoTrabajadoEnYears) {
        this.tiempoTrabajadoEnYears = tiempoTrabajadoEnYears;
    }
    
    public Map<String, Float> obtenerDescuentos(){
        return calcularDescuentos();
    }
    
    private Map<String, Float> calcularDescuentos(){
        Map<String, Float> descuentos = new HashMap<>();
        descuentos.put("tiendaCompensar", Float.NaN);
        descuentos.put("centrosRecreacionales", Float.NaN);
        if(this.tiempoTrabajadoEnYears < 1){
            descuentos.put("tiendaCompensar", 0.15F);
            descuentos.put("centrosRecreacionales", 0.2F);
        }
        if(this.tiempoTrabajadoEnYears >= 1 && this.tiempoTrabajadoEnYears <= 5){
            descuentos.put("tiendaCompensar", 0.3F);
            descuentos.put("centrosRecreacionales", 0.3F);
        }
        if(this.tiempoTrabajadoEnYears >= 6){
            descuentos.put("tiendaCompensar", 0.5F);
            descuentos.put("centrosRecreacionales", 0.6F);
        }
        return descuentos;   
    }

    @Override
    public String toString() {
        return "Empleado{" + "nombres=" + nombres + ", apellidos=" + apellidos + ", numeroDeIdentificacion=" + numeroDeIdentificacion + ", edad=" + edad + ", jornada=" + jornada + ", tiempoTrabajadoEnYears=" + tiempoTrabajadoEnYears + '}';
    }
}
