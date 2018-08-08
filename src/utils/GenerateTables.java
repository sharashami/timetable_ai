package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GenerateTables {
	public static void main ( String [] args ) {
		/* SessionFactory sessionFactory;
	        sessionFactory = new Configuration()
	                .configure("/META-INF/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
	                .buildSessionFactory();
	 */
		
		
		EntityManagerFactory factory = Persistence
	                .createEntityManagerFactory("horario");
		 
		 
	}
}
