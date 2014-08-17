package cn.itcast.i_hbm_oneToOne;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Person.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.addClass(IdCard.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.buildSessionFactory();

	// ���棬�й�����ϵ
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// �½�����
		Person person = new Person();
		person.setName("����");

		IdCard idCard = new IdCard();
		idCard.setNumber("100000011X");

		// ��������
		// person.setIdCard(idCard);
		idCard.setPerson(person);

		// ����
		session.save(person);
		session.save(idCard);

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
		// Person person = (Person) session.get(Person.class, 1);
		// System.out.println(person);
		// System.out.println(person.getIdCard());

		IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		System.out.println(idCard);
		System.out.println(idCard.getPerson());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();

	}

	// ���������ϵ��һ��һ�У�ֻ�������������ά��������ϵ��
	@Test
	public void testRemoveRelation() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// ��������������ϵ�����ԡ�
		// IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		// idCard.setPerson(null);

		// ��������������ϵ�������ԡ�
		Person person = (Person) session.get(Person.class, 1);
		person.setIdCard(null);

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

		// a, ���û�й����ĶԷ�����ɾ����
		// b, ����й����ĶԷ��ҿ���ά��������ϵ����������������ͻ���ɾ��������ϵ����ɾ���Լ���
		// c, ����й����ĶԷ��Ҳ���ά��������ϵ����������������Ի�ֱ��ִ��ɾ���Լ����ͻ����쳣��

		IdCard idCard = (IdCard) session.get(IdCard.class, 1);
		session.delete(idCard);

		// Person person = (Person) session.get(Person.class, 1);
		// session.delete(person);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
