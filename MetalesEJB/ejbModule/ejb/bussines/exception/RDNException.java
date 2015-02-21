package ejb.bussines.exception;

public class RDNException extends Exception {

	public RDNException(String string) {
		super(string);
	}

	public RDNException(String string, Exception e) {
		super(string,e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8115254498553159532L;
	
	

}
