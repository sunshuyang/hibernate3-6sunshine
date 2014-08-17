package cn.itcast.h_session_method;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.buildSessionFactory();

	// save()������ʱ״̬��Ϊ�־û�״̬������Sessioin����
	// �����ɣ�insert into ...
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = new User(); // ��ʱ״̬
		user.setName("test");
		session.save(user); // ��Ϊ�˳־û�״̬

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

		user.setName("����"); // ����״̬
		System.out.println(user.getName()); // ����״̬
	}

	// update()��������״̬��Ϊ�־û�״̬
	// �����ɣ�update ...
	// �ڸ���ʱ�����󲻴��ھͱ���
	@Test
	public void testUpdate() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = (User) session.get(User.class, 1);
		System.out.println(user.getName()); // �־û�״̬

		// session.clear(); // ���Session�����еĶ���
		session.evict(user); // ���Session��һ��ָ���Ķ���

		user.setName("newname3");
		session.update(user);
		System.out.println("----");
		// session.flush(); // ˢ�������ݿ�

		// --------------------------------------------
		session.getTransaction().commit(); // 
		session.close();
	}

	// saveOrUpdate()������ʱ������״̬תΪ�־û�״̬
	// �����ɣ�insert into �� update ...
	// �ڸ���ʱ�����󲻴��ھͱ���
	// �������Ǹ���id�ж϶�����ʲô״̬�ģ����idΪԭʼֵ���������null��ԭʼ����������0��������ʱ״̬���������ԭʼֵ��������״̬��
	@Test
	public void testSaveOrUpdate() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = new User();
		user.setId(3); // �Լ�����һ������״̬����
		user.setName("newName");

		session.saveOrUpdate(user);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// delete()���ѳ־û�������תΪɾ��״̬
	// �����ɣ�delete ...
	// ���ɾ���Ķ��󲻴��ڣ��ͻ����쳣
	@Test
	public void testDelete() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// User user = (User) session.get(User.class, 2); // �־û�

		User user = new User();
		user.setId(300);

		session.delete(user);
		session.flush();

		System.out.println("---");

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// get()����ȡ���ݣ��ǳ־û�״̬
	// �����ɣ�select ... where id=?
	// ������ִ��sql���
	// ������ݲ����ڣ��ͷ���null
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = (User) session.get(User.class, 5); // �־û�
		System.out.println(user.getClass());
		// System.out.println("---");
		// System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// load()����ȡ���ݣ��ǳ־û�״̬
	// �����ɣ�select ... where id=?
	// load()�󷵻ص���һ���������Ҫ���಻����final�ģ�������������������Ͳ���ʹ�������ع����ˡ�
	// ��������ʧЧ�ķ�ʽ��һ����ʵ��д��final�ģ�������hbm.xml��д<class ... lazy="false">
	// ��������ִ��sql��䣬�����ڵ�1��ʹ�÷�id��class����ʱִ��sql��
	// ������ݲ����ڣ������쳣��ObjectNotFoundException
	@Test
	public void testLoad() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = (User) session.load(User.class, 5);
		System.out.println(user.getClass());
		System.out.println("---");
		System.out.println(user.getId());
		System.out.println(user.getName());
		// System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// �����������ݣ�Ҫ��ֹSession�ж��������ڴ����
	@Test
	public void testBatchSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		for (int i = 0; i < 30; i++) {
			User user = new User();
			user.setName("����");
			session.save(user);

			if (i % 10 == 0) {
				session.flush(); // ��ˢ��
				session.clear(); // �����
			}
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void test2() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		User user = (User) session.get(User.class, 5); // �־û�
		System.out.println(user.getName());

		// session.clear();
		// user = (User) session.get(User.class, 5); // �־û�

		session.refresh(user); // ˢ��Session�����ж����״̬��������selectһ��
		System.out.println(user.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
