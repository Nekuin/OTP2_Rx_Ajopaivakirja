package util;

import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class testiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		System.out.println("Session 1: " + factory.hashCode());
		System.out.println("Session 2: " + factory.hashCode());

	}

}
