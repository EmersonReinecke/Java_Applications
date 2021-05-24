/**
 * 
 */
SENAI =new Object();

SENAI.ajax=new Object();

function ajaxRequestDefault(){
	
	var def={
	url:null,
	dataType:'json',
	contentType:"application/json; charset=UTF-8",
	type:'POST',
	success:function(){},
	error:function(err){
	alert("error="+err.responseText);	
	}
	
	};
	return def;
	
}
    function verifyObjectData(cfg){
    	if(cfg.data){
    	if(isObject(cfg.data)){
    	cfg.data = JSON.stringfy(cfg.data);	
    	     }
    	 }
     return cfg;
    } 
    
    function isObject(o){
    	return $.isArray(o) | $.isPlainObject(o) | $.isFunction(o);
    };
    
    SENAI.ajax.post = function(cfg){
    	
    	var def=new ajaxRequestDefault();
    	
    	cfg=verifyObjectData(cfg);
    	
    	var config=$.extend(def, cfg);
    	
    	
    	$.ajax(config);
    		
    };
    
    SENAI.ajax.get = function(cfg){
    	var def = new ajaxRequestDefault();
    	cfg.type="GET";
    	cfg=verifyObjectData(cfg);
    	$.ajax(config);
    };
    
    
    
    
    
    
    
    
    
    