package github.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import github.util.DBHelper;
import github.util.MyActions;

public class Register extends JInternalFrame{
	public Register(){
		super("User Register",true,true,true,true);
		Container c=getContentPane();
		JLabel lumlum=new JLabel("LumLum.");
		lumlum.setFont(new Font(null,Font.BOLD,70));
		lumlum.setForeground(new Color(255,255,255));
		lumlum.setBounds(250,30,350,100);
		JLabel name=new JLabel("Please Input Your Name");
		name.setBounds(90,180,250,20);
		name.setFont(new Font(null,Font.PLAIN,20));
		JTextField InputName=new JTextField(50);
		InputName.setFont(new Font(null,Font.PLAIN,20));
		InputName.setBounds(360,170,400,40);
		InputName.requestFocus();
		JLabel pwd=new JLabel("Please Input Your Password");
		pwd.setBounds(90,230,350,25);
		pwd.setFont(new Font(null,Font.PLAIN,20));
		JPasswordField InputPassword=new JPasswordField(50);
		InputPassword.setFont(new Font(null,Font.PLAIN,20));
		InputPassword.setBounds(360,220,400,40);
		JLabel verify=new JLabel("Verify Your Password");
		verify.setBounds(90,280,350,25);
		verify.setFont(new Font(null,Font.PLAIN,20));
		JPasswordField VerifyPassword=new JPasswordField(50);
		VerifyPassword.setFont(new Font(null,Font.PLAIN,20));
		VerifyPassword.setBounds(360,270,400,40);
		JButton login=new JButton("Login");
		login.setFont(new Font(null,Font.PLAIN,20));
		login.setBounds(180,350,200,50);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name=InputName.getText();
				String password=new String(InputPassword.getPassword());
				String verify=new String(VerifyPassword.getPassword());
				try {
					DBHelper.registerUser(name, password, verify, MyActions.frames.get("Register"));
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		JButton reset=new JButton("Reset");
		reset.setFont(new Font(null,Font.PLAIN,20));
		reset.setBounds(430,350,200,50);
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				InputName.setText("");
				InputPassword.setText("");
				VerifyPassword.setText("");
				InputName.requestFocus();
			}
		});
		JPanel panel=new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(112,128,144));
		panel.add(lumlum);
		panel.add(name);
		panel.add(InputName);
		panel.add(pwd);
		panel.add(InputPassword);
		panel.add(verify);
		panel.add(VerifyPassword);
		panel.add(login);
		panel.add(reset);
		add(panel);
		setLocation(100,100);
		setSize(850,500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
