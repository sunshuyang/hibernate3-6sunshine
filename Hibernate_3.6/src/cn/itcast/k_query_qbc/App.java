package cn.itcast.k_query_qbc;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

	// ʹ��QBC��ʽ��ѯ��Query By Criteria
	@Test
	public void testQBC() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ����Criteria����
		Criteria criteria = session.createCriteria(Employee.class);
		// ���ӹ�������
		criteria.add(Restrictions.ge("id", 1));
		criteria.add(Restrictions.le("id", 5));
		// ������������
		criteria.addOrder(Order.desc("name"));
		criteria.addOrder(Order.desc("id"));
		// ִ�в�ѯ
		// criteria.setFirstResult(0);
		// criteria.setMaxResults(100);
		// criteria.uniqueResult();
		// criteria.list()
		List list = criteria.list();
		// ��ʾ���
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

}
