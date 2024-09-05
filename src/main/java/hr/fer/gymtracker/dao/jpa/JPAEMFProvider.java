package hr.fer.gymtracker.dao.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton class that provides an instance of EntityManagerFactory.
 */
public class JPAEMFProvider {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gymtracker.database");
	
	static EntityManagerFactory getEmf() {
		return emf;
	}
	
}