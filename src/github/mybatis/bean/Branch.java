package github.mybatis.bean;

import org.apache.ibatis.type.Alias;

@Alias("branch")
public class Branch {
	private String title;
	private String code;
	private String saveTime;
	private String username;
	public Branch() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public Branch(String title, String code, String saveTime, String userName) {
		super();
		this.title = title;
		this.code = code;
		saveTime = saveTime;
		userName = userName;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		saveTime = saveTime;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		username = userName;
	}
	@Override
	public String toString() {
		return "MyCode [title=" + title + ", code=" + code + ", SaveTime=" + saveTime + ", UserName=" + username + "]";
	}
	
}
