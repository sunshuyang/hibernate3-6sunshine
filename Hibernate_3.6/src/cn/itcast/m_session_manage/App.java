package cn.itcast.m_session_manage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure("cn/itcast/m_session_manage/myhibernate.cfg.xml")//
			.buildSessionFactory();

	// 要想使用SessionFactory.getCurrentSession()方法
	// 需要在Hibernate主配置文件中配置current_session_context_class项。
	// getCurrentSession()方法：
	// >> 去指定的上下文中（如thread）查找绑定的Session对象，如果有就返回。
	// >> 如果没有，就创建一个并绑定好，然后返回
	// >> openSession()只是开启一个新的Session，不会做绑定和查找操作。
	@Test
	public void testSession() throws Exception {
		Session session1 = sessionFactory.getCurrentSession();
		Session session2 = sessionFactory.getCurrentSession();

		System.out.println(session1 != null); // true
		System.out.println(session1 == session2); // true
	}

	// 当使用getCurrentSession时，Hibernate会在提交或回滚后自动的关闭Session
	@Test
	public void testSessionClose() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		System.out.println("使用Session做XXX操作");

		session.getTransaction().commit();
		// session.close(); // 当使用getCurrentSession时，就不能再自己关闭了
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
