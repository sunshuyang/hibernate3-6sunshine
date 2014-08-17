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
			.addClass(Department.class)// 添加Hibernate实体类（加载对应的映射文件）
			.addClass(Employee.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	// 准备数据
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 保存一些部门数据
		for (int i = 1; i <= 10; i++) {
			Department department = new Department();
			department.setName("开发部_" + i);
			session.save(department);
		}

		// 保存一些员工数据
		for (int i = 1; i <= 20; i++) {
			Employee employee = new Employee();
			employee.setName("李XX_" + i);
			session.save(employee);
		}

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	// 使用QBC方式查询：Query By Criteria
	@Test
	public void testQBC() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 创建Criteria对象
		Criteria criteria = session.createCriteria(Employee.class);
		// 增加过滤条件
		criteria.add(Restrictions.ge("id", 1));
		criteria.add(Restrictions.le("id", 5));
		// 增加排序条件
		criteria.addOrder(Order.desc("name"));
		criteria.addOrder(Order.desc("id"));
		// 执行查询
		// criteria.setFirstResult(0);
		// criteria.setMaxResults(100);
		// criteria.uniqueResult();
		// criteria.list()
		List list = criteria.list();
		// 显示结果
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
