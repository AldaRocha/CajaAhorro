
package org.utl.dsm.cajaahorro.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.cajaahorro.model.Cliente;
import org.utl.dsm.cajaahorro.model.PresupuestoCredito;

/**
 *
 * @author rocha
 */
public class ControllerPresupuestoCredito{
    public PresupuestoCredito analizar(PresupuestoCredito pc){
        if(pc.getCliente().getNumeroHijos()==0 && pc.getMontoCredito()<=(pc.getCliente().getIngresoMensual()/2)){
            pc.setResultadoAnalisis("Aprobado");
        } else if(pc.getCliente().getNumeroHijos()<=3 && pc.getMontoCredito()<=((30*pc.getCliente().getIngresoMensual())/100)){
            pc.setResultadoAnalisis("Aprobado");
        } else if(pc.getCliente().getNumeroHijos()<=6 && pc.getMontoCredito()<=((15*pc.getCliente().getIngresoMensual())/100)){
            pc.setResultadoAnalisis("Aprobado");
        } else if(pc.getCliente().getNumeroHijos()>6 && pc.getMontoCredito()<=((5*pc.getCliente().getIngresoMensual())/100)){
            pc.setResultadoAnalisis("Aprobado");
        } else{
            pc.setResultadoAnalisis("Rechazado");
        }
        return pc;
    }
    
    public int insertarPresupuesto(PresupuestoCredito pc) throws Exception{
        String sql="{CALL insertarpresupuestoCredito(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        int idClienteGenerado=-1;
        int idPresupuestoCreditoGenerado=-1;
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clsmnt=conn.prepareCall(sql);
        
        clsmnt.setString(1, pc.getCliente().getNombre());
        clsmnt.setString(2, pc.getCliente().getFechaNacimiento());
        clsmnt.setString(3, Character.toString(pc.getCliente().getGenero()));
        clsmnt.setInt(4, pc.getCliente().getNumeroHijos());
        clsmnt.setDouble(5, pc.getCliente().getIngresoMensual());
        
        clsmnt.setDouble(6, pc.getMontoCredito());
        clsmnt.setString(7, pc.getResultadoAnalisis());
        
        clsmnt.registerOutParameter(8, Types.INTEGER);
        clsmnt.registerOutParameter(9, Types.INTEGER);
        
        clsmnt.executeUpdate();
        
        idClienteGenerado=clsmnt.getInt(8);
        idPresupuestoCreditoGenerado=clsmnt.getInt(9);
        
        pc.getCliente().setIdCliente(idClienteGenerado);
        pc.setIdPresupuestoCredito(idPresupuestoCreditoGenerado);
        
        clsmnt.close();
        connMySQL.close();
        
        return idPresupuestoCreditoGenerado;
    }
    
    public void actualizarPresupuesto(PresupuestoCredito pc) throws Exception{
        String sql="{CALL actualizarpresupuestoCredito(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clsmnt=conn.prepareCall(sql);
        
        clsmnt.setString(1, pc.getCliente().getNombre());
        clsmnt.setString(2, pc.getCliente().getFechaNacimiento());
        clsmnt.setString(3, Character.toString(pc.getCliente().getGenero()));
        clsmnt.setInt(4, pc.getCliente().getNumeroHijos());
        clsmnt.setDouble(5, pc.getCliente().getIngresoMensual());
        
        clsmnt.setDouble(6, pc.getMontoCredito());
        clsmnt.setString(7, pc.getResultadoAnalisis());
        
        clsmnt.setInt(8, pc.getCliente().getIdCliente());
        clsmnt.setInt(9, pc.getIdPresupuestoCredito());
        
        clsmnt.executeUpdate();
        
        clsmnt.close();
        connMySQL.close();
    }
    
    public List<PresupuestoCredito> getAll(String filtro) throws Exception{
        String sql="SELECT * FROM presupuestoCredito pc INNER JOIN cliente c ON pc.idCliente=c.idCliente WHERE c.nombre LIKE '%"+filtro+"%' ORDER BY pc.idPresupuestoCredito DESC;";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement psmnt=conn.prepareStatement(sql);
        
        ResultSet rs=psmnt.executeQuery();
        
        List<PresupuestoCredito> presupuestoCreditos=new ArrayList<>();
        
        while(rs.next())
            presupuestoCreditos.add(fillPresupuestoCredito(rs));
        
        rs.close();
        psmnt.close();
        connMySQL.close();
        
        return presupuestoCreditos;
    }
    
    private PresupuestoCredito fillPresupuestoCredito(ResultSet rs) throws Exception{
        Cliente c=new Cliente();
        PresupuestoCredito pc=new PresupuestoCredito();
        
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
        
        return pc;
    }
}
