package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import util.HibernateUtil;

public class Dao<T> {
	
	private final Class<T> cl;
	
	public Dao(Class<T> c){
		this.cl = c;
	}

	public T get(int id) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		T o = em.find(cl, id);
		em.getTransaction().commit();
		return o;
	}
	
	public List<T> getAll(){
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(cl);
		criteria.from(cl);
		List<T> list = em.createQuery(criteria).getResultList();
		em.getTransaction().commit();
		return list;
	}
	
	public void create(T t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}
	
	public void update(T t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
	}
	
	public void delete(T t) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}
}
