package github.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import github.util.MyActions;
public class Main extends JFrame{
	private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();
	private static Main instance=null;//ʹ�õ���ģʽ
	public static Main getInstance(){
		if(instance==null)instance=new Main();
		return instance;
	}
	public static void addIFame(JInternalFrame iframe) { // ����Ӵ���ķ���
        DESKTOP_PANE.add(iframe);
    }
	private Main(){
		super("LumLum");
		JButton login=new JButton(MyActions.Login);
		//���ð�ť����������С
		login.setFont(new Font(null,Font.PLAIN,20));
		//���������С
		login.setPreferredSize(new Dimension(120,70));
		JButton register=new JButton(MyActions.Register);
		//���������С
		register.setPreferredSize(new Dimension(120,70));
		//���ð�ť���������С
		register.setFont(new Font(null,Font.PLAIN,20));

		ImageIcon image1=new ImageIcon("images/image1.jpg");
		//����ͼƬ��С
		image1.setImage(image1.getImage().getScaledInstance(100,80,Image.SCALE_DEFAULT));
		JLabel logo=new JLabel(image1);
			
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(logo,"West");
		JPanel panel2=new JPanel();
		panel2.add(login);
		panel2.add(register);
		panel.add(panel2,"East");
		ImageIcon image2=new ImageIcon("images/image2.jpg");
		//����ͼƬ��С
		image2.setImage(image2.getImage().getScaledInstance(600,600,Image.SCALE_DEFAULT));
		JLabel picture=new JLabel(image2);
		DESKTOP_PANE.addComponentListener(new ComponentAdapter() {
            //ʹͼƬ����Ӧ
            public void componentResized(final ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                picture.setSize(e.getComponent().getSize());
            }
        });
		add(panel,"North");
		DESKTOP_PANE.add(picture,new Integer(Integer.MIN_VALUE));
		add(DESKTOP_PANE);
		setLocation(400,120);
		setSize(1000,800);
		setVisible(true);
		setResizable(false);
	}
	public static void main(String[] args){
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //���ù۸�
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		Main.getInstance();//��ȡʵ��
	}
}
