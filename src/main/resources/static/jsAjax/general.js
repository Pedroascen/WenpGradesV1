$(document).ready(function(){
	
	reload();
	
	$("#dataMateria").change(reload);
	$("#dataGrupo").change(reload);
	cargarActividades();
});


//function para los select
function reload(){
	materia = $("#dataMateria").val();
	grupo = $("#dataGrupo").val();
	
	cargarDataEstudiante(materia, grupo);
	//cargar Activdades en select
	cargarActividades(materia);
}

function peticionEstudiantes(){
	
  //peticion ajax para cargar los grupos	
	
	let materia=$("#dataMateria").val();
	let grupo=$("#dataGrupo").val();
	$.ajax({
		method:"Get",
		url:"/grupoestudiantes/reporteEstudiante",
		data:{materia:materia,grupo:grupo},
		success:procesar,
		error:error
	});
}

//function cargar
function cargarDataEstudiante(materia, grupo){
	$("#dataTableGeneral").DataTable({
		"ajax" : {
			"method" : "Get",
			"url" : "/general/reporteNota?"+"materia="+materia+"&"+"grupo="+grupo
		},
		"columns" : [{
			"data":"estudiante"
		},{
			"data":"actividad"
		},{
			"data":"nota"
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
}


//function para cargar Actividad
function cargarActividades(){
	materia=$("#dataMateria").val();
	console.log(materia);
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
		}
	});
}

function error(data){}