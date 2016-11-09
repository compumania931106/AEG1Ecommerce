$(function(){
   //$('#tbRoles').dataTable().api().ajax.reload();
   $('#frmLogin').validate({
       rules:{
           username:{
               minlength: 3,
               maxlength: 20,
               required: true
           },
           password:{
               minlength: 3,
               maxlength: 20,
               required: true
           }
       },
       messages:{
           username:{
               minlength: "Introduzca al menos tres caracteres",
               maxlength: "Introdusca menos de 20 caracteres",
               required: "Capture su nombre de usuario"
           },
           password:{
               minlength: "Introduzca al menos tres caracteres",
               maxlength: "Introdusca menos de 20 caracteres",
               required: "Capture su contraseña"
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
           login();
           return false;
       }
   }); 
   
   function login(){
       $.ajax({
        url: "GetUser",
        type: "post",
        data: {username : $('#username').val(),
              password : $('#password').val()}
    }).done(
        function(data){
            if(data.code === 200){
                var url = "index.html"; 
                $(location).attr('href',url);
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
   
});