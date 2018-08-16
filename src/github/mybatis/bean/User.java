package github.mybatis.bean;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class User {
	private String username;
	private String password;
	public User() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "MyUser [username=" + username + ", password=" + password + "]";
	}
	
}
