package cn.itcast.k_query_hql;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
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

	// ׼������
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ����һЩ��������
		for (int i = 1; i <= 10; i++) {
			Department department = new Department();
			department.setName("������_" + i);
			session.save(department);
		}

		// ����һЩԱ������
		for (int i = 1; i <= 20; i++) {
			Employee employee = new Employee();
			employee.setName("��XX_" + i);
			session.save(employee);
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// ʹ��HQL��ѯ
	// HQL: Hibernate Query Language.
	// �ص㣺
	// >> 1����SQL���ƣ�SQL�е��﷨�����϶�����ֱ��ʹ�á�
	// >> 2��SQL��ѯ���Ǳ�ͱ��е��У�HQL��ѯ���Ƕ���������е����ԡ�
	// >> 3��HQL�Ĺؼ��ֲ����ִ�Сд�������������������ִ�Сд�ġ�
	// >> 4��SELECT����ʡ��.
	@Test
	public void testHql() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------
		String hql = null;

		// 1���򵥵Ĳ�ѯ
		// hql = "FROM Employee";
		// hql = "FROM Employee AS e"; // ʹ�ñ���
		// hql = "FROM Employee e"; // ʹ�ñ�����as�ؼ��ֿ�ʡ��

		// 2�����Ϲ��������ģ�����ʹ�ñ�������Where
		// hql = "FROM Employee WHERE id<10";
		// hql = "FROM Employee e WHERE e.id<10";
		// hql = "FROM Employee e WHERE e.id<10 AND e.id>5";

		// 3���������������ģ�Order By
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name";
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name DESC";
		// hql = "FROM Employee e WHERE e.id<10 ORDER BY e.name DESC, id ASC";

		// 4��ָ��select�Ӿ䣨������ʹ��select *��
		// hql = "SELECT e FROM Employee e"; // �൱��"FROM Employee e"
		// hql = "SELECT e.name FROM Employee e"; // ֻ��ѯһ���У����صļ��ϵ�Ԫ�����;���������Ե�����
		// hql = "SELECT e.id,e.name FROM Employee e"; // ��ѯ����У����صļ��ϵ�Ԫ��������Object����
		// hql = "SELECT new Employee(e.id,e.name) FROM Employee e"; // ����ʹ��new�﷨��ָ���Ѳ�ѯ���Ĳ������Է�װ��������

		// 5��ִ�в�ѯ����ý����list��uniqueResult����ҳ ��
		// Query query = session.createQuery("FROM Employee e WHERE id<3");
		// query.setFirstResult(0);
		// query.setMaxResults(10);
		// // List list = query.list(); // ��ѯ�Ľ����һ��List����
		// Employee employee = (Employee) query.uniqueResult();// ��ѯ�Ľ����Ψһ��һ�������������ж�����ͻ����쳣
		// System.out.println(employee);

		// 6��������
		List list = session.createQuery(//
				"FROM Employee e")//
				.setFirstResult(0)//
				.setMaxResults(10)//
				.list();

		// // ----- ִ�в�ѯ
		// List list = session.createQuery(hql).list();
		//
		// ----- ��ʾ���
		for (Object obj : list) {
			if (obj.getClass().isArray()) {
				System.out.println(Arrays.toString((Object[]) obj));
			} else {
				System.out.println(obj);
			}
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testHql_2() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------
		String hql = null;

		// 1���ۼ�������count(), max(), min(), avg(), sum()
		// hql = "SELECT COUNT(*) FROM Employee"; // ���صĽ����Long�͵�
		// hql = "SELECT min(id) FROM Employee"; // ���صĽ����id���Ե�����
		// Number result = (Number) session.createQuery(hql).uniqueResult();
		// System.out.println(result.getClass());
		// System.out.println(result);

		// 2������: Group By ... Having
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e GROUP BY e.name";
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e GROUP BY e.name HAVING count(e.id)>1";
		// hql = "SELECT e.name,COUNT(e.id) FROM Employee e WHERE id<9 GROUP BY e.name HAVING count(e.id)>1";
		// ---
		// hql = "SELECT e.name,COUNT(e.id) " + //
		// "FROM Employee e " + //
		// "WHERE id<9 " + //
		// "GROUP BY e.name " + //
		// "HAVING count(e.id)>1 " + //
		// "ORDER BY count(e.id) ASC";
		// ---
		// hql = "SELECT e.name,COUNT(e.id) AS c " + //
		// "FROM Employee e " + //
		// "WHERE id<9 " + //
		// "GROUP BY e.name " + //
		// "HAVING count(e.id)>1 " + // ��having�Ӿ��в���ʹ���б���
		// "ORDER BY c ASC"; // ��orderby�Ӿ��п���ʹ���б���

		// 3�����Ӳ�ѯ / HQL���������Ĳ�ѯ
		// >> �����ӣ�inner�ؼ��ֿ���ʡ�ԣ�
		// hql = "SELECT e.id,e.name,d.name FROM Employee e JOIN e.department d";
		// hql = "SELECT e.id,e.name,d.name FROM Employee e INNER JOIN e.department d";
		// >> �������ӣ�outer�ؼ��ֿ���ʡ�ԣ�
		// hql = "SELECT e.id,e.name,d.name FROM Employee e LEFT OUTER JOIN e.department d";
		// >> �������ӣ�outer�ؼ��ֿ���ʡ�ԣ�
		// hql = "SELECT e.id,e.name,d.name FROM Employee e RIGHT JOIN e.department d";
		// ����ʹ�ø�����ķ���
		// hql = "SELECT e.id,e.name,e.department.name FROM Employee e";

		// 4����ѯʱʹ�ò���
		// >> ��ʽһ��ʹ��'?'ռλ
		// hql = "FROM Employee e WHERE id BETWEEN ? AND ?";
		// List list = session.createQuery(hql)//
		// .setParameter(0, 5)// ���ò�������1������������Ϊ0��
		// .setParameter(1, 15)//
		// .list();

		// >> ��ʽ����ʹ�ñ�����
		// hql = "FROM Employee e WHERE id BETWEEN :idMin AND :idMax";
		// List list = session.createQuery(hql)//
		// .setParameter("idMax", 15)//
		// .setParameter("idMin", 5)//
		// .list();

		// �������Ǽ���ʱ��һ��Ҫʹ��setParameterList()���ò���ֵ
		// hql = "FROM Employee e WHERE id IN (:ids)";
		// List list = session.createQuery(hql)//
		// .setParameterList("ids", new Object[] { 1, 2, 3, 5, 8, 100 })//
		// .list();

		// 5��ʹ��������ѯ
		// Query query = session.getNamedQuery("queryByIdRange");
		// query.setParameter("idMin", 3);
		// query.setParameter("idMax", 10);
		// List list = query.list();

		// 6��update��delete������֪ͨSession����
		// >> Update
		// int result = session.createQuery(//
		// "UPDATE Employee e SET e.name=? WHERE id>15")//
		// .setParameter(0, "������")//
		// .executeUpdate(); // ����int�͵Ľ������ʾӰ���˶����С�
		// System.out.println("result = " + result);
		// >> Delete
		int result = session.createQuery(//
				"DELETE FROM Employee e WHERE id>15")//
				.executeUpdate(); // ����int�͵Ľ������ʾӰ���˶����С�
		System.out.println("result = " + result);

		// ----- ִ�в�ѯ����ʾ���
		// // List list = session.createQuery(hql).list();
		// for (Object obj : list) {
		// if (obj.getClass().isArray()) {
		// System.out.println(Arrays.toString((Object[]) obj));
		// } else {
		// System.out.println(obj);
		// }
		// }

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testHql_DML() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ��һ����ʾ����
		Employee employee = (Employee) session.get(Employee.class, 13);
		System.out.println(employee.getName());

		// update��delete������֪ͨSession����
		int result = session.createQuery(//
				"UPDATE Employee e SET e.name=? WHERE id>10")//
				.setParameter(0, "������3")//
				.executeUpdate(); // ����int�͵Ľ������ʾӰ���˶����С�

		// �ڶ�����ʾ����
		// ��update��delete����Ҫrefresh(obj)һ���Ի�ȡ���µ�״̬
		session.refresh(employee);
		System.out.println(employee.getName());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
