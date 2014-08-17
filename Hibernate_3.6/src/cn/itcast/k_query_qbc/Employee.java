package cn.itcast.k_query_qbc;

/**
 * Ա��
 * 
 * @author tyg
 * 
 */
public class Employee {
	private Integer id;
	private String name;
	private Department department; // �����Ĳ��Ŷ���

	public Employee() {
	}

	public Employee(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "[Employee: id=" + id + ", name=" + name + "]";
	}
}
