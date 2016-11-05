/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    $.ajax({
        url: 'GetProducts',
        type: 'GET',
        dataType: 'json'
    }).done(function (json){
        if(json.code===200){
            $.each($.parseJSON(json.msg), function(i,row){
                console.log(row.productname);
                $('#divCatalog').append("<div class='col-sm-4 col-lg-4 col-md-4'><div class='thumbnail'><img src='http://projectxbox.it/wp-content/uploads/2016/08/Gears-of-War-4-320x150.jpg' alt=''><div class='caption'><h4 class='pull-right'>$ " + row.salepricemay + "</h4><p>"+ row.code +"</p><h4><a href='#'>" + row.productname + "</a></h4><button id=btnAdd class='btn btn-primary btn-lg pull-right' onClick = 'addProduct("+row['code']+",\""+row['productname']+"\")'><i class='glyphicon glyphicon glyphicon-shopping-cart'></i></button></div><div class='ratings'><p class='pull-right'>15 reviews</p><p><span class='glyphicon glyphicon-star'></span><span class='glyphicon glyphicon-star'></span><span class='glyphicon glyphicon-star'></span><span class='glyphicon glyphicon-star'></span><span class='glyphicon glyphicon-star'></span></p></div></div></div>");
            });
        }
    })
});

function addProduct(code, name){
    $.ajax({
        url: 'AddProduct',
        type: 'get',
        dataType: 'json',
        data: {code: code,
               productname: name,
               quantity: 1,
               salepricemay: 10}
    }).done(function (json){
        if(json.code===200){
            $('#items').empty();
            $.each($.parseJSON(json.msg), function(i,row){
                console.log(row.quantity);
                $('#items').append("<li><span class='cd-qty'>"+ row.quantity +"x</span> "+ row.productname +"<div class='cd-price'>$"+ row.salepricemay +"</div><a href='#0' class='cd-item-remove cd-img-replace'>Remove</a></li>");
            });
        }
    })
}
