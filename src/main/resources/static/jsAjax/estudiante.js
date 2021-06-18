$(document).ready(inicio);

//implementacion de las funciones
function inicio(){
	cargarDataT();
	cargarInstitucion();
	$("body").on('click','#guardarEstu',function (){
		//bootstrapValidate();
		guardarEstu();
		reset();
	});
	//boton para eliminar
	$("body").on('click','.dropEstu',function () {
		$("#idEstu").val($(this).parent().parent().children('td:eq(0)').text());
		$("#nombreDrop").val($(this).parent().parent().children('td:eq(2)').text());
	});
	//boton para modificar
	$("body").on('click','.ModEstu',function () {
		$("#id").val($(this).parent().parent().children('td:eq(0)').text());
		cargarModal();
	});
	$("body").on('click','#eliminarEstudiante',eliminar);
	$("body").on('click','#modificarEstu',modificar);
}

// function para limpiar los input
function reset() {
	$("#nombre").val(null);
	$("#apellido").val(null);
	$("#email").val(null);
	$("#telefono").val(null);
	$("#institucionSelect").val(null);
}

//cargar los datos de forma sincrona

//funcion para recargar la tabla
function recargarTabla(){
	var table = $('#TDataEstu').DataTable();
	
	table.ajax.reload( function ( json ) {
	    $('#TDataEstu').val( json.lastInput );
	});
}

// function para cargar tabla de datos de base
function cargarDataT(){
	$("#TDataEstu").DataTable({
		"ajax":{
			"method" : "Get",
			"url" : "/estudiantes/allEstudiantes"
		},
		"columns" :[{
			"data" : "id_estudiante",
			"width" : "5%"
		},{
			"data" : "institucion",
			"width" : "20%"
		},{
			"data" : "nombre",
			"width" : "12%"
		},{
			"data" : "apellido",
			"width" : "12%"
		},{
			"data" : "email",
			"width" : "15%"
		},{
			"data" : "telefono",
			"width" : "10%"
		},{
			"data" : "opciones",
			"width" : "26%"
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
            },
        }
	});
}

// function para cargar las instituciones a los select
function cargarInstitucion() {
	$.ajax({
		type: "Get",
		url: "/estudiantes/allInstitucion",
	success: function (response) {
		for(item of response){
			$("#institucionSelect").append(""
			+"<option value='"+item.id_institucion+"'>"+item.nombre+"</option>"
			+"");
			$("#institucionSelect2").append(""
			+"<option value='"+item.id_institucion+"'>"+item.nombre+"</option>"
			+"");
		}
	},
	error:function (response) {
	console.log(response);	
	}
	});
}

//function para cargar registros
function cargarModal(){
	let id_estudiante=$("#id").val();
	$.ajax({
		url:"/estudiantes/getEstudiante/"+id_estudiante,
		method:"Get",
		data:null,
		success:function(response){
			$("#id").val(response.id_estudiante);
			$("#nombre2").val(response.nombre);
			$("#apellido2").val(response.apellido);
			$("#email2").val(response.email);
			$("#telefono2").val(response.telefono);
			$("#institucionSelect2").val(response.institucion.id_institucion);
		},
		error:function(response){
			console.log(response);
		}
	});
	//alert(id_estudiante);
}

//Validaciones para el modal
//function bootstrapValidate(['#nombre', '#apellido','#telefono'], 'min:8:Ingrese 8 digitos min!');

// function para guardar registros
function guardarEstu() {
	$.ajax({
		url:"/estudiantes/save",
		method:"Get",
		data:{
			nombre:$("#nombre").val(),
			apellido:$("#apellido").val(),
			email:$("#email").val(),
			telefono:$("#telefono").val(),
			id_institucion:$("#institucionSelect").val()
		},
		success:function (response) {
			// alert(response);
			recargarTabla();
		},
		error:function (response) {
			alert("No se guardo...")
			console.log(response);
		  }
	});
}

// function para eliminar
function eliminar() {
	var id_estudiante=$("#idEstu").val();
	var nombre=$("#nombreDrop").val();
	$.ajax({
		url:"/estudiantes/eliminar/"+id_estudiante,
		method:"Get",
		data:null,

		success:function (response) {
			// alert(response.mensaje);
			recargarTabla();
		  },
		error:function (response) {
			console.log(response.mensaje);
		  }
	});
}

//funcion para modificar
function modificar(){
	var id_estudiante = $("#id").val();
	$.ajax({
		method:"Get",
		url:"/estudiantes/modificar/"+id_estudiante,
		data:{
			id_estudiante:id_estudiante,
			nombre:$("#nombre2").val(),
			apellido:$("#apellido2").val(),
			telefono:$("#telefono2").val(),
			email:$("#email2").val(),
			id_institucion:$("#institucionSelect2").val()
		},
		success: function(response){
			recargarTabla();
		},
		error: function(response){
			console.log(response);
		}
	});
}
