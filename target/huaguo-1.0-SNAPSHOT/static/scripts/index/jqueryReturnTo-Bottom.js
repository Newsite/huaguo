// JavaScript Document
$(function(){
   //the element inside of which we want to scroll
   var contentWrap = $("#home");
   var btns = $("#topBottomBar a");
   var $ruiwang = $("#topBottomBar");
	$(window).scroll( function(){
    if ($(window).scrollTop() > 660){
        $ruiwang.fadeIn("slow");
       /*$('.btn-top').addClass('fixed');*/

    } else {
    $ruiwang.fadeOut("slow");
    /*$('.btn-top').removeClass('fixed');*/
    }
    } );    
   
   //show the buttons
   btns.fadeIn("slow");
   //whenever we scroll fade out both buttons
   /*$(window).load("scrollstart",function(){
     btns.stop().css({"opacity":"0"});
   });*/
   $(window).bind("scrollstart",function(){
     btns.stop().animate({"opacity":"0.6"});
   });
   //...and whenever we stop scrolling fade in both buttons
   $(window).bind("scrollstop",function(){
     btns.stop().animate({"opacity":"1"});
   });
   //clicking the "goToBottom" button will make the page scroll to the contentWrap's height
   $("#goToBottom").click(function(e){
     $("html,body").animate({scrollTop:contentWrap.height()},800);
   });
   //clicking the "goToTop" button will make the page scroll to the top of the page
   $("#goToTop").click(function(e){
     $("html,body").animate({scrollTop:"0px"},800);
   });
});
