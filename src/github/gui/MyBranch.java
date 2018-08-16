package github.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import org.apache.ibatis.cursor.Cursor;

import github.mybatis.bean.Branch;
import github.util.DBHelper;
import github.util.MyActions;

public class MyBranch extends JInternalFrame{
	JTextArea textarea=new JTextArea();
	Container c=getContentPane();//��Container�������ں���Ի���������Ķ���
	public MyBranch(){
		super("My Branch",true,true,true,true);
		JToolBar toolBar = createToolBar();
		JPanel panel=new JPanel();
		JButton save=new JButton("Save");
		save.setPreferredSize(new Dimension(120,70));//���ð�ť�����С
		save.setFont(new Font(null,Font.PLAIN,35));//���ð�ť���������С
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String code=textarea.getText();
				try {
					DBHelper.SaveOperation(code,MyActions.frames.get("Branch"));
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		//MyActions.isEnabled==true����Edit��ť����.Edit��ť���ñ�ʾBranch���ڿɶ�״̬
		//MyActions.getColumnOption==2���������turn_to����

		//MyActions.isEnabled==false����Edit��ť������.Edit��ť�����ñ�ʾBranch���ڿɱ༭״̬
		//MyActions.getColumnOption==3���������Edit����
		if((MyActions.isEnabled==true&&MyActions.getColumnOption==2)||(MyActions.isEnabled==false&&MyActions.getColumnOption==3)){
			List<Branch> branches;
			try {
				branches = DBHelper.ReadMyBranch();
				for(Branch branch:branches){
					String str=branch.getCode();
					textarea.append(str);
				}
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		JLabel space=new JLabel("");
		space.setPreferredSize(new Dimension(120,70));
		JButton back=new JButton("Back");
		back.setFont(new Font(null,Font.PLAIN,35));
		back.setPreferredSize(new Dimension(120,70));
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}		
		});
		panel.add(save);
		panel.add(space);
		panel.add(back);
		textarea.setFont(new Font(null,Font.PLAIN,30));
		//textarea��edit�Ŀ��������෴��
		textarea.setEditable(!MyActions.isEnabled);
	
		add(toolBar, BorderLayout.NORTH);
		add(new JScrollPane(textarea));
		add(panel,"South");
		setLocation(70,20);
		setSize(1000,800);
		setVisible(true);
		setResizable(false);
	}
	private JToolBar createToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);//���ù�������������
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));//���ñ߽�Ч��
		JButton open=new JButton(MyActions.Open);
		//open�Ŀ�������textarea��ͬ
		open.setEnabled(!MyActions.isEnabled);
		ImageIcon image1=new ImageIcon("images/image5.png");
		//����ͼƬ��С
		image1.setImage(image1.getImage().getScaledInstance(80,70,Image.SCALE_DEFAULT));
		open.setIcon(image1);
		open.setHideActionText(true);
		open.setPreferredSize(new Dimension(80,70));
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int rVal = fc.showOpenDialog(open);
				if (rVal == JFileChooser.APPROVE_OPTION){
					String fileName = fc.getSelectedFile().getName();
					String path = fc.getCurrentDirectory().toString();
					try {
						FileReader fread = new FileReader(path + "/" + fileName);
						BufferedReader bread = new BufferedReader(fread);
						String line = bread.readLine();
						while (line != null) {
							textarea.append(line + "\n");
							// ����һ��
							line = bread.readLine();
						}
						bread.close();
						fread.close();
					} catch (FileNotFoundException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}	
				}
			}
		});	
		JButton edit=new JButton(MyActions.Edit);
		ImageIcon image2=new ImageIcon("images/image8.jpg");
		//����ͼƬ��С
		image2.setImage(image2.getImage().getScaledInstance(80,70,Image.SCALE_DEFAULT));
		edit.setIcon(image2);
		//���ð�ť�����С
		edit.setPreferredSize(new Dimension(80,70));
		edit.setHideActionText(true);
		edit.setEnabled(MyActions.isEnabled);
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				open.setEnabled(true);
				edit.setEnabled(false);//�������edit֮��,edit��ť��Ҳ������
				textarea.setEditable(true);
			}
		});	
		JButton remove=new JButton(MyActions.Remove);
		ImageIcon image3=new ImageIcon("images/image9.jpg");
		//����ͼƬ��С
		image3.setImage(image3.getImage().getScaledInstance(75,70,Image.SCALE_DEFAULT));
		remove.setIcon(image3);
		remove.setHideActionText(true);
		//���ð�ť�����С
		remove.setPreferredSize(new Dimension(75,70));
		remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					DBHelper.DeleteMyBranch(MyActions.frames.get("Branch"));
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(open);
		toolBar.add(edit);
		toolBar.add(remove);
		return toolBar;
	}
	//��ȡ��ǰʱ��
	public static String CurrentTime(){
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH)+1;
		int date=c.get(Calendar.DATE);
		int hour=c.get(Calendar.HOUR_OF_DAY);
		int minute=c.get(Calendar.MINUTE);
		int second=c.get(Calendar.SECOND);
		//��ǰʱ��Ϊ
		String time=year+"/"+month+"/"+date+" "+hour+":"+minute+":"+second;
		return time;
	}
}
