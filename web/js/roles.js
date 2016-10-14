/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
   //$('#tbRoles').dataTable().api().ajax.reload();
   $('#frmRole').validate({
       rules:{
           rolename:{
               minlength: 3,
               maxlength: 20,
               required: true
           }
       },
       messages:{
           rolename:{
               minlength: "Introduzca al menos tres caracteres",
               maxlength: "Introdusca menos de 20 caracteres",
               required: "Capture el nombre del rol"
           }
       },
       highlight: function (element){
           $(element).closest('.form-group').addClass('has-error');
       },
       unhighlight: function (element){
           $(element).closest('.form-group').removeClass('has-error');
       },
       errorElement: 'span',
       errorClass: 'help-block',
       errorPlacement: function(error, element){
           if(element.parent('.input-group').length){
               error.insertAfter(element.parent());
           }else{
               error.insertAfter(element);
           }
       },
       submitHandler: function(form){
           newRole();
           return false;
       }
   }); 
   
   $('#frmEditRole').validate({
       rules:{
           rolename2:{
               minlength: 3,
               maxlength: 20,
               required: true
           }
       },
       messages:{
           rolename2:{
               minlength: "Introduzca al menos tres caracteres",
               maxlength: "Introdusca menos de 20 caracteres",
               required: "Capture el nombre del rol"
           }
       },
       highlight: function (element){
           $(element).closest('.form-group').addClass('has-error');
       },
       unhighlight: function (element){
           $(element).closest('.form-group').removeClass('has-error');
       },
       errorElement: 'span',
       errorClass: 'help-block',
       errorPlacement: function(error, element){
           if(element.parent('.input-group').length){
               error.insertAfter(element.parent());
           }else{
               error.insertAfter(element);
           }
       },
       submitHandler: function(form){
           updateRole();
           return false;
       }
   });
   
   
   $('#tbRoles').DataTable({
       language: {
           url: "//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json"
       },
       ajax:{
           url: "GetRoles",
           dataSrc: function(json){
               //console.log("Resultado: "+ json['msg']);
               return $.parseJSON(json['msg']);
           }
       },
       columns:[
           {
               data: "roleid"
           },
           {
               data: "rolename"
           },
           {
               data: function(row){
                   console.log(row);
                   str = "<button id='btnBorrar' class = 'btn btn-danger btn-xs' onClick = 'deleteRole("+row['roleid']+")'>Borar</button>";
                   str+= "&nbsp;<button id='btnEditar' class = 'btn btn-success btn-xs' onClick = 'showRole("+row['roleid']+",\""+row['rolename']+"\")'>Modificar</button>";
                   return str;
               }
           }
       ]
   });
});

function showRole(roleid, rolename){
    $('#roleid').val(roleid);
    $('#rolename2').val(rolename);
    $("#modalRole").modal("show");
    console.log("El id del rol es:" + roleid);
}

function updateRole(){
    $.ajax({
        url: "UpdateRole",
       type: "post",
       data: {roleid : $('#roleid').val(),
              rolename : $('#rolename2').val()}
    }).done(
        function(data){
            if(data.code === 200){
                $("#modalRole").modal("hide");
                $.growl.notice({message: data.msg});
                $('#tbRoles').dataTable().api().ajax.reload();
            }else{
                $.growl.error({ message: data.msg });
            }
        }
    ).fail(
        function(){
            $.growl.error({ message: "No hay mensaje que mostrar" });
        }
    );
}

function deleteRole(idRole){
    $.ajax({
       url: "DeleteRole",
       type: "post",
       data: {roleid : idRole}
    }).done(
        function(data){
            if(data.code === 200){
                $.growl.notice({message: data.msg});
                $('#tbRoles').dataTable().api().ajax.reload();
            }else{
                $.growl.error({ message: data.msg });
            }
        }
    ).fail(
        function(){
            $.growl.error({ message: "No hay mensaje que mostrar" });
        }
    );
}

function newRole(){
    $.ajax({
        url: "NewRole",
        type: "post",
        data: $('#frmRole').serialize()
    }).done(
        function(data){
            if(data.code === 200){
                $.growl.notice({ message: data.msg });
                $('#tbRoles').dataTable().api().ajax.reload();
                $('#rolename').val('');
            }
            else{
                $.growl.error({ message: data.msg });
            }
            
        }
    ).fail(
        function(){
            $.growl.error({ message: "No hay mensaje que mostrar" });
        }
    );
}


