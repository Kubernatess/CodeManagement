package github.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import github.util.DBHelper;
import github.util.MyActions;
public class Login extends JInternalFrame{
	public Login(){
		super("Login",true,true,true,true); 
		Container c=getContentPane();//此Container对象用于后面对话框的上下文对象
		String path="images\\login.jpg";  
        JLabel image=new JLabel(new ImageIcon(path));  
        // 把标签的大小位置设置为图片刚好填充整个面板  
        image.setBounds(0, 0,getWidth(),getHeight()/2);  
        JLabel UserName=new JLabel("UserName");
        //设置标签字体大小
        UserName.setFont(new Font(null,Font.PLAIN,20));
        JLabel Password=new JLabel("Password");
        //设置标签字体大小
        Password.setFont(new Font(null,Font.PLAIN,20));
        JTextField text_username=new JTextField(20);
        //设置文本框里面输入的字体的大小
        text_username.setFont(new Font(null,Font.PLAIN,20));
        text_username.requestFocus();
        JPasswordField text_pwd=new JPasswordField(20);
        //设置文本框里面输入的字体的大小
        text_pwd.setFont(new Font(null,Font.PLAIN,20));
        JButton login=new JButton("Login");
        //设置按钮组件的大小
        login.setPreferredSize(new Dimension(100,50));
        //设置按钮组件里面字体的大小
        login.setFont(new Font(null,Font.PLAIN,20));
        login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String name=text_username.getText();
					String pwd=new String(text_pwd.getPassword());
					//如果用户名为空
					if(name.equals("")){
						JOptionPane.showMessageDialog(c, "用户名不能为空,请输入用户名", "消息",JOptionPane.INFORMATION_MESSAGE);
						text_username.requestFocus();
						return ;
					}
					//如果密码为空
					if(pwd.equals("")){
						JOptionPane.showMessageDialog(c, "密码不能为空,请输入密码", "消息",JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					try {
						DBHelper.selectUserNameExistOn(name, pwd,MyActions.frames.get("Login"));
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}	    
			}
        });
        JButton reset=new JButton("Reset");
        //设置组件大小
        reset.setPreferredSize(new Dimension(100,50));
        //设置组件里面字体大小
        reset.setFont(new Font(null,Font.PLAIN,20));
        reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				text_username.setText("");
				text_pwd.setText("");
			}
        });
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        p1.add(UserName);
        p1.add(text_username);
        p2.add(Password);
        p2.add(text_pwd);
        p3.add(login);
        //用一个空label来隔开两个按钮的间距
        p3.add(new JLabel("        "));
        p3.add(reset);
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        setLayout(new GridLayout(2,1));
        add(image);
        add(panel);
        setLocation(200, 100);
        setSize(600,500);
        setVisible(true); 
		setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	}
}
