package in.co.rays.proj3.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate DataSource for Data Connection Pool
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 *
 */

public class HibernateDataSource {

	private static SessionFactory sessionFactory;

	/**
	 * Get the instance of Session Factory
	 *
	 * @return sessionFactory   
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * Get Session by SessionFactory
	 *
	 * @return session
	 */
	public static Session getSession() {
		Session session = getSessionFactory().openSession();
		return session;
	}

	/**
	 * Close Session by SessionFactory
	 *
	 * @return session
	 */
	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

}
