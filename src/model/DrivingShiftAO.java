package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.*;

import util.HibernateUtil;

public class DrivingShiftAO implements IDao<DrivingShift>{

	private org.hibernate.SessionFactory sf;

	public DrivingShiftAO() {
		//this.sf = HibernateUtil.getSessionFactory();
	}

	public boolean createDrivingShift(DrivingShift dShift) {
		Transaction t = null;

		try (Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.saveOrUpdate(dShift);
			t.commit();
			return true;
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateDrivingShift(DrivingShift dShift) {
		Transaction t = null;

		try (Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.saveOrUpdate(dShift);
			t.commit();
			return true;
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public DrivingShift readDrivingShift(int ID) {
		DrivingShift shift = new DrivingShift();
		Transaction t = null;
//j
		try (Session session = sf.openSession()) {
			t = session.beginTransaction();
			session.load(shift, ID);
			t.commit();
			return shift;
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
		return null;
	}

	public List<DrivingShift> readDrivingShift() {
		List<DrivingShift> shifts = null;
		Transaction t = null;
		try (Session session = sf.openSession()) {
			t = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<DrivingShift> criteria = builder.createQuery(model.DrivingShift.class);
			criteria.from(model.DrivingShift.class);
			shifts = session.createQuery(criteria).getResultList();
			t.commit();
			return shifts;
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteDrivingShift(int ID) {
		Transaction t = null;
		try (Session session = sf.openSession()) {
			t = session.beginTransaction();
			DrivingShift d = session.get(DrivingShift.class, ID);
			session.delete(d);
			t.commit();
			return true;
		} catch (HibernateException e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public DrivingShift get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DrivingShift> getAll() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DrivingShift> criteria = builder.createQuery(model.DrivingShift.class);
		criteria.from(model.DrivingShift.class);
		List<DrivingShift> shifts = em.createQuery(criteria).getResultList();
		return shifts;
	}

	@Override
	public void create(DrivingShift t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}

	@Override
	public void update(DrivingShift t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(DrivingShift t) {
		// TODO Auto-generated method stub
		
	}

}
