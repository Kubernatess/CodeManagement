package github.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import github.mybatis.bean.User;

public interface UserMapper {
	public User getUserByName(String username);
	
	public List<User> getAllUserName();
	
	public void addUser(@Param("username")String username,@Param("password")String password);
}
