package util;

import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class testiMain {

	public static void main(String[] args) {

		SessionFactory factory = HibernateUtil.getSessionFactory();

		System.out.println("Session 1: " + factory.hashCode());
		System.out.println("Session 2: " + factory.hashCode());
	}
}
