
package org.utl.dsm.cajaahorro.model;

/**
 *
 * @author rocha
 */
public class PresupuestoCredito{
    private int idPresupuestoCredito;
    private double montoCredito;
    private String resultadoAnalisis;
    private Cliente cliente;

    public PresupuestoCredito(int idPresupuestoCredito, double montoCredito, String resultadoAnalisis, Cliente cliente){
        this.idPresupuestoCredito=idPresupuestoCredito;
        this.montoCredito=montoCredito;
        this.resultadoAnalisis=resultadoAnalisis;
        this.cliente=cliente;
    }

    public PresupuestoCredito(){
    }

    public int getIdPresupuestoCredito(){
        return idPresupuestoCredito;
    }

    public void setIdPresupuestoCredito(int idPresupuestoCredito){
        this.idPresupuestoCredito=idPresupuestoCredito;
    }

    public double getMontoCredito(){
        return montoCredito;
    }

    public void setMontoCredito(double montoCredito){
        this.montoCredito=montoCredito;
    }

    public String getResultadoAnalisis(){
        return resultadoAnalisis;
    }

    public void setResultadoAnalisis(String resultadoAnalisis){
        this.resultadoAnalisis=resultadoAnalisis;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }
}
