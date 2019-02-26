package util;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;
import org.hibernate.service.*;

public class HibernateUtil {

	
	private static SessionFactory sessionFactory;
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.addAnnotatedClass(model.Driver.class);
			configuration.addAnnotatedClass(model.DrivingShift.class);
			configuration.addAnnotatedClass(model.Client.class);
			configuration.addAnnotatedClass(model.Cargo.class);
			configuration.addAnnotatedClass(model.HrManager.class);
			System.out.println("Configuration loaded");
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			System.out.println("serviceregistry created");
			
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			return sessionFactory;
		}
		catch(Throwable e) {
			System.err.println("sessionfactory creation failed. " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static synchronized SessionFactory getSessionFactory() {
		if(sessionFactory == null) sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
	
	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if(emf == null) {
			System.out.println("[UTIL] create new factory");
			emf = Persistence.createEntityManagerFactory("otp1");
			System.out.println("[UTIL] new factory created!");
		}
		
		return emf;
	}
	
	public static synchronized EntityManager getEntityManager() {
		if(em == null) {
			System.out.println("[UTIL] creating new manager");
			em = getEntityManagerFactory().createEntityManager();
			System.out.println("[UTIL] new manager created!");
		}
		
		return em;
	}
}
