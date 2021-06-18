let materia={id_materia:0,nombre:""};

$(document).ready(inicio);
//implementacion de las functions
function inicio() {
	cargarDataTM();
	$('body').on('click','.SelectMat', function() {
		cargarInputMate($(this).parent().parent().children('td:eq(0)').text(),$(this).parent().parent().children('td:eq(1)').text());	
		console.log(materia);
		
	});
	
	$('body').on('click','.SelectMat', function() {
		modificarMateria($(this).parent().parent().children('td:eq(0)').text(),$(this).parent().parent().children('td:eq(1)').text());
	});
}

//function para cargar la tabla dinamica
function cargarDataTM() {
	console.log("cargar materias");
	$("#TDataMate").DataTable({
		"ajax":{
			"method" : "Get",
			"url" : "/actividades/allMaterias"
		},
		"columns" :[{
			"data" : "id_materia",
			"height" : "auto"
		},{
			"data" : "nombre",
			"height" : "auto"
		},{
			"data" : "descripcion",
			"height" : "auto"
		},{
			"data" : "especialidad",
			"height" : "auto"
		},{
			"data" : "opciones",
			"height" : "auto"
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

//funcion para mostrar dato en input 
function cargarInputMate(id_materia,nombre) {
	
	materia.id_materia=id_materia;
	materia.nombre=nombre;
	
	$("#Campoid").val(materia.id_materia);
	$("#CampoMateria").val(materia.nombre);
	
	console.log(materia);
}

//function para modificar materia
function modificarMateria(id_materia,nombre){
	materia.id_materia=id_materia;
	materia.nombre=nombre;
	
	$("#Campoid2").val(materia.id_materia);
	$("#CampoMateria2").val(materia.nombre);
	//alert(nombre);
}