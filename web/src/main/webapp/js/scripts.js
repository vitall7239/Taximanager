jQuery(document).ready(function(e) {
	/*jQuery("input[type='text'],input[type='email'],input[type='tel'],input[type='password'], textarea").focusin(function(e) {
       jQuery(this).addClass("clean"); 
    });
	jQuery("input[type='text'],input[type='email'],input[type='tel'],input[type='password'], textarea").focusout(function(e) {
       jQuery(this).removeClass("clean"); 
    });*/
	jQuery(".tel").inputmask({
		alias: "phonemy",
    });
	jQuery("input[type='checkbox'], [type='radio'], select").styler();
	
	jQuery(".popup_open").click(function(){
		var h = jQuery("#site").height();
		var obj = ".popup_wrapper>."+jQuery(this).attr("data-href");
		jQuery(".popup_bg").css({"display":"block","height":h}).animate({opacity:1},200);
		jQuery(obj).css({"display":"block"});
		if(jQuery(this).attr("href") == "popup_order_info"){
			jQuery(".popup_order_info .info_bl .adr_from").text(jQuery(this).data("from"));	
			jQuery(".popup_order_info .info_bl .adr_to").text(jQuery(this).data("to"));	
			jQuery(".popup_order_info .info_bl .time").text(jQuery(this).data("time"));	
			jQuery(".popup_order_info .info_bl .status").text(jQuery(this).data("status"));	
			jQuery(".popup_order_info .info_bl .price span").text(jQuery(this).data("price"));	
			jQuery(".popup_order_info .info_bl .description").text(jQuery(this).data("description"));	
			jQuery(".popup_order_info .info_bl .order-id").text(jQuery(this).data("order-id"));	
		}
		var ih = jQuery(window).innerHeight();
		var wh = jQuery(obj).outerHeight();
		var wt = (ih-wh)/2;
		var st = jQuery(document).scrollTop();
		if(wt<0){wt=0};
		wt = wt+st;
        if(wt+wh>h){
        	wt = h - wh;
            jQuery("body, html").animate({scrollTop:wt},500);
        }
		
		jQuery(obj).css({"top":wt}).animate({opacity:1},200);
		
		return false;
	});
	jQuery(".popup_close").click(function(){
		jQuery(this).parents("li").animate({opacity:0},200).hide(0);
		jQuery(".popup_bg").animate({opacity:0},200).hide(0);
	});
	jQuery(".popup_bg").click(function(){
		jQuery(".popup_wrapper>li").animate({opacity:0},200).hide(0);
		jQuery(".popup_bg").animate({opacity:0},200).hide(0);
	});        
	
	function ok_function(){
		jQuery(".popup_wrapper>li").animate({opacity:0},0).hide(0);
		var h = jQuery("#site").height();
		var obj = ".popup_wrapper>.popup_thanks";
		jQuery(".popup_bg").css({"display":"block","height":h}).animate({opacity:1},200);
		jQuery(obj).css({"display":"block"});
		var ih = jQuery(window).innerHeight();
		var wh = jQuery(obj).outerHeight();
		var wt = (ih-wh)/2;
		var st = jQuery(document).scrollTop();
		if(wt<0){wt=0};
		wt = wt+st;
		jQuery(obj).css({"top":wt}).animate({opacity:1},200);
	}
	function ok_function_sub(){
		jQuery(".popup_wrapper>li").animate({opacity:0},0).hide(0);
		var h = jQuery("#site").height();
		var obj = ".popup_wrapper>.popup_thanks_descount";
		jQuery(".popup_bg").css({"display":"block","height":h}).animate({opacity:1},200);
		jQuery(obj).css({"display":"block"});
		var ih = jQuery(window).innerHeight();
		var wh = jQuery(obj).outerHeight();
		var wt = (ih-wh)/2;
		var st = jQuery(document).scrollTop();
		if(wt<0){wt=0};
		wt = wt+st;
		jQuery(obj).css({"top":wt}).animate({opacity:1},200);
	}
	function error_function(){
		alert("Error");
	}
	function valid_func(form){
		if(jQuery(form).find("input[name='agree']:checked").length>0){
			jQuery(form).find(".agree_bl .error").remove();
			if(jQuery(form).find("input[type='tel']").length>0){
				
				if(jQuery(form).find("input[type='tel']").inputmask("getmetadata")["cd"] != "no" && jQuery(form).find("input[type='tel']").inputmask("isComplete") && jQuery(form).valid()){
					return true;
				}else{
					jQuery(form).find("input[type='tel']").val("");
					jQuery(form).valid();
					return false;	
				}
			}else{
				if(jQuery(form).valid()){
					return true;
				}else{
					return false;	
				}	
			}
		}else{
			if(jQuery(form).find(".agree_bl .error").length == 0){
				jQuery(form).find(".agree_bl").append("<label class='error'>You should agree this action.</label>");
			}
		}
	}
	jQuery.fn.form_send = function(valid_option, action_file, valid_function, send_ok_function, send_error_function){
		
		jQuery(this).each(function(index, element) {
			jQuery(element).validate(valid_option);
			jQuery(element).find("[type='submit']").click(function(e){
				e.preventDefault();
				if(valid_function(element) == true){
					send_ok_function(element);
					var http = new XMLHttpRequest();
					http.open("POST", action_file, true);
					http.onreadystatechange = function() {
						if (http.readyState == 4 && http.status >= 200 && http.status < 300) {
							console.log("Send");
						}
					}
					http.onerror = function() {
						send_error_function();
					}
					http.send(new FormData(element));
				}else{
				}
				return false;
			});
        });
		
	};
	jQuery("#popup_call_form").form_send({rules:{tel:"required"}}, "include/mail.php", valid_func, ok_function, error_function);
	
	
	var dayList = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	function setDateTime(){
		var date = new Date();
		//jQuery(".date_wrap .day").text(dayList[date.getDay()]);
		var data = date.getDate();
		if(data < 10){
			data = "0"+data;
		}
		jQuery(".date_wrap .date").text(data);
		var mon = date.getMonth()+1;
		if(mon < 10){
			mon = "0"+mon;
		}
		jQuery(".date_wrap .month").text(mon);
		jQuery(".date_wrap .year").text(date.getFullYear());
		var hour = date.getHours();
		if(hour < 10){
			hour = "0"+hour;
		}
		jQuery(".date_wrap .hour").text(hour);
		var minut = date.getMinutes();
		if(minut < 10){
			minut = "0"+minut;
		}
		jQuery(".date_wrap .min").text(minut);
	}
	setDateTime();
	setInterval(setDateTime,1000*5);



});

function update_popup(){
   	var h = jQuery("#site").height();
    var obj = ".popup_wrapper>li";
    var ih = jQuery(window).innerHeight();
    var wh = jQuery(obj).outerHeight();
    var wt = (ih-wh)/2;
    var st = jQuery(document).scrollTop();
    if(wt<0){wt=0};
    wt = wt+st;
    if(wt+wh>h){
        wt = h - wh;
        jQuery("body, html").animate({scrollTop:wt},0);
    }
    jQuery(obj).css({"top":wt});
}