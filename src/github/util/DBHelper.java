package github.util;

import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import github.mybatis.bean.Branch;
import github.gui.MyBranch;
import github.gui.MyRepository;
import github.home.Main;
import github.home.Welcone;
import github.mybatis.bean.User;
import github.mybatis.dao.BranchMapper;
import github.mybatis.dao.UserMapper;

public class DBHelper {
	public static SqlSession getSqlSessionFactory() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory.openSession();
	}
	public static void selectUserNameExistOn(String name,String pwd,JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		try{
			UserMapper mapper=openSession.getMapper(UserMapper.class);
			User myUser=mapper.getUserByName(name);
			//从数据库查找该用户名是否存在,若存在则会返回结果
			if (myUser!=null){
				//若用户存在,则从数据库获取该用户的密码
				String password= myUser.getPassword();
				//判断用户输入的密码与数据库中获取到的密码是否相同
				if(!pwd.equals(password)){
					JOptionPane.showMessageDialog(iframe, "密码不正确", "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					JOptionPane.showMessageDialog(iframe,"登陆成功","恭喜",JOptionPane.DEFAULT_OPTION);
					iframe.dispose();
					//登陆成功。把登陆窗口关闭了,把Main窗口隐藏掉,进入新的界面welcone();
					Main.getInstance().setVisible(false);
					//登陆成功 ,记得保存用户名 ,以便其他页面的使用,好比如session cookie
					MyActions.UserName=name;
					//打开新的窗口
					new Welcone();
				}
			}
			else{
				//也就是说sql="select * from userlist where username=?";没有查询到结果
				JOptionPane.showMessageDialog(iframe, "该用户名不存在", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}	
		}finally{
			openSession.close();
		}
	}
	public static void registerUser(String name,String password,String verify,JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		UserMapper mapper=openSession.getMapper(UserMapper.class);
		try{
			//判断用户有没有输入内容
			if(name.equals("")){
				JOptionPane.showMessageDialog(iframe, "用户名不能为空,请输入用户名", "消息",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			String username=null;
			//从数据库中获取所有用户名字
			List<User> users=mapper.getAllUserName();
			for(User user:users){
				username=user.getUsername();
				if(name.equals(username)){
					 JOptionPane.showMessageDialog(iframe, "用户名已被注册,请重新输入", "消息",JOptionPane.INFORMATION_MESSAGE);
					 return;
				 }
			}
			if(password.equals("")){
				JOptionPane.showMessageDialog(iframe, "密码不能为空,请输入密码", "消息",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			if(verify.equals("")){
				JOptionPane.showMessageDialog(iframe, "验证密码不能为空,请输入验证密码", "消息",JOptionPane.INFORMATION_MESSAGE);
				return ;	
			}
			if(password.equals(verify)){
				int i=JOptionPane.showConfirmDialog(iframe,"确定注册该用户名?","确认",JOptionPane.OK_CANCEL_OPTION);
				if(i==0){
				  //如果i==0,说明点了确认
					JOptionPane.showMessageDialog(iframe,"恭喜您,用户注册成功!","恭喜",JOptionPane.DEFAULT_OPTION);
					//点击确认以后就开始添加数据
					mapper.addUser(name,password);
					iframe.dispose();
					//注册成功以后立马登陆并跳转到新的窗口
				    Main.getInstance().setVisible(false);
				    MyActions.UserName=name;
				    new Welcone();
				}
			}else{
				JOptionPane.showMessageDialog(iframe,"密码不一致,请重新输入","错误",JOptionPane.ERROR_MESSAGE);
			}
			//手动提交数据
			openSession.commit();
		}finally {
			openSession.close();
		}
	}
	public static void SaveOperation(String code,JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		BranchMapper mapper=openSession.getMapper(BranchMapper.class);
		//接收输入内容
		String content=JOptionPane.showInputDialog(iframe,"Input Its Title");
		int total=0,count=0;
		try {
			System.out.println(MyBranch.CurrentTime());
			//计算branch表中title的数目
			List<Branch> branches=mapper.getAllTitle(MyActions.UserName);
			total=branches.size();
			
			//判断输入的title是否已经在branch表中
			//如果title不在branch表中,count++最后结果等于total
			List<Branch> branchs=mapper.getAllTitle(MyActions.UserName);
			for(Branch branch:branchs){
				String title=branch.getTitle();
				if(!title.equals(content)){
					count++;
				}else break;
			}
			//System.out.println(count);
			//如果是新title,则执行Insert操作
			if(count==total){
				Branch branch=new Branch(content,code,MyBranch.CurrentTime(),MyActions.UserName);
				mapper.newBranch(branch);
			}//否则执行Update操作
			else{
				mapper.updateBranch(MyBranch.CurrentTime(),code,MyActions.UserName,content);
				
			}
			//把当前MyRepository关闭  然后再重新打开  从而达到刷新效果
			MyActions.frames.get("Repository").dispose();
			if (!MyActions.frames.containsKey("Repository") || MyActions.frames.get("Repository").isClosed()) {
				MyRepository iframe_Repository = new MyRepository();
				MyActions.frames.put("Repository", iframe_Repository);
                Welcone.addIFame(MyActions.frames.get("Repository"));
			}
			//手动提交数据
			openSession.commit();
		} finally {
			openSession.close();
		}
	}
	public static List<Branch> ReadMyBranch() throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		BranchMapper mapper=openSession.getMapper(BranchMapper.class);
		List<Branch> branches=null;
		try{
			branches=mapper.getAllBranch(MyActions.UserName,MyActions.Title);
		}finally {
            openSession.close();// 关闭连接
        }
		return branches;
	}
	public static int DeleteMyBranch(JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		BranchMapper mapper=openSession.getMapper(BranchMapper.class);
		int i=0;
        try {
        	i=JOptionPane.showConfirmDialog(iframe, "Are you sure you want to delete the Branch?","Warning",JOptionPane.YES_NO_OPTION);
			//如果点击了确定
        	if(i==0){
        		//恭喜 删除成功
				JOptionPane.showMessageDialog(iframe,"Successfully Delete!");
				mapper.deleteBranch(MyActions.UserName,MyActions.Title);
				//把当前MyRepository关闭  然后再重新打开  从而达到刷新效果
				MyActions.frames.get("Repository").dispose();
				if (!MyActions.frames.containsKey("Repository") || MyActions.frames.get("Repository").isClosed()) {
					MyRepository iframe_Repository = new MyRepository();
					MyActions.frames.put("Repository", iframe_Repository);
	                Welcone.addIFame(MyActions.frames.get("Repository"));
				}
        	}
        	openSession.commit();
		}finally {
            openSession.close();// 关闭连接
        }
        return i;
	}
	public static int BranchesTotal() throws IOException{
		SqlSession opensession = getSqlSessionFactory();
		BranchMapper mapper=opensession.getMapper(BranchMapper.class);
		int i=0,total=0;
		try {
			List<Branch> branches=mapper.getAllTitle(MyActions.UserName);
			total=branches.size();
		}finally{
			opensession.close();
		}
		return total;
	}
	public static List<Branch> ReadMyRepository() throws IOException{
		SqlSession opensession = getSqlSessionFactory();
		BranchMapper mapper=opensession.getMapper(BranchMapper.class);
		List<Branch> branches=null;
        try {
        	branches=mapper.getRepository(MyActions.UserName);
		}finally {
            opensession.close();// 关闭连接
        }
        return branches;
	}
}
