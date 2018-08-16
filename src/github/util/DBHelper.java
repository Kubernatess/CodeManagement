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
			//�����ݿ���Ҹ��û����Ƿ����,��������᷵�ؽ��
			if (myUser!=null){
				//���û�����,������ݿ��ȡ���û�������
				String password= myUser.getPassword();
				//�ж��û���������������ݿ��л�ȡ���������Ƿ���ͬ
				if(!pwd.equals(password)){
					JOptionPane.showMessageDialog(iframe, "���벻��ȷ", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					JOptionPane.showMessageDialog(iframe,"��½�ɹ�","��ϲ",JOptionPane.DEFAULT_OPTION);
					iframe.dispose();
					//��½�ɹ����ѵ�½���ڹر���,��Main�������ص�,�����µĽ���welcone();
					Main.getInstance().setVisible(false);
					//��½�ɹ� ,�ǵñ����û��� ,�Ա�����ҳ���ʹ��,�ñ���session cookie
					MyActions.UserName=name;
					//���µĴ���
					new Welcone();
				}
			}
			else{
				//Ҳ����˵sql="select * from userlist where username=?";û�в�ѯ�����
				JOptionPane.showMessageDialog(iframe, "���û���������", "����",JOptionPane.WARNING_MESSAGE);
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
			//�ж��û���û����������
			if(name.equals("")){
				JOptionPane.showMessageDialog(iframe, "�û�������Ϊ��,�������û���", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			String username=null;
			//�����ݿ��л�ȡ�����û�����
			List<User> users=mapper.getAllUserName();
			for(User user:users){
				username=user.getUsername();
				if(name.equals(username)){
					 JOptionPane.showMessageDialog(iframe, "�û����ѱ�ע��,����������", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
					 return;
				 }
			}
			if(password.equals("")){
				JOptionPane.showMessageDialog(iframe, "���벻��Ϊ��,����������", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			if(verify.equals("")){
				JOptionPane.showMessageDialog(iframe, "��֤���벻��Ϊ��,��������֤����", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return ;	
			}
			if(password.equals(verify)){
				int i=JOptionPane.showConfirmDialog(iframe,"ȷ��ע����û���?","ȷ��",JOptionPane.OK_CANCEL_OPTION);
				if(i==0){
				  //���i==0,˵������ȷ��
					JOptionPane.showMessageDialog(iframe,"��ϲ��,�û�ע��ɹ�!","��ϲ",JOptionPane.DEFAULT_OPTION);
					//���ȷ���Ժ�Ϳ�ʼ�������
					mapper.addUser(name,password);
					iframe.dispose();
					//ע��ɹ��Ժ������½����ת���µĴ���
				    Main.getInstance().setVisible(false);
				    MyActions.UserName=name;
				    new Welcone();
				}
			}else{
				JOptionPane.showMessageDialog(iframe,"���벻һ��,����������","����",JOptionPane.ERROR_MESSAGE);
			}
			//�ֶ��ύ����
			openSession.commit();
		}finally {
			openSession.close();
		}
	}
	public static void SaveOperation(String code,JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		BranchMapper mapper=openSession.getMapper(BranchMapper.class);
		//������������
		String content=JOptionPane.showInputDialog(iframe,"Input Its Title");
		int total=0,count=0;
		try {
			System.out.println(MyBranch.CurrentTime());
			//����branch����title����Ŀ
			List<Branch> branches=mapper.getAllTitle(MyActions.UserName);
			total=branches.size();
			
			//�ж������title�Ƿ��Ѿ���branch����
			//���title����branch����,count++���������total
			List<Branch> branchs=mapper.getAllTitle(MyActions.UserName);
			for(Branch branch:branchs){
				String title=branch.getTitle();
				if(!title.equals(content)){
					count++;
				}else break;
			}
			//System.out.println(count);
			//�������title,��ִ��Insert����
			if(count==total){
				Branch branch=new Branch(content,code,MyBranch.CurrentTime(),MyActions.UserName);
				mapper.newBranch(branch);
			}//����ִ��Update����
			else{
				mapper.updateBranch(MyBranch.CurrentTime(),code,MyActions.UserName,content);
				
			}
			//�ѵ�ǰMyRepository�ر�  Ȼ�������´�  �Ӷ��ﵽˢ��Ч��
			MyActions.frames.get("Repository").dispose();
			if (!MyActions.frames.containsKey("Repository") || MyActions.frames.get("Repository").isClosed()) {
				MyRepository iframe_Repository = new MyRepository();
				MyActions.frames.put("Repository", iframe_Repository);
                Welcone.addIFame(MyActions.frames.get("Repository"));
			}
			//�ֶ��ύ����
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
            openSession.close();// �ر�����
        }
		return branches;
	}
	public static int DeleteMyBranch(JInternalFrame iframe) throws IOException{
		SqlSession openSession = getSqlSessionFactory();
		BranchMapper mapper=openSession.getMapper(BranchMapper.class);
		int i=0;
        try {
        	i=JOptionPane.showConfirmDialog(iframe, "Are you sure you want to delete the Branch?","Warning",JOptionPane.YES_NO_OPTION);
			//��������ȷ��
        	if(i==0){
        		//��ϲ ɾ���ɹ�
				JOptionPane.showMessageDialog(iframe,"Successfully Delete!");
				mapper.deleteBranch(MyActions.UserName,MyActions.Title);
				//�ѵ�ǰMyRepository�ر�  Ȼ�������´�  �Ӷ��ﵽˢ��Ч��
				MyActions.frames.get("Repository").dispose();
				if (!MyActions.frames.containsKey("Repository") || MyActions.frames.get("Repository").isClosed()) {
					MyRepository iframe_Repository = new MyRepository();
					MyActions.frames.put("Repository", iframe_Repository);
	                Welcone.addIFame(MyActions.frames.get("Repository"));
				}
        	}
        	openSession.commit();
		}finally {
            openSession.close();// �ر�����
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
            opensession.close();// �ر�����
        }
        return branches;
	}
}
