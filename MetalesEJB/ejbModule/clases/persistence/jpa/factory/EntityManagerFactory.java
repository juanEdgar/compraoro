package clases.persistence.jpa.factory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import clases.persistence.jpa.factory.qualifier.MetalesEM;


@RequestScoped
public class EntityManagerFactory {
	
	@Produces @MetalesEM
	@PersistenceContext(unitName="MetalesJPA")
	private EntityManager metales;
	
	
	
	
}



