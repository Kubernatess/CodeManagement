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
		Container c=getContentPane();//��Container�������ں���Ի���������Ķ���
		String path="images\\login.jpg";  
        JLabel image=new JLabel(new ImageIcon(path));  
        // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������  
        image.setBounds(0, 0,getWidth(),getHeight()/2);  
        JLabel UserName=new JLabel("UserName");
        //���ñ�ǩ�����С
        UserName.setFont(new Font(null,Font.PLAIN,20));
        JLabel Password=new JLabel("Password");
        //���ñ�ǩ�����С
        Password.setFont(new Font(null,Font.PLAIN,20));
        JTextField text_username=new JTextField(20);
        //�����ı����������������Ĵ�С
        text_username.setFont(new Font(null,Font.PLAIN,20));
        text_username.requestFocus();
        JPasswordField text_pwd=new JPasswordField(20);
        //�����ı����������������Ĵ�С
        text_pwd.setFont(new Font(null,Font.PLAIN,20));
        JButton login=new JButton("Login");
        //���ð�ť����Ĵ�С
        login.setPreferredSize(new Dimension(100,50));
        //���ð�ť�����������Ĵ�С
        login.setFont(new Font(null,Font.PLAIN,20));
        login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String name=text_username.getText();
					String pwd=new String(text_pwd.getPassword());
					//����û���Ϊ��
					if(name.equals("")){
						JOptionPane.showMessageDialog(c, "�û�������Ϊ��,�������û���", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						text_username.requestFocus();
						return ;
					}
					//�������Ϊ��
					if(pwd.equals("")){
						JOptionPane.showMessageDialog(c, "���벻��Ϊ��,����������", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					try {
						DBHelper.selectUserNameExistOn(name, pwd,MyActions.frames.get("Login"));
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}	    
			}
        });
        JButton reset=new JButton("Reset");
        //���������С
        reset.setPreferredSize(new Dimension(100,50));
        //����������������С
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
        //��һ����label������������ť�ļ��
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
