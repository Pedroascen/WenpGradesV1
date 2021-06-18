let grupoestudiante={id_estudiante:0,nombre:""};

$(document).ready(inicio);

function inicio(){
	cargarTableEstu();
	$('body').on('click','.SelectEstu',function (){
		cargarInput($(this).parent().parent().children('td:eq(0)').text(),$(this).parent().parent().children('td:eq(1)').text());
	});
	
	$('body').on('click','.SelectEstu',function (){
		modificarEstudiante($(this).parent().parent().children('td:eq(0)').text(),$(this).parent().parent().children('td:eq(1)').text());
	});
}

//cargar data en table
function cargarTableEstu(){
	$("#TDataEstudiante").DataTable({
		"ajax": {
			"method" : "Get",
			"url" : "/grupoestudiantes/allEstudiantes"
				},
		"columns" :[{
			"data" : "id_estudiante"
		},{
			"data" : "nombre"
		},{
			"data" : "apellido"
		},{
			"data" : "id_institucion"
		},{
			"data" : "opciones"
		}],
		
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
            },
        }
	});
}

//function para guardar id
function cargarInput(id_estudiante,nombre){
	grupoestudiante.id_estudiante=id_estudiante;
	grupoestudiante.nombre=nombre;
	
	$("#CampoidEstu").val(grupoestudiante.id_estudiante);
	$("#CampoEstudiante").val(grupoestudiante.nombre);
}

//fnction para modificar estudiante
function modificarEstudiante(id_estudiante,nombre){
	grupoestudiante.id_estudiante=id_estudiante;
	grupoestudiante.nombre=nombre;
	
	$("#CampoidEstu2").val(grupoestudiante.id_estudiante);
	$("#CampoEstudiante2").val(grupoestudiante.nombre);
}