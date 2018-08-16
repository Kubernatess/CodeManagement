package github.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.ibatis.cursor.Cursor;

import github.home.Welcone;
import github.mybatis.bean.Branch;
import github.util.DBHelper;
import github.util.MyActions;
public class MyRepository extends JInternalFrame{
	public MyRepository(){
		super("My Repository",true,true,true,true);
		Container c=getContentPane();//此Container对象用于后面对话框的上下文对象
		JLabel title=new JLabel("My Repository",JLabel.CENTER);
		title.setFont(new Font(null,Font.PLAIN,60));
		String []Ttitle={"Title","Date","Turn to","Edit","Remove"};		

		int total=0;
		try {
			total = DBHelper.BranchesTotal();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		Object info[][]=new Object[total][2];
		int i=0;
		List<Branch> branches;
		try {
			branches=DBHelper.ReadMyRepository();
			for(Branch branch:branches){
				//读取title
				info[i][0]=branch.getTitle();
				System.out.println(info[i][0]);
				//读取时间
				info[i][1]=branch.getSaveTime();
				System.out.println(info[i][1]);
				i++;
			}
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		
		//设置默认渲染器为按钮
		TableModel model=new DefaultTableModel(info,Ttitle);
		JTable table=new JTable(model);
		table.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer(){
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		    	ImageIcon image2=new ImageIcon("images/image12.png");
				image2.setImage(image2.getImage().getScaledInstance(30,40,Image.SCALE_DEFAULT));
				JButton turn_to=new JButton(image2);
		    	return turn_to;
		    }
		});
		//设置默认编辑器
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()){
			public Component getTableCellEditorComponent(JTable table, Object value,
		            boolean isSelected, int row, int column){
				ImageIcon image2=new ImageIcon("images/image12.png");
				image2.setImage(image2.getImage().getScaledInstance(30,40,Image.SCALE_DEFAULT));
				JButton turn_to=new JButton(image2);
				turn_to.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						//是Branch中edit可用   因为此时Branch处于可读状态  点击edit立马可以编辑Branch
						MyActions.isEnabled=true;
						int row=table.getSelectedRow();
						//MyActions.getColumnOption表明点击了哪个按钮   2表示turn to  3表示编辑   4表示remove
						MyActions.getColumnOption=table.getSelectedColumn();	
						//MyActions.Title获取title  以便Branch使用
						MyActions.Title=(String) info[row][0];
						Welcone.addIFame(new MyBranch());
					}	
				});
		    	return turn_to;
			}
		});
		//设置默认渲染器为按钮
		table.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer(){
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		    	ImageIcon image2=new ImageIcon("images/image8.jpg");
				image2.setImage(image2.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
				JButton edit=new JButton(image2);
		    	return edit;
		    }
		});
		//设置默认编辑器
		table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()){
			public Component getTableCellEditorComponent(JTable table, Object value,
		            boolean isSelected, int row, int column){
				ImageIcon image2=new ImageIcon("images/image8.jpg");
				image2.setImage(image2.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
				JButton edit=new JButton(image2);
				edit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						//是Branch中edit不可用  此时Branch已经处于编辑状态
						MyActions.isEnabled=false;
						int row=table.getSelectedRow();	
						//MyActions.getColumnOption表明点击了哪个按钮   2表示turn to  3表示编辑   4表示remove
						MyActions.getColumnOption=table.getSelectedColumn();
						MyActions.Title=(String) info[row][0];
						Welcone.addIFame(new MyBranch());
					}
					
				});
		    	return edit;
			}
		});
		//设置第4列默认渲染器为按钮
		table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer(){
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		    	ImageIcon image2=new ImageIcon("images/image9.jpg");
				image2.setImage(image2.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
				JButton remove=new JButton(image2);
		    	return remove;
		    }
		});
		//设置第4列的默认编辑器
		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()){
			public Component getTableCellEditorComponent(JTable table, Object value,
		            boolean isSelected, int row, int column){
				ImageIcon image2=new ImageIcon("images/image9.jpg");
				image2.setImage(image2.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
				JButton remove=new JButton(image2);
				remove.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try {
							int i = DBHelper.DeleteMyBranch(MyActions.frames.get("Repository"));
							if(i==0){
								//把当前MyRepository关闭  然后再重新打开  从而达到刷新效果
								dispose();
								if (!MyActions.frames.containsKey("Repository") || MyActions.frames.get("Repository").isClosed()) {
									MyRepository iframe = new MyRepository();
									MyActions.frames.put("Repository", iframe);
					                Welcone.addIFame(MyActions.frames.get("Repository"));
								}
							}
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
						
					}
				});
		    	return remove;
			}
		});
		table.setRowHeight(40);//设置行高
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(420);
		table.getColumnModel().getColumn(1).setPreferredWidth(420);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(new JScrollPane(table));
		add(title,"North");
		setLocation(120,50);
		setSize(1070,800);
		setVisible(true);
		setResizable(false);
	}
}
