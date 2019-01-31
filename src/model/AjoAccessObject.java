package model;
import java.sql.Connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//DAO

public class AjoAccessObject implements AjoDAO_IF {
	
	private Connection myConnect;
	
	SessionFactory istuntotehdas= null;
	
	public AjoAccessObject() {
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try{
			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch(Exception e){
			System.out.println("Oh no");
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	}
}
