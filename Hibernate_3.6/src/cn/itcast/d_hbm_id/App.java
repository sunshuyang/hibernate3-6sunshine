package cn.itcast.d_hbm_id;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 构建对象
		User user = new User();
		// user.setId(UUID.randomUUID().toString());
		user.setName("张三");

		// 保存
		session.save(user);
		// session.save(new User());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
