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
				.configure()// ��ȡ�����ļ�
				.addClass(User.class)//
				.buildSessionFactory();
	}

	@Test
	public void testSave() throws Exception {
		// ��ȡͼƬ�ļ�
		InputStream in = new FileInputStream( "c:/test.png");
		byte[] photo = new byte[in.available()];
		in.read(photo);
		in.close();
		
		// ��������ʵ��
		User user = new User();
		user.setName("����");
		user.setAge(20);
		user.setBirthday(new Date());
		user.setDesc("һ��ε�˵�����˴�ʡ��5000�֡���");
		user.setPhoto(photo);

		// ����
		Session session = sessionFactory.openSession(); // ��һ���µ�Session
		Transaction tx = session.beginTransaction(); // ��ʼ����

		session.save(user);

		tx.commit(); // �ύ����
		session.close(); // �ر�Session���ͷ���Դ
	}

	
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		User user = (User) session.get(User.class, 4); // ��ȡ
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
