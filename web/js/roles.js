/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
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
   $('#tbRoles').DataTable({
       language: {
           url: "//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json"
       },
       ajax:{
           url: "GetRoles",
           dataSrc: function(json){
               console.log("Resultado: "+ json['msg']);
               return $.parseJSON(json['msg']);
           }
       },
       columns:[
           {
               data: "roleid"
           },
           {
               data: "rolename"
           }
       ]
   });
});

function newRole(){
    $.ajax({
        url: "NewRole",
        type: "post",
        data: $('#frmRole').serialize()
    }).done(
        function(data){
            if(data.code === 200){
                $.growl.notice({ message: data.msg });
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


