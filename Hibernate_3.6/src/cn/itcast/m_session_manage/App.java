package cn.itcast.m_session_manage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure("cn/itcast/m_session_manage/myhibernate.cfg.xml")//
			.buildSessionFactory();

	// Ҫ��ʹ��SessionFactory.getCurrentSession()����
	// ��Ҫ��Hibernate�������ļ�������current_session_context_class�
	// getCurrentSession()������
	// >> ȥָ�����������У���thread�����Ұ󶨵�Session��������оͷ��ء�
	// >> ���û�У��ʹ���һ�����󶨺ã�Ȼ�󷵻�
	// >> openSession()ֻ�ǿ���һ���µ�Session���������󶨺Ͳ��Ҳ�����
	@Test
	public void testSession() throws Exception {
		Session session1 = sessionFactory.getCurrentSession();
		Session session2 = sessionFactory.getCurrentSession();

		System.out.println(session1 != null); // true
		System.out.println(session1 == session2); // true
	}

	// ��ʹ��getCurrentSessionʱ��Hibernate�����ύ��ع����Զ��Ĺر�Session
	@Test
	public void testSessionClose() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		System.out.println("ʹ��Session��XXX����");

		session.getTransaction().commit();
		// session.close(); // ��ʹ��getCurrentSessionʱ���Ͳ������Լ��ر���
	}

	@Test
	public void testSession2() throws Exception {
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactory.openSession();

		System.out.println(session1 != null); // true
		System.out.println(session1 == session2); // false
	}

	@Test
	public void testSession3() throws Exception {
		Session session2 = sessionFactory.getCurrentSession();
		Session session1 = sessionFactory.openSession();

		System.out.println(session1 != null); // true
		System.out.println(session1 == session2); // false
	}
}
