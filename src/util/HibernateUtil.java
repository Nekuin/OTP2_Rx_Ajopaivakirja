package util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;
import org.hibernate.service.*;

public class HibernateUtil {

	
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.addAnnotatedClass(model.Driver.class);
			configuration.addAnnotatedClass(model.DrivingShift.class);
			configuration.addAnnotatedClass(model.Client.class);
			configuration.addAnnotatedClass(model.Cargo.class);
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
}
