//$(document).ajaxStart(function(){
//    $("#loading").css("display","block");
//  });
//      
//$(document).ajaxComplete(function(){
//    $("#loading").css("display","none");
//});
//
//
function appendAjaxLoader(){
   var html = "<div id='loading' style='display:none'><img id='loading-image' src='js/ajaxloader/ajax-loader.gif' alt='Loading...' /></div>";
   $("body").append(html);
}


/*$(function(){

        appendAjaxLoader();
        
        $(document).bind('ajaxStart', function(){
            $('#loading').show();
         // alert("loading ...");
        }).bind('ajaxStop', function(){
         // alert("loading completed ...");
            $('#loading').hide();
            
        });
});*/
