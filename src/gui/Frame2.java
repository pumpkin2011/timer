package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame2 extends JFrame{
	private JFrame frame2 = null;
	private JLabel label2;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int)screenSize.getHeight();
	private int width = (int)screenSize.getWidth();
	
//	public static void main(String args[]){
//		Frame2 f2 = new Frame2();
//		f2.setVisible(true);
//	}
	
	public Frame2(){
		super();
		this.setBounds(width/2, height/2, 200, 100);
		this.setResizable(false);
		this.setTitle("MESSAGE");
		this.getLabel2().setText("   是时候了");
		this.add(this.getLabel2());
		this.setAlwaysOnTop(true);
	}
	
	/* get */
	public JLabel getLabel2(){
		if(label2 == null){
			label2 = new JLabel();
		}
		return label2;
	}
	
	public void setLabel2(String str){
		this.getLabel2().setText(str);
	}
}
