package cn.itcast.d_hbm_id;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ��������
		User user = new User();
		// user.setId(UUID.randomUUID().toString());
		user.setName("����");

		// ����
		session.save(user);
		// session.save(new User());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
