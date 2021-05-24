//Ordem de Serviço
function ConsultarLogin(){
	
	var valorlogin =$("#usuario").val();
	 var valorsenha =$("#senha").val();
	 var encodedData = btoa(valorsenha);
	    
	    
	 if(valorlogin!=null&&encodedData!=null){
		 $.ajax({
			
			type:"POST",
			url:"login",
			
			data: {
				usuario: valorlogin,
				senha: encodedData
			},
			
			success: function(data,status){
				console.log(data);
				window.location='http://localhost:8080/OrdemDeServico/app/Home.html';
				
			},
					
			error:function(msg){
				  alert("Usuario ou senha incorretos");
					
				}
		     });
	 }else{
		 alert("Usuario ou senha errados");
	 }
	
 };
 
 
 