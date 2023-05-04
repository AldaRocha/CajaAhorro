
package org.utl.dsm.cajaahorro.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.cajaahorro.core.ControllerPresupuestoCredito;
import org.utl.dsm.cajaahorro.model.PresupuestoCredito;

/**
 *
 * @author rocha
 */
@Path("presupuesto")
public class RESTPresupuestoCredito{
    @Path("cotizar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response cotizar(@FormParam("datos") @DefaultValue("") String datos){
        String out="";
        Gson gson=new Gson();
        PresupuestoCredito pc=new PresupuestoCredito();
        ControllerPresupuestoCredito cpc=new ControllerPresupuestoCredito();
        
        try{
            pc=gson.fromJson(datos, PresupuestoCredito.class);
            cpc.analizar(pc);
            if(pc.getIdPresupuestoCredito() == 0){
                cpc.insertarPresupuesto(pc);
            } else{
                cpc.actualizarPresupuesto(pc);
            }
            out=gson.toJson(pc);
        } catch(JsonParseException jpe){
            jpe.printStackTrace();
            out="""
                {"exception":"Formato JSON de Datos Incorrectos."}
                """;
        } catch(Exception e){
            e.printStackTrace();
            out="""
                {"exception":"%s"}
                """;
            out=String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllPresupuesto")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllPresupuesto(@QueryParam("filtro") @DefaultValue("") String filtro){
        String out="";
        Gson gson=new Gson();
        ControllerPresupuestoCredito cpc=new ControllerPresupuestoCredito();
        List<PresupuestoCredito> presupuestoCredito=new ArrayList<>();
        
        try{
            presupuestoCredito=cpc.getAll(filtro);
            out=gson.toJson(presupuestoCredito);
        } catch(Exception e){
            e.printStackTrace();
            out="{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
