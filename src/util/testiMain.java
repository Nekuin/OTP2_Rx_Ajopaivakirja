package util;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.SessionFactory;

import model.*;
import util.HibernateUtil;

public class testiMain {

	public static void main(String[] args) {
		
		//Hascode testi

		SessionFactory factory = HibernateUtil.getSessionFactory();

		System.out.println("Session 1: " + factory.hashCode());
		System.out.println("Session 2: " + factory.hashCode());
		Set<DrivingShift> s = new HashSet<>();
		ICargo cargo = new Cargo();
		IClient client = new Client();
		s.add(new DrivingShift(client, cargo));
		s.forEach(System.out::println);
	}
}
