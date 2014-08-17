package cn.itcast.d_hbm_id;

public class User {
	// private Integer id; // 0, null
	private String id;
	private String name;

	// public Integer getId() { return id; }
	// public void setId(Integer id) { this.id = id; }

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
