package cn.itcast.h_session_method;

public/* final */class User {
	private Integer id;
	private String name;
	private byte[] data = new byte[1024 * 1024 * 5];

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

}
