package cn.itcast.b_dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cn.itcast.a_helloworld.User;

public class HibernateUtils {

	// SessionFactory全局只需要有一个就可以了
	private static SessionFactory sessionFactory;

	static {
		Configuration cfg = new Configuration();
		// cfg.configure(); // 读取默认的配置文件（hibernate.cfg.xml）
		// // cfg.configure("hibernate.cfg.xml"); // 读取指定位置的配置文件
		// sessionFactory = cfg.buildSessionFactory();

		// cfg.addResource("cn/itcast/a_helloworld/User.hbm.xml");
		// cfg.addClass(User.class); // 去User类所在的包中查找名称为User，后缀为.hbm.xml的文件

		// 初始化SessionFactory
		sessionFactory = new Configuration()//
				.configure()//
				.buildSessionFactory();
		
	}

	/**
	 * 获取全局唯一的SessionFactory
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 从全局唯一的SessionFactory中打开一个Session
	 * 
	 * @return
	 */
	public static Session openSession() {
		return sessionFactory.openSession();
	}
}
