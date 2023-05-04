
package org.utl.dsm.cajaahorro.model;

/**
 *
 * @author rocha
 */
public class CreditoOtorgado{
    private int idCreditoOtorgado;
    private PresupuestoCredito presupuestoCredito;

    public CreditoOtorgado(int idCreditoOtorgado, PresupuestoCredito presupuestoCredito){
        this.idCreditoOtorgado=idCreditoOtorgado;
        this.presupuestoCredito=presupuestoCredito;
    }

    public CreditoOtorgado(){
    }

    public int getIdCreditoOtorgado(){
        return idCreditoOtorgado;
    }

    public void setIdCreditoOtorgado(int idCreditoOtorgado){
        this.idCreditoOtorgado=idCreditoOtorgado;
    }

    public PresupuestoCredito getPresupuestoCredito(){
        return presupuestoCredito;
    }

    public void setPresupuestoCredito(PresupuestoCredito presupuestoCredito){
        this.presupuestoCredito=presupuestoCredito;
    }
}
