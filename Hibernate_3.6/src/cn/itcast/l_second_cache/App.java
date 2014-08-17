package cn.itcast.l_second_cache;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Department.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.addClass(Employee.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.buildSessionFactory();

	// ����һ�����棨Session���棩
	@Test
	public void testSessionCache() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Employee employee = (Employee) session.get(Employee.class, 1); // ��ȡ

		session.getTransaction().commit();
		session.close();

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Employee employee2 = (Employee) session2.get(Employee.class, 1); // ��ȡ

		session2.getTransaction().commit();
		session2.close();
	}

	// ���Զ�������
	@Test
	public void testSecondCache() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Department department = (Department) session.get(Department.class, 1); // ��ȡ
		System.out.println(department.getName());
		System.out.println(department.getEmployees());

		session.getTransaction().commit();
		session.close();

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Department department2 = (Department) session2.get(Department.class, 1); // ��ȡ
		System.out.println(department2.getName());
		System.out.println(department2.getEmployees());

		session2.getTransaction().commit();
		session2.close();
	}

	// ���Բ�ѯ����
	// ��ʹ��Query.list()ʱ��Ĭ�ϲ���ʹ�ö�������
	@Test
	public void testQueryCache() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Employee> list = session.createQuery("FROM Employee e WHERE e.id<10").list();
		System.out.println(list);

		Employee employee5 = (Employee) session.get(Employee.class, 5);
		System.out.println(employee5);

		session.getTransaction().commit();
		session.close();

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		List<Employee> list2 = session2.createQuery("FROM Employee e WHERE e.id<10").list();
		System.out.println(list2);

		session2.getTransaction().commit();
		session2.close();
	}

	// ���Բ�ѯ����
	// ��ʹ��HQL��ʽ�Ĳ�ѯʱ�������iterate()�������ͻ�ʹ�û��档
	// ��Ϊ����������Ȳ�ѯ���з���������id���ϣ���һ��һ���İ�id�������ݣ��������ϻ����ˡ�
	// �������������N+1�β�ѯ�����⣬�����������ޣ���̫���á�
	@Test
	public void testQueryCache2() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Iterator<Employee> iterator = session.createQuery("FROM Employee e WHERE e.id<8").iterate();
		while (iterator.hasNext()) {
			Employee e = iterator.next();
			System.out.println(e);
		}

		Employee employee5 = (Employee) session.get(Employee.class, 5);
		System.out.println(employee5);

		session.getTransaction().commit();
		session.close();
		System.out.println("\n------------------\n");

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Iterator<Employee> iterator2 = session2.createQuery("FROM Employee e WHERE e.id<10").iterate();
		while (iterator2.hasNext()) {
			Employee e = iterator2.next();
			System.out.println(e);
		}

		session2.getTransaction().commit();
		session2.close();
	}

	@Test
	public void testQueryCache3() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List list = session.createQuery("FROM Employee e WHERE e.id<18")//
				.setCacheable(true)// �Ƿ�ʹ�ò�ѯ���棬��Ҫ��hibernate.cfg.xml�п�����ѯ�������
				.list();
		System.out.println(list);

		session.getTransaction().commit();
		session.close();
		System.out.println("\n------------------\n");

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		List list2 = session2.createQuery("FROM Employee e WHERE e.id<18")//
				.setCacheable(true)// �Ƿ�ʹ�ò�ѯ����
				.list();
		System.out.println(list2);

		session2.getTransaction().commit();
		session2.close();
	}

	// ����Update�� Delete��ʽ��HQL��Զ��������Ӱ��
	// ���ö�����������ص�����ʧЧ���´�ʹ����Щ����ʱ�����µ����ݿ��м���
	@Test
	public void testUpdateTimestampCache() throws Exception {
		// ===================== ��1��Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// �Ȼ�ȡ
		Employee employee = (Employee)session.get(Employee.class, 1);
		System.out.println(employee.getName());
		
		// ��ֱ�Ӹ���DB
		session.createQuery("UPDATE Employee e SET e.name=? WHERE id=?")//
			.setParameter(0, "����")//
			.setParameter(1, 1)//
			.executeUpdate();// ִ��
		
		// ����ʾ���Ա��������
		System.out.println(employee.getName());

		session.getTransaction().commit();
		session.close();
		System.out.println("\n------------------\n");

		// ===================== ��2��Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Employee employee2 = (Employee)session2.get(Employee.class, 1);
		System.out.println(employee2.getName());


		session2.getTransaction().commit();
		session2.close();
	}
}
