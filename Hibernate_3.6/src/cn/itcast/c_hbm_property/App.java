package cn.itcast.c_hbm_property;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration()//
				.configure()// 读取配置文件
				.addClass(User.class)//
				.buildSessionFactory();
	}

	@Test
	public void testSave() throws Exception {
		// 读取图片文件
		InputStream in = new FileInputStream( "c:/test.png");
		byte[] photo = new byte[in.available()];
		in.read(photo);
		in.close();
		
		// 创建对象实例
		User user = new User();
		user.setName("张三");
		user.setAge(20);
		user.setBirthday(new Date());
		user.setDesc("一大段的说明，此处省略5000字……");
		user.setPhoto(photo);

		// 保存
		Session session = sessionFactory.openSession(); // 打开一个新的Session
		Transaction tx = session.beginTransaction(); // 开始事务

		session.save(user);

		tx.commit(); // 提交事务
		session.close(); // 关闭Session，释放资源
	}

	
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		User user = (User) session.get(User.class, 4); // 获取
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getDesc());
		System.out.println(user.getPhoto());
		
		OutputStream out = new FileOutputStream("c:/copy.png");
		out.write(user.getPhoto());
		out.close();

		tx.commit();
		session.close();
	}
}
