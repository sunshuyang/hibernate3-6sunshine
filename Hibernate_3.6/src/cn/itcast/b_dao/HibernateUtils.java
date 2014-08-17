package cn.itcast.b_dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cn.itcast.a_helloworld.User;

public class HibernateUtils {

	// SessionFactoryȫ��ֻ��Ҫ��һ���Ϳ�����
	private static SessionFactory sessionFactory;

	static {
		Configuration cfg = new Configuration();
		// cfg.configure(); // ��ȡĬ�ϵ������ļ���hibernate.cfg.xml��
		// // cfg.configure("hibernate.cfg.xml"); // ��ȡָ��λ�õ������ļ�
		// sessionFactory = cfg.buildSessionFactory();

		// cfg.addResource("cn/itcast/a_helloworld/User.hbm.xml");
		// cfg.addClass(User.class); // ȥUser�����ڵİ��в�������ΪUser����׺Ϊ.hbm.xml���ļ�

		// ��ʼ��SessionFactory
		sessionFactory = new Configuration()//
				.configure()//
				.buildSessionFactory();
		
	}

	/**
	 * ��ȡȫ��Ψһ��SessionFactory
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * ��ȫ��Ψһ��SessionFactory�д�һ��Session
	 * 
	 * @return
	 */
	public static Session openSession() {
		return sessionFactory.openSession();
	}
}
