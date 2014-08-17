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
			.addClass(Department.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(Employee.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// 测试一级缓存（Session缓存）
	@Test
	public void testSessionCache() throws Exception {
		// ===================== 第1个Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Employee employee = (Employee) session.get(Employee.class, 1); // 获取

		session.getTransaction().commit();
		session.close();

		// ===================== 第2个Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Employee employee2 = (Employee) session2.get(Employee.class, 1); // 获取

		session2.getTransaction().commit();
		session2.close();
	}

	// 测试二级缓存
	@Test
	public void testSecondCache() throws Exception {
		// ===================== 第1个Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Department department = (Department) session.get(Department.class, 1); // 获取
		System.out.println(department.getName());
		System.out.println(department.getEmployees());

		session.getTransaction().commit();
		session.close();

		// ===================== 第2个Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Department department2 = (Department) session2.get(Department.class, 1); // 获取
		System.out.println(department2.getName());
		System.out.println(department2.getEmployees());

		session2.getTransaction().commit();
		session2.close();
	}

	// 测试查询缓存
	// 当使用Query.list()时，默认不会使用二级缓存
	@Test
	public void testQueryCache() throws Exception {
		// ===================== 第1个Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Employee> list = session.createQuery("FROM Employee e WHERE e.id<10").list();
		System.out.println(list);

		Employee employee5 = (Employee) session.get(Employee.class, 5);
		System.out.println(employee5);

		session.getTransaction().commit();
		session.close();

		// ===================== 第2个Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		List<Employee> list2 = session2.createQuery("FROM Employee e WHERE e.id<10").list();
		System.out.println(list2);

		session2.getTransaction().commit();
		session2.close();
	}

	// 测试查询缓存
	// 在使用HQL方式的查询时，如果用iterate()方法，就会使用缓存。
	// 因为这个方法是先查询所有符合条件的id集合，再一个一个的按id查找数据，就能用上缓存了。
	// 但这个方法会有N+1次查询的问题，提升性能有限，不太常用。
	@Test
	public void testQueryCache2() throws Exception {
		// ===================== 第1个Session
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

		// ===================== 第2个Session
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
		// ===================== 第1个Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List list = session.createQuery("FROM Employee e WHERE e.id<18")//
				.setCacheable(true)// 是否使用查询缓存，需要在hibernate.cfg.xml中开启查询缓存才行
				.list();
		System.out.println(list);

		session.getTransaction().commit();
		session.close();
		System.out.println("\n------------------\n");

		// ===================== 第2个Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		List list2 = session2.createQuery("FROM Employee e WHERE e.id<18")//
				.setCacheable(true)// 是否使用查询缓存
				.list();
		System.out.println(list2);

		session2.getTransaction().commit();
		session2.close();
	}

	// 测试Update与 Delete格式的HQL语对二级缓存的影响
	// 会让二级缓存中相关的数据失效，下次使用这些数据时会重新到数据库中加载
	@Test
	public void testUpdateTimestampCache() throws Exception {
		// ===================== 第1个Session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// 先获取
		Employee employee = (Employee)session.get(Employee.class, 1);
		System.out.println(employee.getName());
		
		// 再直接更新DB
		session.createQuery("UPDATE Employee e SET e.name=? WHERE id=?")//
			.setParameter(0, "测试")//
			.setParameter(1, 1)//
			.executeUpdate();// 执行
		
		// 再显示这个员工的名称
		System.out.println(employee.getName());

		session.getTransaction().commit();
		session.close();
		System.out.println("\n------------------\n");

		// ===================== 第2个Session
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		Employee employee2 = (Employee)session2.get(Employee.class, 1);
		System.out.println(employee2.getName());


		session2.getTransaction().commit();
		session2.close();
	}
}
