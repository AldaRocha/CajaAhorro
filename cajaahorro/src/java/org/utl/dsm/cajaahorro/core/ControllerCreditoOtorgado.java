
package org.utl.dsm.cajaahorro.core;

import org.utl.dsm.cajaahorro.model.CreditoOtorgado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.dsm.cajaahorro.model.Cliente;
import org.utl.dsm.cajaahorro.model.PresupuestoCredito;

/**
 *
 * @author rocha
 */
public class ControllerCreditoOtorgado{
    public boolean insertarCreditoOtorgado(PresupuestoCredito pc){
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt =null;
        boolean r=false;
        
        try{
            conn.setAutoCommit(false);
            String sql="INSERT INTO creditoOtorgado(idPresupuestoCredito) VALUES("+pc.getIdPresupuestoCredito()+")";
            stmt=conn.createStatement();
            stmt.execute(sql);
            conn.commit();
            conn.setAutoCommit(true);
            stmt.close();
            conn.close();
            connMySQL.close();
            r=true;
        } catch(SQLException sqle){
            Logger.getLogger(ControllerCreditoOtorgado.class.getName()).log(Level.SEVERE, null, sqle);
            try{
                conn.rollback();
                conn.setAutoCommit(true);
                stmt.close();
                conn.close();
                connMySQL.close();
                r=false;
            } catch(SQLException sqle2){
                Logger.getLogger(ControllerCreditoOtorgado.class.getName()).log(Level.SEVERE, null, sqle2);
                r=false;
            }
        }
        return r;
    }
    
    public List<CreditoOtorgado> getAll(String filtro) throws Exception{
        String sql="SELECT * FROM creditoOtorgado co INNER JOIN presupuestoCredito pc INNER JOIN cliente c WHERE co.idPresupuestoCredito=pc.idPresupuestoCredito AND pc.idCliente=c.idCliente AND c.nombre LIKE '%"+filtro+"%';";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement psmnt=conn.prepareStatement(sql);
        
        ResultSet rs=psmnt.executeQuery();
        
        List<CreditoOtorgado> creditoOtorgados=new ArrayList<>();
        
        while(rs.next())
            creditoOtorgados.add(fillCreditoOtorgado(rs));
        
        rs.close();
        psmnt.close();
        connMySQL.close();
        
        return creditoOtorgados;
    }
    
    private CreditoOtorgado fillCreditoOtorgado(ResultSet rs) throws Exception{
        Cliente c=new Cliente();
        PresupuestoCredito pc=new PresupuestoCredito();
        CreditoOtorgado co=new CreditoOtorgado();
        
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNombre(rs.getString("nombre"));
        c.setFechaNacimiento(rs.getString("fechaNacimiento"));
        c.setGenero(rs.getString("genero").charAt(0));
        c.setNumeroHijos(rs.getInt("numeroHijos"));
        c.setIngresoMensual(rs.getDouble("ingresoMensual"));
        
        pc.setIdPresupuestoCredito(rs.getInt("idPresupuestoCredito"));
        pc.setMontoCredito(rs.getDouble("montoCredito"));
        pc.setResultadoAnalisis(rs.getString("resultadoAnalisis"));
        pc.setCliente(c);
        
        co.setIdCreditoOtorgado(rs.getInt("idCreditoOtorgado"));
        co.setPresupuestoCredito(pc);
        
        return co;
    }
}
