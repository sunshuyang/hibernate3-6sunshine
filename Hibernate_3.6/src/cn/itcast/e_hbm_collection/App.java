package cn.itcast.e_hbm_collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
		user.setName("����");
		// >> Set����
		user.setAddressSet(new TreeSet<String>()); // ��������sort����ʱ����Ҫʹ��SortedSet����
		user.getAddressSet().add("2������ó԰");
		user.getAddressSet().add("1�Ķ���·");
		// >> List����
		user.getAddressList().add("������ó԰");
		user.getAddressList().add("�Ķ���·");
		user.getAddressList().add("�Ķ���·");
		// >> ����
		user.setAddressArray(new String[] { "������ó԰", "�Ķ���·" });
		// >> Map����
		user.getAddressMap().put("��˾", "������ó԰");
		user.getAddressMap().put("��ͥ", "�Ķ���·");
		// >> Bag����
		user.getAddressBag().add("������ó԰");
		user.getAddressBag().add("�Ķ���·");
		user.getAddressBag().add("�Ķ���·");

		// ����
		session.save(user);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ��ȡ����
		User user = (User) session.get(User.class, 9);

		// ��ʾ��Ϣ
		System.out.println(user.getName());
		System.out.println(user.getAddressSet());
		System.out.println(user.getAddressList());
		System.out.println(Arrays.toString(user.getAddressArray()));
		System.out.println(user.getAddressMap());
		System.out.println(user.getAddressBag());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
