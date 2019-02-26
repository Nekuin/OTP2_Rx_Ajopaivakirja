package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import util.HibernateUtil;

public class HrAccessObject implements IDao<HrManager>{
	
	
	public HrAccessObject() {
	}


	@Override
	public HrManager get(int id) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		HrManager m = em.find(model.HrManager.class, id);
		em.getTransaction().commit();
		return m;
	}

	@Override
	public List<HrManager> getAll() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<HrManager> criteria = builder.createQuery(model.HrManager.class);
		criteria.from(model.HrManager.class);
		List<HrManager> managers = em.createQuery(criteria).getResultList();
		em.getTransaction().commit();
		return managers;
	}

	@Override
	public void create(HrManager t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}

	@Override
	public void update(HrManager t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
	}

	@Override
	public void delete(HrManager t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		HrManager manager = get(t.getEmployeeID());
		em.getTransaction().begin();
		em.remove(manager);
		em.getTransaction().commit();
	}
}
