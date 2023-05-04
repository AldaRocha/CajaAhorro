
package org.utl.dsm.cajaahorro.rest;

import com.google.gson.Gson;
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
import org.utl.dsm.cajaahorro.core.ControllerCreditoOtorgado;
import org.utl.dsm.cajaahorro.model.CreditoOtorgado;
import org.utl.dsm.cajaahorro.model.PresupuestoCredito;

/**
 *
 * @author rocha
 */
@Path("credito")
public class RESTCreditoOtorgado{
    @Path("aceptado")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response aceptado(@FormParam("datos") @DefaultValue("") String datos){
        String out="";
        boolean r=false;
        Gson gson=new Gson();
        PresupuestoCredito pc=new PresupuestoCredito();
        ControllerCreditoOtorgado cco=new ControllerCreditoOtorgado();
        pc=gson.fromJson(datos, PresupuestoCredito.class);
        System.out.println("pc = " + pc);
        r=cco.insertarCreditoOtorgado(pc);
        if(r){
            out="""
                {"result":"correcto"}
                """;
        } else{
            out="""
                {"error":"psss... error"}
                """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllOtorgados")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllOtorgados(@QueryParam("filtro") @DefaultValue("") String filtro){
        String out="";
        Gson gson=new Gson();
        ControllerCreditoOtorgado cco=new ControllerCreditoOtorgado();
        List<CreditoOtorgado> creditoOtorgados=new ArrayList<>();
        
        try{
            creditoOtorgados=cco.getAll(filtro);
            out=gson.toJson(creditoOtorgados);
        } catch(Exception e){
            e.printStackTrace();
            out="{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
