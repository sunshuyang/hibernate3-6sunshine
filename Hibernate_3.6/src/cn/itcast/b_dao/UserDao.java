package cn.itcast.b_dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.a_helloworld.User;

public class UserDao {

	/**
	 * ����
	 * 
	 * @param user
	 */
	public void save(User user) {
		Session session = HibernateUtils.openSession();
		try {
			Transaction tx = session.beginTransaction(); // ��ʼ����
			session.save(user);
			tx.commit(); // �ύ����
		} catch (RuntimeException e) {
			session.getTransaction().rollback(); // �ع�����
			throw e;
		} finally {
			session.close(); // �ر�Session
		}
	}

	/**
	 * ����
	 * 
	 * @param user
	 */
	public void update(User user) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(user); // ����

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 */
	public void delete(int id) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Object user = session.get(User.class, id); // Ҫ�Ȼ�ȡ���������
			session.delete(user); // ɾ������ʵ�����

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * ����id��ѯһ��User����
	 * 
	 * @param id
	 * @return
	 */
	public User getById(int id) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, id); // ����
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
	 * ��ѯ����
	 * 
	 * @return
	 */
	public List<User> findAll() {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// ��ʽһ��ʹ��HQL��ѯ
//			List<User> list = session.createQuery("FROM User").list(); 
			
			// ��ʽ����ʹ��Criteria��ѯ
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
	 * * ��ҳ�Ĳ�ѯ�����б�
	 * 
	 * @param firstResult
	 *            �ӽ���б��е��ĸ�������ʼȡ����
	 * @param maxResults
	 *            ���ȡ����������
	 * @return һҳ�������б� + �ܼ�¼��
	 */
	@SuppressWarnings("unchecked")
	public QueryResult findAll(int firstResult, int maxResults) {
		Session session = HibernateUtils.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// ��ѯһҳ�������б�
			// ��ʽһ��
			// Query query = session.createQuery("FROM User");
			// query.setFirstResult(firstResult);
			// query.setMaxResults(maxResults);
			// List<User> list = query.list();
			// ��ʽ����������
			List<User> list = session.createQuery(//
					"FROM User")//
					.setFirstResult(firstResult)//
					.setMaxResults(maxResults)//
					.list();

			// ��ѯ�ܼ�¼��
			Long count = (Long) session.createQuery(//
					"SELECT COUNT(*) FROM User")//
					.uniqueResult();
			tx.commit();
			
			//  ���ؽ��
			return new QueryResult(count.intValue(), list);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
