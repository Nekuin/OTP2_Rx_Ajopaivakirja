package model;
import java.sql.Connection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//DAO

public class AjoAccessObject implements IAjo {
	
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
	
	@Override
	protected void finalize() {
		try {
			if(myConnect!=null) {
				myConnect.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public boolean createDriver (IDriver driver) {
		Session istunto = istuntotehdas.openSession();
		Transaction t = null;
		try {
			t = istunto.beginTransaction();
			istunto.saveOrUpdate(driver);
			t.commit();
			return true;
		} catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		} finally {
			istunto.close();
		}
		return false;
	}
	
	@Override
	public Driver readDriver (String driversLicense) {
		
		Transaction t = null;
		Driver driver = new Driver();
		
		try(Session istunto = istuntotehdas.openSession()){
			t = istunto.getTransaction();
			t.begin();
			istunto.load(driver, driversLicense);
			t.commit();
			return driver;
		} catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public List<IDriver> readDriver(){
		//parempi varmaa avata uus session ja sulkea se lopuksi
		Session istunto = istuntotehdas.openSession();
		List<IDriver> driver = null;
		try {
			driver = (List<IDriver>)istunto.createQuery("from Driver").list();
		} catch (HibernateException e) {e.printStackTrace();}
		finally {
			istunto.close();
			}
		return driver;
	}
	
	@Override
	public boolean updateDriver(IDriver driver) {
		Transaction t = null;
		try(Session istunto = istuntotehdas.openSession();){
			t = istunto.beginTransaction();
			istunto.saveOrUpdate(driver);
			t.commit();
			return true;
		}catch(Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteDriver(int driverID) {
		Transaction t = null;
		try(Session istunto = istuntotehdas.openSession();){
			t = istunto.beginTransaction();
			Driver d = (Driver)istunto.get(Driver.class, driverID);
			istunto.delete(d);
			t.commit();
			return true;
		}catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
