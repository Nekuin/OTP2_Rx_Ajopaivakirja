package util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Handles creating EntityManager for Data Access objects
 * @author Nekuin
 *
 */
public class HibernateUtil {

	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	//hide implicit public constructor
	private HibernateUtil() {
		
	}
	
	/**
	 * Getter for an entity manager factory
	 * @return EntityManagerFactory emf
	 */
	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if(emf == null) {
			System.out.println("[UTIL] create new factory");
			emf = Persistence.createEntityManagerFactory("otp1");
			System.out.println("[UTIL] new factory created!");
		}
		
		return emf;
	}
	/**
	 * Getter for the test version of entity manager factory
	 * @return EntityManagerFactory emf
	 */
	public static synchronized EntityManagerFactory getTestEntityManagerFactory() {
		if(emf == null) {
			System.out.println("[UTIL] creating test factory");
			emf = Persistence.createEntityManagerFactory("otp-test");
			System.out.println("[UTIL] test factory created");
		}
		return emf;
	}
	/**
	 * Getter for an entity manager
	 * @return EntityManager em
	 */
	public static synchronized EntityManager getEntityManager() {
		if(em == null) {
			System.out.println("[UTIL] creating new manager");
			em = getEntityManagerFactory().createEntityManager();
			System.out.println("[UTIL] new manager created!");
		}
		
		return em;
	}
	
	/**
	 * Getter for the test version of the EntityManager
	 * @return EntityManager em
	 */
	public static synchronized EntityManager getTestEntityManager() {
		if(em == null) {
			em = getTestEntityManagerFactory().createEntityManager();
		}
		return em;
	}
}
