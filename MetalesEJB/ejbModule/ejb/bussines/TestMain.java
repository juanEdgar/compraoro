package ejb.bussines;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import clases.business.metales.vo.cotizador.Metal;

import com.sun.xml.internal.ws.resources.ManagementMessages;

public class TestMain {

	public static void main(String[] args) {
			
		
		try {
			
			
			
			List<Metal> metales= new ArrayList<Metal>();
			Metal m=null;
			for(int i=0; i<10; i++){
				 m= new Metal(i);
				metales.add(m);
			}
			
			
			System.out.println(metales.indexOf(new Metal(4)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
