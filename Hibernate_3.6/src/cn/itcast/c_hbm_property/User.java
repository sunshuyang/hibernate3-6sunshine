package cn.itcast.c_hbm_property;

import java.util.Date;

/**
 * ʵ��
 * 
 * @author tyg
 * 
 */
public class User {
	private int id;
	private String name;
	private Integer age;
	private Date birthday; // ����
	private String desc; // һ���˵��
	private byte[] photo; // ͷ��ͼƬ

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "[User: id=" + id + ", name=" + name + "]";
	}

}
