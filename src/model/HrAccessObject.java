package model;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class HrAccessObject {
	
	private SessionFactory sf;
	
	public HrAccessObject() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public boolean createHrManager(HrManager manager) {
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(manager);
			t.commit();
			return true;
		} catch (HibernateException e) {
			//if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public HrManager readHrManager(int id) {
		HrManager manager = new HrManager();
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.load(manager, id);
			t.commit();
			return manager;
		} catch (HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<HrManager> readHrManager(){
		Transaction t = null;
		List<HrManager> managers = null;
		try(Session session = sf.openSession()) {
			t = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<HrManager> criteria = builder.createQuery(model.HrManager.class);
			criteria.from(model.HrManager.class);
			managers = session.createQuery(criteria).getResultList();
			t.commit();
			return managers;
		} catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
}
