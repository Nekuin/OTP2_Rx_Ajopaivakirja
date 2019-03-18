package model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import util.HibernateUtil;

public class DriverAccessObject implements IDao<Driver>{
	

	@Override
	public Driver get(int id) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		Driver d = em.find(model.Driver.class, id);
		em.getTransaction().commit();
		return d;
	}

	@Override
	public List<Driver> getAll() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Driver> criteria = builder.createQuery(model.Driver.class);
		criteria.from(model.Driver.class);
		List<Driver> drivers = em.createQuery(criteria).getResultList();
		em.getTransaction().commit();
		return drivers;
	}

	@Override
	public void create(Driver driver) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(driver);
		em.getTransaction().commit();
	}

	@Override
	public void update(Driver driver) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(driver);
		em.getTransaction().commit();
	}

	@Override
	public void delete(Driver driver) {
		EntityManager em = HibernateUtil.getEntityManager();
		Driver d = get(driver.getEmployeeID());
		em.getTransaction().begin();
		em.remove(d);
		em.getTransaction().commit();
	}


}
