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
public abstract class Producto {
    
    private String nombre;
    private Integer numeroDeUnidades;
    private Double valorUnitario;

    public Producto() {
    }

    public Producto(String nombre, Integer numeroDeUnidades, Double valorUnitario) {
        this.nombre = nombre;
        this.numeroDeUnidades = numeroDeUnidades;
        this.valorUnitario = valorUnitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroDeUnidades() {
        return numeroDeUnidades;
    }

    public void setNumeroDeUnidades(Integer numeroDeUnidades) {
        this.numeroDeUnidades = numeroDeUnidades;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    abstract public double getIVA();
    
    public double getValorConIVA(){
        return this.valorUnitario + this.getIVA();
    }
    
    public double getValorTotal(){
        return getValorConIVA() * this.numeroDeUnidades;
    }
    
}
