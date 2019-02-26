package model;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class VehicleAccessObject {
	
	SessionFactory sf;
	
	public VehicleAccessObject() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public boolean createVehicle(Vehicle vehicle) {
		
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(vehicle);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateVehicle(Vehicle vehicle) {
		
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(vehicle);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public Vehicle readVehicle(int id) {
		Vehicle vehicle = new Vehicle();
		Transaction t = null;
		try(Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.load(vehicle, id);
			t.commit();
			return vehicle;
		}
		catch(HibernateException e){
			if (t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Vehicle> readVehicle(){
		List<Vehicle> vehicles = null;
		Transaction t = null;
		try (Session session = sf.openSession()){
			t = session.beginTransaction();
			CriteriaBuilder build = session.getCriteriaBuilder();
			CriteriaQuery<Vehicle> query = build.createQuery(model.Vehicle.class);
			query.from(model.Vehicle.class);
			vehicles = session.createQuery(query).getResultList();
			t.commit();
			return vehicles;
		}
		catch(HibernateException e) {
			if (t != null)t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteVehicle(int id) {
		Transaction t = null;
		try(Session session = sf.openSession()) {
			t = session.beginTransaction();
			Vehicle vehicle = session.get(Vehicle.class, id);
			session.delete(vehicle);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.commit();
			e.printStackTrace();
		}
		return false;
	}
		
	
	
	
}
