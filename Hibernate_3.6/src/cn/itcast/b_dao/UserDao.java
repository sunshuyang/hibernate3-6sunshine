package cn.itcast.b_dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.a_helloworld.User;

public class UserDao {

	/**
	 * 保存
	 * 
	 * @param user
	 */
	public void save(User user) {
		Session session = HibernateUtils.openSession();
		try {
			Transaction tx = session.beginTransaction(); // 开始事务
			session.save(user);
			tx.commit(); // 提交事务
		} catch (RuntimeException e) {
			session.getTransaction().rollback(); // 回滚事务
			throw e;
		} finally {
			session.close(); // 关闭Session
		}
	}

	/**
	 * 更新
	 * 
	 * @param user
	 */
	public void update(User user) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(user); // 操作

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(int id) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Object user = session.get(User.class, id); // 要先获取到这个对象
			session.delete(user); // 删除的是实体对象

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据id查询一个User数据
	 * 
	 * @param id
	 * @return
	 */
	public User getById(int id) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, id); // 操作
			tx.commit();
			return user;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<User> findAll() {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// 方式一：使用HQL查询
//			List<User> list = session.createQuery("FROM User").list(); 
			
			// 方式二：使用Criteria查询
			Criteria criteria = session.createCriteria(User.class);
			List<User> list = criteria.list();
			
			tx.commit();
			return list;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * * 分页的查询数据列表
	 * 
	 * @param firstResult
	 *            从结果列表中的哪个索引开始取数据
	 * @param maxResults
	 *            最多取多少条数据
	 * @return 一页的数据列表 + 总记录数
	 */
	@SuppressWarnings("unchecked")
	public QueryResult findAll(int firstResult, int maxResults) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// 查询一页的数据列表
			// 方式一：
			// Query query = session.createQuery("FROM User");
			// query.setFirstResult(firstResult);
			// query.setMaxResults(maxResults);
			// List<User> list = query.list();
			// 方式二：方法链
			List<User> list = session.createQuery(//
					"FROM User")//
					.setFirstResult(firstResult)//
					.setMaxResults(maxResults)//
					.list();

			// 查询总记录数
			Long count = (Long) session.createQuery(//
					"SELECT COUNT(*) FROM User")//
					.uniqueResult();
			tx.commit();
			
			//  返回结果
			return new QueryResult(count.intValue(), list);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
