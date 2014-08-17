package cn.itcast.f_hbm_oneToMany;

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

	// ���棬�й�����ϵ
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// �½�����
		Department department = new Department();
		department.setName("������");

		Employee employee1 = new Employee();
		employee1.setName("����");

		Employee employee2 = new Employee();
		employee2.setName("����");

		// ��������
		employee1.setDepartment(department);
		employee2.setDepartment(department);
		department.getEmployees().add(employee1);
		department.getEmployees().add(employee2);

		// ����
		// session.save(employee1);
		// session.save(employee2);
		session.save(department); // ���沿��

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// ��ȡ�����Ի�ȡ�������ĶԷ�
	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ��ȡһ��������ʾ��һ����Ϣ
		Department department = (Department) session.get(Department.class, 1);
		System.out.println(department);
		System.out.println(department.getEmployees());

		Employee employee = (Employee) session.get(Employee.class, 1);
		System.out.println(employee);
		System.out.println(employee.getDepartment());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// ���������ϵ
	@Test
	public void testRemoveRelation() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// // ��Ա�������
		// Employee employee = (Employee) session.get(Employee.class, 1);
		// employee.setDepartment(null);

		// �Ӳ��ŷ��������inverse�й�ϵ��Ϊfalseʱ���Խ����
		Department department = (Department) session.get(Department.class, 1);
		department.getEmployees().clear();

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// ɾ�����󣬶Թ��������Ӱ��
	@Test
	public void testDelete() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// // ɾ��Ա�������෽�����ԶԷ�û��Ӱ��
		// Employee employee = (Employee) session.get(Employee.class,2);
		// session.delete(employee);

		// ɾ�����ŷ���һ����
		// a, ���û�й�����Ա������ɾ����
		// b, ����й�����Ա����inverse=true�����ڲ���ά��������ϵ�����Ի�ֱ��ִ��ɾ�����ͻ����쳣
		// c, ����й�����Ա����inverse=false�����ڿ���ά��������ϵ�����ͻ��Ȱѹ�����Ա�����������Ϊnullֵ����ɾ���Լ���
		Department department = (Department) session.get(Department.class, 4);
		session.delete(department);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testLazy() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		Department department = (Department) session.get(Department.class, 1);
		// Department department2 = (Department) session.get(Department.class, 1);

		System.out.println(department.getName());
		// System.out.println(department.getEmployees().size());

		Hibernate.initialize(department.getEmployees()); // ��������ָ���������ض���

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

		// ��ʹ������������ʱ��Ҫע��LazyInitializationException�쳣��
		System.out.println(department.getEmployees());
	}

}
