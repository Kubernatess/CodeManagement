package github.mybatis.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import github.gui.MyBranch;
import github.mybatis.bean.Branch;
import github.util.MyActions;

public interface BranchMapper {
	
	public List<Branch> getAllTitle(String username);
	
	public void newBranch(Branch branch);
	
	public void updateBranch(@Param("saveTime")String saveTime,@Param("code")String code,@Param("username")String username,@Param("title")String title);
	
	public List<Branch> getAllBranch(@Param("username")String username,@Param("title")String title);
	
	public void deleteBranch(@Param("username")String username,@Param("title")String title);
	
	public List<Branch> getRepository(String username);
	
	
}
