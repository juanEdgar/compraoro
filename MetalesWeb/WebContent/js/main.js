function soloEnteros(event){ 
	var keyCode;
	if(window.event){
		keyCode = event.keyCode;
	}else{		
		if(event.which == 0){
			keyCode = -1;
		}else{
			keyCode = event.which;
		}
	}
	if(keyCode != -1){
		if ( keyCode == 8 || keyCode == 0 || keyCode == 9 
		        ||  (keyCode >= 48 && keyCode <= 57)) {
		    return true;
		}else{
			return false;
		}
	}
}