
let presupuestos=[];
let otorgados=[];

function inicializar(){
    pedirPresupuestos();
    configureTableFilter(document.getElementById("txtBuscarPresupuesto"), document.getElementById("tablaCreditos"));

    pedirOtorgados();
    configureTableFilter(document.getElementById("txtBuscarOtorgados"), document.getElementById("tablaCreditosOtorgados"));
}

function verConsultas(){
    document.getElementById("formulario").classList.add("d-none");
    document.getElementById("creditosOtorgados").classList.add("d-none");
    document.getElementById("consultas").classList.remove("d-none");
}

function creditosOtorgados(){
    document.getElementById("formulario").classList.add("d-none");
    document.getElementById("consultas").classList.add("d-none");
    document.getElementById("creditosOtorgados").classList.remove("d-none");
}

function evaluarSolicitud(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    localStorage.removeItem("currentRequest");
    console.log("Se elimino el local storage");
    
    let datos=null;
    let params=null;
    
    let presupuestoCredito=new Object();
    presupuestoCredito.cliente=new Object();
    
    presupuestoCredito.cliente.nombre=document.getElementById("txtNombre").value;
    presupuestoCredito.cliente.fechaNacimiento=document.getElementById("txtFechaNacimiento").value;
    presupuestoCredito.cliente.genero=document.getElementById("cmbGenero").value;
    presupuestoCredito.cliente.numeroHijos=document.getElementById("txtNumeroHijos").value;
    presupuestoCredito.cliente.ingresoMensual=document.getElementById("txtIngresoMensual").value;
    
    presupuestoCredito.montoCredito=document.getElementById("txtMontoCredito").value;
    presupuestoCredito.resultadoAnalisis="";
    
    datos={
        datos:JSON.stringify(presupuestoCredito)
    };
    
    console.log(datos);
    
    params=new URLSearchParams(datos);
    
    console.log(params);
    
    fetch("api/presupuesto/cotizar",{
        method:"POST",
        headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{                                                        
                            try{                                
                                localStorage.setItem('currentRequest', JSON.stringify(data));
                                alert("La solicitud se realizo con exito");                                
                            } catch(e){
                                alert("Error al guardar la respuesta en el almacenamiento local");
                            }
                    })
                            .catch(error=>{
                                alert("Error del servidor, intentar mas tarde.");
                                console.error(error.message.toString());
                            });
    document.querySelector(".spinner-container").classList.add("d-none");
}

function cargarPresupuesto(){
    let currentRequest=localStorage.getItem("currentRequest");
    let data=JSON.parse(currentRequest);

    if(currentRequest != null){
        let respuestas=document.querySelectorAll(".respuesta");
        respuestas.forEach(function(elemento){
            elemento.classList.remove("d-none");
        });

        document.getElementById("spnNombre").innerHTML=data.cliente.nombre;
        document.getElementById("spnFechaNacimiento").innerHTML=data.cliente.fechaNacimiento;
        document.getElementById("spnGenero").innerHTML=data.cliente.genero;
        document.getElementById("spnNumeroHijos").innerHTML=data.cliente.numeroHijos;
        document.getElementById("spnIngresoMensual").innerHTML=data.cliente.ingresoMensual;
        document.getElementById("spnMontoCredito").innerHTML=data.montoCredito;
        if (typeof data.resultadoAnalisis==="string" && data.resultadoAnalisis==="Aprobado"){
            document.querySelector(".colorletra").classList.add("text-info");
        }else{
            document.querySelector(".colorletra").classList.add("text-danger");
        }
        document.getElementById("spnResultado").innerHTML=data.resultadoAnalisis;
    }else{
        console.log("Bienvendio");
    }
}

function habilitarBotones(){
    var inputs=document.querySelectorAll("input");
    var todosVacios=true;
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].value.trim()!==""){
            todosVacios=false;
            break;
        }
    }
    if(todosVacios){
        document.querySelector("#btnLimpiar").disabled=true;
    } else{
        document.querySelector("#btnLimpiar").disabled=false;
    }
}

function aceptarCredito(){
    const currentRequest=JSON.parse(localStorage.getItem('currentRequest'));
    console.log(currentRequest);
    
    const params=new URLSearchParams({        
        datos:JSON.stringify(currentRequest)
    });
    
    console.log(params);
    
    fetch("api/credito/aceptado",{
        method:"POST",
        headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        alert("El credito"+currentRequest.idPresupuestoCredito+" fue desembolsado con exito");
                    })
                            .catch(error=>{
                                alert("Error del servidor, intentar mas tarde.");
                                console.error(error.message.toString());
                            });
}

function limpiar(){
    document.getElementById("txtNombre").value="";
    document.getElementById("txtFechaNacimiento").value="";
    document.getElementById("txtNumeroHijos").value="";
    document.getElementById("txtIngresoMensual").value="";
    document.getElementById("txtMontoCredito").value="";
    document.querySelector("#btnLimpiar").disabled=true;
}

function llenarFormulario(){
    document.getElementById("consultas").classList.add("d-none");
    document.getElementById("creditosOtorgados").classList.add("d-none");
    document.getElementById("formulario").classList.remove("d-none");
}

function pedirPresupuestos(){
    fetch("api/presupuesto/getAllPresupuesto")
            .then(response=>{
                return response.json();
            })
                    .then(function(data){
                        llenarTablaPresupuestos(data);
                    })
                            .catch(error=>{
                                alert("Error del servidor, intentar mas tarde.");
                                console.error(error.message.toString());
                            });
}

function llenarTablaPresupuestos(data){
    let cuerpo="";
    presupuestos=data;
    presupuestos.forEach(function(presupuesto){
        let datos='<tr onclick="seleccionarCredito('+presupuestos.indexOf(presupuesto)+');">'+
                  '<td>'+presupuesto.idPresupuestoCredito+'</td>'+
                  '<td>'+presupuesto.cliente.nombre+'</td>'+
                  '<td>'+presupuesto.cliente.ingresoMensual+'</td>'+
                  '<td>'+presupuesto.montoCredito+'</td>'+
                  '<td>'+presupuesto.resultadoAnalisis+'</td>'+
                  '</tr>';
        cuerpo+=datos;
    });
    document.getElementById("datosSolicitudes").innerHTML=cuerpo;
}

function seleccionarCredito(index){
    console.log(presupuestos[index]);
    localStorage.setItem('currentRequest', JSON.stringify(presupuestos[index]));
    llenarFormulario();
    cargarPresupuesto();
}

function pedirOtorgados(){
    fetch("api/credito/getAllOtorgados")
            .then(response=>{
                return response.json();
            })
                    .then(function(data){
                        llenarTablaOtorgados(data);
                    })
                            .catch(error=>{
                                alert("Error del servidor, intentar mas tarde.");
                                console.error(error.message.toString());
                            });
}

function llenarTablaOtorgados(data){
    let cuerpo="";
    otorgados=data;
    otorgados.forEach(function(otorgado){
        let datos='<tr onclick="seleccionarOtorgado('+otorgados.indexOf(otorgado)+');">'+
                  '<td>'+otorgado.idCreditoOtorgado+'</td>'+
                  '<td>'+otorgado.presupuestoCredito.cliente.nombre+'</td>'+
                  '<td>'+otorgado.presupuestoCredito.montoCredito+'</td>'+                  
                  '</tr>';
        cuerpo+=datos;
    });
    document.getElementById("datosCreditosOtorgados").innerHTML=cuerpo;
}

function seleccionarOtorgado(index){
    console.log(otorgados[index]);
    localStorage.setItem('currentRequest', JSON.stringify(otorgados[index]));
    llenarFormulario();
    cargarOtorgado();
}

function cargarOtorgado(){
    let currentRequest=localStorage.getItem("currentRequest");
    let data=JSON.parse(currentRequest);

    if(currentRequest != null){
        let respuestas=document.querySelectorAll(".respuesta");
        respuestas.forEach(function(elemento){
            elemento.classList.remove("d-none");
        });

        document.getElementById("spnNombre").innerHTML=data.presupuestoCredito.cliente.nombre;
        document.getElementById("spnFechaNacimiento").innerHTML=data.presupuestoCredito.cliente.fechaNacimiento;
        document.getElementById("spnGenero").innerHTML=data.presupuestoCredito.cliente.genero;
        document.getElementById("spnNumeroHijos").innerHTML=data.presupuestoCredito.cliente.numeroHijos;
        document.getElementById("spnIngresoMensual").innerHTML=data.presupuestoCredito.cliente.ingresoMensual;
        document.getElementById("spnMontoCredito").innerHTML=data.presupuestoCredito.montoCredito;
        if (typeof data.presupuestoCredito.resultadoAnalisis==="string" && data.presupuestoCredito.resultadoAnalisis==="Aprobado"){
            document.querySelector(".colorletra").classList.add("text-info");
        }else{
            document.querySelector(".colorletra").classList.add("text-danger");
        }
        document.getElementById("spnResultado").innerHTML="Desembolsado";
    }else{
        console.log("Bienvendio");
    }
}
