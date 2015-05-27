package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.Calculate;
import action.XMLCRUD;

import event.Listener;

/* main Frame */
public class FrameMain extends JFrame implements ActionListener{
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int)screenSize.getHeight();
	private int width = (int)screenSize.getWidth();
	
	private Frame3 f3;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label4;
	private JLabel label5;
	private JButton btn1;
	
	private TrayIcon trayIcon;//托盘图标
    private SystemTray systemTray;//系统托盘
    
    public void SystemTray(){
    	systemTray = SystemTray.getSystemTray();//获得系统托盘的实例
    	
    	try {
            // 定义托盘图标的图片
            String filePath = "com/images/001.jpg";
            String path = ClassLoader.getSystemResource(filePath).getFile();
            Image image = Toolkit.getDefaultToolkit().getImage(path);
            trayIcon = new TrayIcon(image);
            systemTray.add(trayIcon);//设置托盘的图标，0.gif与该类文件同一目录
            this.dispose();
        } catch (AWTException e2) {
            e2.printStackTrace();
        }
        
		this.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				dispose();// 窗口最小化时dispose该窗口
			}
		});
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)// 双击托盘窗口再现
				{
					setExtendedState(Frame.NORMAL);
				}
				setVisible(true);
			}
		});
    }
	
	public FrameMain(){
		
		this.setBounds(width/2-width/10, height/2-height/10, width/5, height/5);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("TIMER 1.0");
		this.setResizable(false);
		
		this.getLabel1().setBounds(20, 5, 150, 20);
		this.getLabel2().setBounds(20, 25, 150, 20);
		this.getLabel4().setBounds(20, 55, 150, 20);
		this.getLabel5().setBounds(20, 75, 150, 20);
		this.getBtn1().setBounds(150, 5, 80, 30);
		
		this.add(label1);
		this.add(label2);
		this.add(label4);
		this.add(label5);
		this.add(btn1);
		btn1.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if ("remind".equals(ae.getActionCommand())) {
			if(this.getF3().isShowing()){
				this.getF3().load();
				this.getF3().setVisible(false);
			}else{
				this.getF3().setVisible(true);
			}
		}
	}
	
	//get
	public JLabel getLabel1(){
		if(label1 == null){
			label1 = new JLabel("00:00:00");
		}
		return label1;
	}
	
	public JLabel getLabel2(){
		if(label2 == null){
			label2 = new JLabel("00:00:00");
		}
		return label2;
	}
	
	public JLabel getLabel4(){
		if(label4 == null){
			label4 = new JLabel("00:00:00");
		}
		return label4;
	}
	
	public JLabel getLabel5(){
		if(label5 == null){
			label5 = new JLabel("00:00:00");
		}
		return label5;
	}
	
	public JButton getBtn1(){
		if(btn1 == null){
			btn1 = new JButton("remind");
		}
		return btn1;
	}
	
	public Frame3 getF3(){
		if(f3 == null){
			f3 = new Frame3();
			f3.setTitle("REMINDER");
		}
		return f3;
	}
}







