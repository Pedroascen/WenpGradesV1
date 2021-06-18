/*$(document).ready(inicio);

function inicio() {
	peticionPromedio();
	$("#dataMateria").change(peticionPromedio);
	$("#dataGrupo").change(peticionPromedio);
}

function peticionPromedio() {
	let materia=$("#dataMateria").val();
	let grupo=$("#dataGrupo").val();
	
	//peticion ajax para cargar datos en table
	$.ajax({
		method:"Get",
		url:"/notas/reporteNota",
		data:{materia:materia,grupo:grupo},
		success:procesar,
		error:error
	});
}

function procesar(data) {
	console.log(data);
	//insercion de datos en tabla
	$("#tdatosConsul").html("");
	for(i of data){
		var porcentaje = i.actividad.ponderacion;
		var nota = i.nota;		
		var notaPromedio = (porcentaje*nota);*/

		/*var estudiante = i.grupoestudiante.estudiante.nombre;
		if (estudiante == estudiante) {
			actividades=i.actividad.length;
			alert(actividades);	
			}*/
		/*$("#tdatosConsul").append(""
				+"<tr>"
				+"<td>"+i.grupoestudiante.estudiante.nombre+" "+i.grupoestudiante.estudiante.apellido+"</td>"
				+"<td>"+i.actividad.nombre+"="+porcentaje+"</td>"
				+"<td>"+i.nota+"</td>"
			+"</tr>"
				+"");
	}
}

function error(response){
	console.log(response);
}*/