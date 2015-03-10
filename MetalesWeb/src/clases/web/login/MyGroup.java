package clases.web.login;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;


public class MyGroup implements Group, Serializable { 
	  private static final long serialVersionUID = 1L;
	  private final String name;
	  private final Set<Principal> users = new HashSet<Principal>();

	  public MyGroup(String name) {
	    this.name = name;
	  }

	  public boolean addMember(Principal user) {
	    return users.add(user);
	  }

	  public boolean removeMember(Principal user) {
	    return users.remove(user);
	  }

	  public boolean isMember(Principal member) {
	    return users.contains(member);
	  }

	  public Enumeration<? extends Principal> members() {
	    return Collections.enumeration(users);
	  }

	  public String getName() {
	    return name;
	  }

	  public boolean equals(Object o) {
		  if(!(o instanceof MyGroup)){
			  return false;
		  }
		  
		  if(this.name.equals(((MyGroup)o).getName())){
			  return true;
		  }else{
			  return false;
		  }
	  }

	  public int hashCode(){
		  return this.name.hashCode();
	  }
	  
	  
	}