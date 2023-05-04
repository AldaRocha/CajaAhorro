
package org.utl.dsm.cajaahorro.model;

/**
 *
 * @author rocha
 */
public class Cliente{
    private int idCliente;
    private String nombre;
    private String fechaNacimiento;
    private char genero;
    private int numeroHijos;
    private double ingresoMensual;

    public Cliente(int idCliente, String nombre, String fechaNacimiento, char genero, int numeroHijos, double ingresoMensual){
        this.idCliente=idCliente;
        this.nombre=nombre;
        this.fechaNacimiento=fechaNacimiento;
        this.genero=genero;
        this.numeroHijos=numeroHijos;
        this.ingresoMensual=ingresoMensual;
    }

    public Cliente(){
    }

    public int getIdCliente(){
        return idCliente;
    }

    public void setIdCliente(int idCliente){
        this.idCliente=idCliente;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento=fechaNacimiento;
    }

    public char getGenero(){
        return genero;
    }

    public void setGenero(char genero){
        this.genero=genero;
    }

    public int getNumeroHijos(){
        return numeroHijos;
    }

    public void setNumeroHijos(int numeroHijos){
        this.numeroHijos=numeroHijos;
    }

    public double getIngresoMensual(){
        return ingresoMensual;
    }

    public void setIngresoMensual(double ingresoMensual){
        this.ingresoMensual=ingresoMensual;
    }
}
