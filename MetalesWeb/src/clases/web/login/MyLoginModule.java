package clases.web.login;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class MyLoginModule implements LoginModule {

	private CallbackHandler handler;
	private Subject subject;

	private String login;

	private MyPrincipal user;
	 private MyPrincipal[] roles;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {

		handler = callbackHandler;
		this.subject = subject;
	}

	@Override
	public boolean login() throws LoginException {

		
		NameCallback nameCallback = new NameCallback("Username");
	    PasswordCallback passwordCallback = new PasswordCallback("Password", false);
	    Callback[] callbacks = new Callback[]{nameCallback, passwordCallback};
		
		System.out.println("Log in in");
		
		try {
			handler.handle(callbacks);
			String name = nameCallback.getName();
			String password = String.valueOf( passwordCallback.getPassword());
			
			
			System.out.println(name);
			System.out.println(password);
			
			if (name != null && name.equals("admin") && password != null
					&& password.equals("admin")) {
				login = name;
				
				roles = new MyPrincipal[] {
					      new MyPrincipal("admin") 
					    };
				
				
				this.user = new MyPrincipal(name);
				
				System.out.println("log in ok");
				 passwordCallback.clearPassword();
				return true;
			}
			
			
			if (name != null && name.equals("cajero") && password != null
					&& password.equals("cajero")) {
				login = name;
				
				roles = new MyPrincipal[] {
					      new MyPrincipal("cajero") 
					    };
				
				
				this.user = new MyPrincipal(name);
				
				System.out.println("log in ok");
				 passwordCallback.clearPassword();
				return true;
			}
			
			System.out.println("Log fallo");
			
			throw new LoginException("Authentication failed");

		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} catch (UnsupportedCallbackException e) {
			throw new LoginException(e.getMessage());
		}

	}

	@Override
	public boolean commit() throws LoginException {

		 subject.getPrincipals().add(user);
		    // jboss requires the name 'Roles'
		    MyGroup group = new MyGroup("Roles");
		    for (MyPrincipal role : roles) {
		      group.addMember(role);
		    }
		    subject.getPrincipals().add(group);
		    return true;
	
	}

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(user);
		
		for(MyPrincipal p:roles){		
			subject.getPrincipals().remove(p);
		}
		return true;
	}

}
