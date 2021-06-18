$(document).ready(inicio);
	
	function inicio(){
		camposTabla();
		cargarTabla();
	}

	function camposTabla(){ 
		/*$("#myTable").dynamicTable({
		"ajax":{
			"method" : "Get",
			"url" : "http://localhost:8080/estudiantes/allEstudiantes"
		},
		
		"columns":[{
			"data" : "id_estudiante"
		},{
			"data" : "nombre"
		}]
	});*/
  $("#myTable").dynamicTable({
        //definimos las columnas iniciales    
        columns: [{
              text : "nombre",
              key: "name"
          },
          {
              text: "Apellido",
              key: "lastname"
          },
          {
              text: "Email",
              key: "email"
          },
          {
              text: "Telefono",
              key: "phone"
          },
          {
              text: "Institucion",
              key: "id_institucion"
          },        
        ],
 
        //carga de datos
        data: [{
        	"ajax":{
      		  "method" : "Get",
      			"url" : "http://localhost:8080/estudiantes/allEstudiantes"
      	  },
      	columns:[{
      		"data":"nombre"
      	}]
  }],

        //definición de botones
        buttons: {
            addButton: '<input type="button" value="Nuevo" class="btn btn-success" />',
            cancelButton: '<input type="button" value="Cancelar" class="btn btn-primary" />',
            deleteButton: '<input type="button" value="Borrar" class="btn btn-danger" />',
            editButton: '<input type="button" value="Editar" class="btn btn-primary" />',
            saveButton: '<input type="button" value="Guardar" class="btn btn-success" />',
        },
        showActionColumn: true,
        //condicionales
        getControl: function (columnKey) {
            if (columnKey == "phone") {
              return '<input type="number" class="form-control" />';
            }

            if (columnKey == "id_institucion") {
              return '<select class="form-control"><option value="M">Masculino</option><option value="F">Femenino</option></select>';
            }

            return '<input type="text" class="form-control"/>';
        }

    });   
}
	
function cargarTabla(){
	
}