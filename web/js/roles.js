/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(function(){  
   $('#mRoles').trigger('click');
    
    
   $.ajax({
        url: 'GetRoles',
        type: 'GET',
        dataType: 'json'
    }).done(function (json){
        console.log("Codigo json: "+json.code);
        if(json.code===200)
        $.each($.parseJSON(json.msg), function(i,row){
            console.log(row.rolename);
           $('<option></option>', {text: row.rolename}).attr('value',row.roleid).appendTo('#cbRoles'); 
        });
    });
   
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
        language:{
            url:"http://cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json"
        },
        ajax:{
          url:"GetRoles"  ,
          dataSrc:function(json){
              
              return $.parseJSON(json['msg']);
          }
        },
        columns:[
            {
                data:function(row){
                str="<div align='right'>";
                str+= accounting.formatMoney( row["roleid"] );
                str+="</div>";
                return str;
                }
            },
            {
             data:"rolename"   
            },
            {
              data: function(row){
                  str="<div align='center'>";
                  str+="<button id='btnBorrar' class='btn btn-danger btn-xs' onclick='deleteRole("+row["roleid"]+")'><i class='glyphicon glyphicon-trash'></i></button>";
                  str+= "&nbsp;<button id='btnEditar' class = 'btn btn-success btn-xs' onClick = 'showRole("+row['roleid']+",\""+row['rolename']+"\")'><i class='glyphicon glyphicon-edit'></i></button>";
                  str+="<div>";
                  return str;
              }  
            }
        ]
            
        
    });
   
   $("#btnModificar").on('click', function(){
       $("#frmEditRole").submit();
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

function deleteRole(id){
    swal({title: "¿Estás seguro que deseas eliminar?",
        text: "No podrás recuperar la información después de borrarla.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Sí, eliminar",
        closeOnConfirm: false},
            function () {
                $.ajax(
                        {
                            url: "DeleteRole",
                            type: "post",
                            data: {roleid: id}
                        }
                ).done(
                        function (data) {
                            if (data.code === 200) {
                                //$.growl.notice({title: "Successful", message: data.msg });
                                swal("Eliminado", data.msg, "success");
                                $('#tbRoles').dataTable().api().ajax.reload();
                                $('#rolename').val('');
                            } else {
                                $.growl.error({message: data.msg});
                            }
                        }
                ).fail(
                        function (data) {
                            //$.growl.notice({message: "Algo va mal"});
                            swal({title: "Error", text: "Algo va mal, no se pudo eliminar", type: "error", confirmButtonText: "Cerrar"});
                        }
                );    
                
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


