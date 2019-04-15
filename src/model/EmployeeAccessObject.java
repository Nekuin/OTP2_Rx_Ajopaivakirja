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
public class EmployeeAccessObject {

	private SessionFactory sf;
	
	public EmployeeAccessObject() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public boolean createEmployee(Employee employee) {
		Transaction t = null;
		try (Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(employee);
			t.commit();
			return true;
		}
		catch(HibernateException e){
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateEmployee(Employee employee) {
		Transaction t = null;
		try (Session session = sf.openSession()){
			t = session.beginTransaction();
			session.saveOrUpdate(employee);
			t.commit();
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public Employee readEmployee (int id) {
		Employee employee = new Employee();
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			session.load(employee, id);
			t.commit();
			return employee;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Employee> readEmployee(){
		List<Employee> employees = null;
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			CriteriaBuilder build = session.getCriteriaBuilder();
			CriteriaQuery<Employee> query = build.createQuery(model.Employee.class);
			query.from(model.Employee.class);
			employees = session.createQuery(query).getResultList();
			t.commit();
			return employees;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteEmployee(int id) {
		Transaction t = null;
		try(Session session = sf.openSession()){
			t = session.beginTransaction();
			Employee employee = session.get(Employee.class, id);
			session.delete(employee);
			return true;
		}
		catch(HibernateException e) {
			if(t != null) t.rollback();
			e.printStackTrace();
		}
		return false;
	}
		
}
