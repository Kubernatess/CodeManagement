package github.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import github.util.MyActions;

public class Welcone extends JFrame{
	private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();
	public static void addIFame(JInternalFrame iframe) { // 添加子窗体的方法
        DESKTOP_PANE.add(iframe);
    }
	Container c=getContentPane();//此Container对象用于后面对话框的上下文对象
	public Welcone(){
		super("Welcone to "+MyActions.UserName);
		JToolBar toolBar = createToolBar();
		add(toolBar, BorderLayout.NORTH);

		ImageIcon image3=new ImageIcon("images/image3.jpg");
		image3.setImage(image3.getImage().getScaledInstance(600,600,Image.SCALE_DEFAULT));
		JLabel picture=new JLabel(image3);
		DESKTOP_PANE.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                picture.setSize(e.getComponent().getSize());
            }
        });
		DESKTOP_PANE.add(picture,new Integer(Integer.MIN_VALUE));
		DESKTOP_PANE.setBackground(new Color(255,255,255));
		add(DESKTOP_PANE);
		setLocation(300,30);
		setSize(1300,1000);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e){
				Main.getInstance().setVisible(true);
			}
			public void windowClosing(WindowEvent e){
				Main.getInstance().setVisible(true);
			}
		});
	}
	private JToolBar createToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JButton Branch=new JButton(MyActions.Branch);
		ImageIcon image1=new ImageIcon("images/image7.png");
		image1.setImage(image1.getImage().getScaledInstance(80,75,Image.SCALE_DEFAULT));
		Branch.setIcon(image1);
		Branch.setFont(new Font(null,Font.PLAIN,40));
		Branch.setPreferredSize(new Dimension(85,75));
		
		JButton Repository=new JButton(MyActions.Repository);
		ImageIcon image2=new ImageIcon("images/image6.png");
		image2.setImage(image2.getImage().getScaledInstance(85,80,Image.SCALE_DEFAULT));
		Repository.setIcon(image2);
		Repository.setFont(new Font(null,Font.PLAIN,40));
		Repository.setPreferredSize(new Dimension(85,75));
		
		JButton logout=new JButton("Logout");
		ImageIcon image3=new ImageIcon("images/image13.jpg");
		image3.setImage(image3.getImage().getScaledInstance(85,85,Image.SCALE_DEFAULT));
		logout.setIcon(image3);
		logout.setFont(new Font(null,Font.PLAIN,40));
		logout.setPreferredSize(new Dimension(85,75));
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int i=JOptionPane.showConfirmDialog(c,"确定注销该用户?","确认",JOptionPane.OK_CANCEL_OPTION);
				if(i==0){
					dispose();
				}	
			}
		});
		toolBar.add(Branch);
		toolBar.add(Repository);
		toolBar.add(logout);
		return toolBar;
	}
}
