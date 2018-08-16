package github.util;

import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import github.gui.*;
import github.home.Main;
import github.home.Welcone;
public class MyActions {
	public static Map<String, JInternalFrame> frames;
	public static Open_Action Open;
	public static Edit_Action Edit;
	public static Remove_Action Remove;
	public static Login_Action Login;
	public static Register_Action Register;
	public static Branch_Action Branch;
	public static Repository_Action Repository;
	public static String UserName=null;//用于保存登陆后的用户名
	public static String Title=null;//用于保存title 以便所有页面使用
	public static boolean isEnabled=true;//用于设置Branch中edit textarea open的可用性
	public static int getColumnOption=0;//表明MyRepository点击了哪个操作
	static{
		frames = new HashMap<String, JInternalFrame>();
		Open=new Open_Action();
		Edit=new Edit_Action();
		Remove=new Remove_Action();
		Login=new Login_Action();
		Register=new Register_Action();
		Branch=new Branch_Action();
		Repository=new Repository_Action();
	}
	private static class Open_Action extends AbstractAction{
		Open_Action(){
			super("Open",null);
			//鼠标悬浮再Open按钮  有文字"Open File"弹出
			putValue(Action.SHORT_DESCRIPTION, "Open File");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	private static class Edit_Action extends AbstractAction{
		Edit_Action(){
			super("Edit",null);
			//鼠标悬浮再Edit按钮  有文字"Edit"弹出
			putValue(Action.SHORT_DESCRIPTION, "Edit");
		}
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	private static class Remove_Action extends AbstractAction{
		Remove_Action(){
			super("Remove",null);
			putValue(Action.SHORT_DESCRIPTION, "Remove");
		}
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	private static class Login_Action extends AbstractAction{
		Login_Action(){
			super("Login",null);
			putValue(Action.SHORT_DESCRIPTION, "Login");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Login") || frames.get("Login").isClosed()) {
                Login iframe = new Login();
                frames.put("Login", iframe);
                Main.addIFame(frames.get("Login"));
            }
		}
	}
	private static class Register_Action extends AbstractAction{
		Register_Action(){
			super("Register",null);
			putValue(Action.SHORT_DESCRIPTION, "Register");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Register") || frames.get("Register").isClosed()) {
                Register iframe = new Register();
                frames.put("Register", iframe);
                Main.addIFame(frames.get("Register"));
            }
		}
	}
	private static class Branch_Action extends AbstractAction{
		Branch_Action(){
			super("New Branch",null);
			putValue(Action.SHORT_DESCRIPTION, "New Branch");
		}
		public void actionPerformed(ActionEvent e) {
				//使Branch中edit按钮不可用
				MyActions.isEnabled=false;
				//MyActions.getColumnOption只要不等于2、3、4方可
				MyActions.getColumnOption=0;
				MyBranch iframe = new MyBranch();
				frames.put("Branch", iframe);
                Welcone.addIFame(frames.get("Branch"));
		}
	}
	private static class Repository_Action extends AbstractAction{
		Repository_Action(){
			super("My Repository",null);
			putValue(Action.SHORT_DESCRIPTION, "New Branch");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Repository") || frames.get("Repository").isClosed()) {
				MyRepository iframe = new MyRepository();
                frames.put("Repository", iframe);
                Welcone.addIFame(frames.get("Repository"));
            }
		}
	}
}

