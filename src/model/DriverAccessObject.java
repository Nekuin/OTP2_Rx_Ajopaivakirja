package model;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class DriverAccessObject {
	private SessionFactory sf;
	
	public DriverAccessObject() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public boolean createDriver(IDriver driver) {
		Transaction t = null;
		try(Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.saveOrUpdate(driver);
			t.commit();
			return true;
		} catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public IDriver readDriver(int id) {
		Driver driver = new Driver();
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.load(driver, id);
			t.commit();
			return driver;
		} catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Driver> readDriver(){
		Transaction t = null;
		List<Driver> drivers = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Driver> criteria = builder.createQuery(model.Driver.class);
			criteria.from(model.Driver.class);
			drivers = session.createQuery(criteria).getResultList();
			t.commit();
			return drivers;
			
		} catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
}
