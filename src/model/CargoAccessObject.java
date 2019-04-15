package model;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;
//NOT IN USE
public class CargoAccessObject {
	
	SessionFactory sf;
	
	public CargoAccessObject() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public boolean createCargo(Cargo cargo) {
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(cargo);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateCargo(Cargo cargo) {
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(cargo);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public Cargo readCargo(int id) {
		Cargo cargo = new Cargo();
		Transaction t = null;
		try(Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.load(cargo, id);
			t.commit();
			return cargo;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Cargo> readCargo(){
		Transaction t = null;
		List<Cargo> cargo = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			CriteriaBuilder build = session.getCriteriaBuilder();
			CriteriaQuery<Cargo> query = build.createQuery(model.Cargo.class);
			query.from(model.Cargo.class);
			cargo = session.createQuery(query).getResultList();
			t.commit();
			return cargo;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteCargo(int id) {
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			Cargo cargo = session.get(Cargo.class, id);
			session.remove(cargo);
			t.commit();
			return true;
		}
		catch(HibernateException e) { 
			if (t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}

}
