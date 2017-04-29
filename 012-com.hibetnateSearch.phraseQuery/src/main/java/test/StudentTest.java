package test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


import model.Address;
import model.Lesson;
import model.Student;
import session.JpaEntityManagerFactory;

public class StudentTest {
	
	public static void main(String[] args) {
		
		EntityManager entityManager = JpaEntityManagerFactory.buildEntityManager();
		EntityTransaction transaction = JpaEntityManagerFactory.transaction();
		
		Lesson lesson1 = new Lesson("Matematik", "Ahmet");
		Lesson lesson2 = new Lesson("Fizik", "Burak");
		Lesson lesson3 = new Lesson("Kimya", "Hamza");
		
		Set<Lesson> lessons = new HashSet<>();
		lessons.add(lesson3);
		lessons.add(lesson2);
		lessons.add(lesson1);
		
		Set<String> phoneNumbers1 = new HashSet<>();
		phoneNumbers1.add("05555555555");
		phoneNumbers1.add("04444444444");
		phoneNumbers1.add("03333333333");
		
		Set<String> phoneNumbers2 = new HashSet<>();
		phoneNumbers2.add("02222222222");
		phoneNumbers2.add("01111111111");
		
		Address address1 = new Address("Meydan Mahallesi", "K�tahya");
		Address address2 = new Address("�anakkale Mahallesi", "Adana");
		
		
		String criteria1 = "Java bilen, iyi derecede sql yazabilen, ingilizcesi iyi olan, 5 y�ll�k deneyim sahibi olan, muhendislik fak�ltesi mezunu olan ";
		
		String criteria2 = "C# bilen, iyi derecede mssql yazabilen, ingilizcesi orta olan, 5 y�ll�k deneyim sahibi olan, bilgisayar fak�ltesi mezunu olan ";
		
		Student ogrenci = new Student("Ferhat", "Aykan", new Date(), lessons, phoneNumbers1, "aykanferhat@gmail.com", address1, criteria1);
		Student ogrenci1 = new Student("Ahmet", "Deniz", new Date(), lessons, phoneNumbers2, "kitaplar", address2, criteria2);
		
		transaction.begin();
		entityManager.persist(address2);
		entityManager.persist(address1);
		entityManager.persist(lesson1);
		entityManager.persist(lesson2);
		entityManager.persist(lesson3);
		entityManager.persist(ogrenci);
		entityManager.persist(ogrenci1);
		transaction.commit();
	
	}

}
