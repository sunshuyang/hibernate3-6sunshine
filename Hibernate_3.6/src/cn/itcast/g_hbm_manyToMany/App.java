package cn.itcast.g_hbm_manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(Student.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.addClass(Teacher.class)// ���Hibernateʵ���ࣨ���ض�Ӧ��ӳ���ļ���
			.buildSessionFactory();

	// ���棬�й�����ϵ
	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// �½�����
		Student student1 = new Student();
		student1.setName("��ͬѧ");

		Student student2 = new Student();
		student2.setName("��ͬѧ");

		Teacher teacher1 = new Teacher();
		teacher1.setName("����ʦ");

		Teacher teacher2 = new Teacher();
		teacher2.setName("����ʦ");

		// ��������
		student1.getTeachers().add(teacher1);
		student1.getTeachers().add(teacher2);
		student2.getTeachers().add(teacher1);
		student2.getTeachers().add(teacher2);

		teacher1.getStudents().add(student1);
		teacher1.getStudents().add(student2);
		teacher2.getStudents().add(student1);
		teacher2.getStudents().add(student2);

		// ����
		session.save(student1);
		session.save(student2);
		session.save(teacher1);
		session.save(teacher2);

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
		Teacher teacher = (Teacher) session.get(Teacher.class, 3L);
		System.out.println(teacher);
		System.out.println(teacher.getStudents());

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

		// ���inverse=false�Ϳ��Խ�������Ϊtrue�Ͳ����Խ��
		Teacher teacher = (Teacher) session.get(Teacher.class, 3L);
		teacher.getStudents().clear();

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
		// b, ����й����ĶԷ���inverse=false�����ڿ���ά��������ϵ�����ͻ���ɾ��������ϵ����ɾ���Լ���
		// c, ����й����ĶԷ���inverse=true�����ڲ���ά��������ϵ�����Ի�ֱ��ִ��ɾ���Լ����ͻ����쳣��
		Teacher teacher = (Teacher) session.get(Teacher.class, 9L);
		session.delete(teacher);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

}
