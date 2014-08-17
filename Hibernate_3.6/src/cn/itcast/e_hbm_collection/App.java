package cn.itcast.e_hbm_collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	private static SessionFactory sessionFactory = new Configuration()//
			.configure()//
			.addClass(User.class)// 添加Hibernate实体类（加载对应的映射文件）
			.buildSessionFactory();

	@Test
	public void testSave() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 构建对象
		User user = new User();
		user.setName("张天");
		// >> Set集合
		user.setAddressSet(new TreeSet<String>()); // 当设置了sort属性时，就要使用SortedSet类型
		user.getAddressSet().add("2御富科贸园");
		user.getAddressSet().add("1棠东东路");
		// >> List集合
		user.getAddressList().add("御富科贸园");
		user.getAddressList().add("棠东东路");
		user.getAddressList().add("棠东东路");
		// >> 数组
		user.setAddressArray(new String[] { "御富科贸园", "棠东东路" });
		// >> Map集合
		user.getAddressMap().put("公司", "御富科贸园");
		user.getAddressMap().put("家庭", "棠东东路");
		// >> Bag集合
		user.getAddressBag().add("御富科贸园");
		user.getAddressBag().add("棠东东路");
		user.getAddressBag().add("棠东东路");

		// 保存
		session.save(user);

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testGet() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// --------------------------------------------

		// 获取数据
		User user = (User) session.get(User.class, 9);

		// 显示信息
		System.out.println(user.getName());
		System.out.println(user.getAddressSet());
		System.out.println(user.getAddressList());
		System.out.println(Arrays.toString(user.getAddressArray()));
		System.out.println(user.getAddressMap());
		System.out.println(user.getAddressBag());

		// --------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
