package clases.web.login;

import java.io.Serializable;
import java.security.Principal;

public class MyPrincipal implements Principal, Serializable {
	  private static final long serialVersionUID = 1L;
	  private final String name;

	  public MyPrincipal(String name) {
	    this.name = name;
	  }

	  public String getName() {
	    return name;
	  }
	  
	  public boolean equals(Object o){
		  if(!(o instanceof MyPrincipal)){
			  return false;
		  }
		  
		  if(this.name.equals(((MyPrincipal)o).getName())){
			  return true;
		  }else{
			  return false;
		  }
	  }
	  
	  public int hashCode(){
		  return this.name.hashCode();
	  }
}
	 