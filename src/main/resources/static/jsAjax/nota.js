let grupoestudianteNota={id_grupoestudiante:0,estudiante:""};

$(document).ready(inicio);

function inicio(){


	//function para cargar tabla de grupoestudiantes
	reload();
	//metodo para obtener los campos
	$("#datoMateria").change(reload);
	$("#datoGrupo").change(reload);
	$("#dataActividad").change(cargarDataEstudiante);
	
	//seleccionar Id para calificar
	$('body').on('click','.nota', function() {
		selectEstudiante($(this).parent().parent().children('td:eq(0)').text());
	});
	
	$('body').on('click','.BorrarNota',EditAndDrop);
	
	//function para guardar
	$('body').on('click','.CargarNota',SaveAndEdit);
	
	
}

//function para guardar o modificar
function SaveAndEdit(){
	//comparacion();
	//console.log("llego: "+id_nota);
	id_grupoestudiante=$(this).parent().parent().children('td:eq(0)').text();
	nota=$(this).parent().parent().children('td:eq(3)').children('.nota').val();
	observacion=$(this).parent().parent().children('td:eq(4)').children('.observacion').val();
	id_nota=$(this).parent().parent().children('td:eq(3)').children('.idNota:hidden').val();
	console.log(nota,observacion);
	if(nota==null){
		alert("Es necesario campo nota..."+nota);
	}else{
		//alert(id_nota);
		if(id_nota==null)
			guardarNota(id_grupoestudiante,nota,0,observacion);
		else
			guardarNota(id_grupoestudiante,id_nota,nota,observacion);
	}
}

//function para editar o agregar
function EditAndDrop(id_nota){
	console.log(id_nota);
	$(this).parent().parent().children('td:eq(3)').children('.nota').removeAttr("readonly");
	$(this).parent().parent().children('td:eq(4)').children('.observacion').removeAttr("readonly");
	nota=$(this).parent().parent().children('td:eq(3)').children('.nota').val();
	grupestudiante=$(this).parent().parent().children('td:eq(0)').text();
	actividad=$("#dataActividad").val();
	//sustitucionNota(nota,actividad,grupestudiante);
}

//function para habilitar los input
function removeAttr(){
	$(".nota").removeAttr("readonly");
	$(".observacion").removeAttr("readonly");
}


//function para cargar actividad
function cargarActividad(materia){
	materia=$("#datoMateria").val();
	//alert("Cargar; "+materia);
	$.ajax({
		method:"Get",
		url:"/notas/reporteActividad",
		data:{materia:materia},
		success: function (data){
			$("#dataActividad").html("");
			
			for(item of data){
				
				$("#dataActividad").append(""
				+"<option value='"+item.id_actividad+"'>"+item.materia.nombre+": "+item.nombre+"</option>"
				+"");
				
			}
			actividad=$("#dataActividad").val();
			cargarDataEstudiante(actividad);
			
			//alert("Act: "+actividad)
		}
	});
}

//function para los select
function reload(){
	materia = $("#datoMateria").val();
	grupo = $("#datoGrupo").val();
	cargarActividad(materia);
	//cargar Activdades en select
	//alert(actividad);
	cargarDataEstudiante(materia, grupo);
	
}


//function para cargar la tabla dinamica
function cargarDataEstudiante(materia,grupo,actividad) {
	materia = $("#datoMateria").val();
	grupo=$("#datoGrupo").val();
	actividad=$("#dataActividad").val();
	console.log(""+actividad);
	$("#TableGEstu").DataTable({
		"ajax":{
			"method" : "Get",
			"url" : "/notas/reporteEstudiante?"+"materia="+materia+"&grupo="+grupo+"&actividad="+actividad
		},
		
		"columns" :[{
			"data" : "id_estudiante"//,"width": "10%" 
		},{
			"data" : "nombre"//,"width": "25%" 
		},{
			"data" : "apellido"//,"width": "25%" 
		},{
			"data" : "nota"//,"width": "10%" 
		},{
			"data" : "observacion"//,"width": "20%" 
		},{
			"data" : "opcion"//,"width": "10%" 
		}],
		"scrollY":300,
		"language" : {
            "lengthMenu" : "Mostrar _MENU_ ",
            "zeroRecords" : "Datos no encontrados",
            "info" : "Mostar páginas _PAGE_ de _PAGES_",
            "infoEmpty" : "Datos no encontrados",
            "infoFiltered" : "(Filtrados por _MAX_ total registros)",
            "search" : "Buscar:",
            "paginate" : {
                "first" : "Primero",
                "last" : "Anterior",
                "next" : "Siguiente",
                "previous" : "Anterior"
            }
        },
		"bDestroy":"true"
	});
	$.fn.dataTable.ext.errMode = 'throw';
}

//funtion para cargar estudiantes
function selectEstudiante(id_grupoestudiante){
	grupoestudianteNota.id_grupoestudiante=id_grupoestudiante;
	
	$(".id_grupoestudiante").val(id_grupoestudiante);
	id=$(".id_grupoestudiante").val();
	//alert(id);
	console.log(id_grupoestudiante);
}

//function para guardar
function guardarNota(id_grupoestudiante,id_nota,nota,observaciones){
	var actividad=$("#dataActividad").val();
	//var grupoestudiante=$(".id_grupoestudiante").val();
	//var obserInsert=observacion;
	if(nota<=0 || nota>10){
		alert("El valor de la nota ingresado es invalido.");
		nota=null;
	}else{
		nota==nota;
	}
	$.ajax({
		method : "Get",
		url : "/notas/guardar",
		data:{
			id_actividad:actividad,
			id_grupoestudiante:id_grupoestudiante,
			nota:nota,
			observaciones:observaciones,
			id_nota:id_nota
		},
		success:function (response) {
			console.log("Success: 200 function: guardarNota");
			//recargarTabla();
			cargarDataEstudiante();
			$(".nota").attr("readonly","readonly");
			$(".observacion").attr("readonly","readonly");
		},
		error:function (response) {
			console.log("Falta algun campo...");
			console.log(response.mensaje);
		  }
	});
}

//function para editar
function sustitucionNota(nota,actividad,grupestudiante){

	$.ajax({
		method:"Get",
		url:"/notas/allNotas",
		data:null,
		success:comparacion
	});
} 

//funtion para comparar los id de la base
function comparacion(response){
	for(i of response){
		var notaC=i.nota;
		var actividadC=i.actividad.id_actividad;
		var grupestudianteC=i.grupoestudiante.id_grupoestudiante;
		console.log("datos send"+nota+" : "+actividad+" :  "+grupestudiante);
		if(nota==notaC&grupestudiante==grupestudianteC&actividad==actividadC){
			console.log("La de nota es: "+notaC+" ac "+actividadC+" "+grupestudianteC);	
		    id_nota=i.id_nota;
			console.log("El id es"+id_nota,actividadC,grupestudianteC);
			//modificar(id_nota,actividadC,grupestudianteC);
		}else{
			console.log("comparacion de: "+i.id_nota+" : "+notaC);
			//guardarNota();
		}
	}
}

//function para renovar
function modificar(id_nota,actividadC,grupestudianteC){
	console.log(id_nota+actividadC+grupestudianteC);
	 $.ajax({
	        url:"/notas/modificar/"+id_nota,
	        method:"Get",
	        data:{
	            id_nota:id_nota,
	            id_actividad:actividadC,
	            id_grupoestudiante:grupestudianteC,
	            nota:$(".nota").val(),
	            observaciones:$(".observacion").val()
	        },
	        success:function (response) {
	            alert("Se actualizo...");
	            $(".nota").attr("readonly","readonly");
				$(".observacion").attr("readonly","readonly");
	        },
	        error:errorPeticion
	    });
}


//function para cargar los datos de nota
/*function cargarTablaNotas(){
	$("#TDataNota").DataTable({
		"ajax" : {
			"method" : "Get",
			"url" : "/notas/allNotas"
		},
		"columns" : [{
			"data" : "id_nota"
		},{
			"data" : "id_grupoestudiante"
		},{
			"data" : "id_actividad"
		},{
			"data" : "nota"
		},{
			"data" : "observaciones"
		},{
			"data" : "opciones"
		}],
		"scrollY":400,
		"language" : {
          "lengthMenu" : "Mostrar _MENU_ ",
          "zeroRecords" : "Datos no encontrados",
          "info" : "Mostar páginas _PAGE_ de _PAGES_",
          "infoEmpty" : "Datos no encontrados",
          "infoFiltered" : "(Filtrados por _MAX_ total registros)",
          "search" : "Buscar:",
          "paginate" : {
              "first" : "Primero",
              "last" : "Anterior",
              "next" : "Siguiente",
              "previous" : "Anterior"
          }
      },
	});
}*/

//limpiar la memoria ram
/* function resetNotaRam(){
	 $.ajax({
			method : "Get",
			url : "/notas/resetLisNota",
			data:null,
			success: function(){
				alert("Ram reseteada");
			},
			error:function(){
				alert("algo salio mal en la function: resetNotaRam");
			}
	 });
 }
*/

//function para obtener las variables y mandar los arrays
/*function MateriaGrupo(){
	let materia=$("#datoMateria").val();
	let grupo=$("#datoGrupo").val();
	
	$.ajax({
		method:"Get",
		url:"/notas/reporteEstudiante",
		data:{materia:materia,grupo:grupo},
		success:cargarDataEstudiante,//function para cargar estudiantes
		error: function (response){
			console.log(response);
			}
	});
	
}*/
