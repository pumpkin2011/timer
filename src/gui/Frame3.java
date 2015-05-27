package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import action.Calculate;
import action.XMLCRUD;


//public class Frame3 extends JFrame{
//	public static void main(String args[]){
//		FrameRemind f3 = new FrameRemind();
//		
//		f3.setVisible(true);
//		f3.setResizable(false);
//		f3.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		f3.text1.setText("");
//		f3.text2.setText("");
//		f3.textArea.setText("");
//	}
//}

public class Frame3 extends JFrame implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private XMLCRUD xmlcrud;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JButton btn1;
	private JButton btn2;
	private JTextArea textArea;
	
	private int height = (int)screenSize.getHeight();
	private int width = (int)screenSize.getWidth();
	private int id=0;
	private String[] s;
	private Calculate cal;
	
	public static void main(String args[]){
		Frame3 f3 = new Frame3();
		
		f3.setVisible(true);
		f3.setResizable(true);
//		f3.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		f3.text1.setText("");
//		f3.text2.setText("");
//		f3.textArea.setText("");
	}
	
	public Frame3(){
		super();
		this.getText1().setText("");
		this.getText2().setText("");
		this.getTextArea().setText("");
		
		this.setLayout(null);
		this.setBounds(width/2-200, height/2-200, 490, 500);
		this.setResizable(false);
		
		this.add(this.getText1());
		this.add(this.getText2());
		this.add(this.getText3());
		this.add(this.getTextArea());
		this.add(this.getBtn1());
		this.add(this.getBtn2());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		load();
	}
	
	
	public void load(){
		this.getTextArea().setText("");
		try {
			ArrayList al = this.getXmlCrud().R("config/record.xml");

			for (int i = 0; i < al.size(); i++) {
				s = al.get(i).toString().split("/");
				textArea.append("id=" + s[0] + " --- " + s[1] + " --- " + s[2] + "\n");
//				 System.out.println(al.get(i));
			}
		} catch (Exception e) {
			System.out.println(e);
			javax.swing.JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void add(){
		this.getXmlCrud().C("config/record.xml", text1.getText() + "/" + text2.getText(), id+"");
		id++;
		textArea.setText("");
		load();
	}
	
	public void actionPerformed(ActionEvent e) {
		if("add".equals(e.getActionCommand())){
			if(!("".equals(text1.getText())) && !("".equals(text2.getText())))
				add();
			else
				javax.swing.JOptionPane.showMessageDialog(null, "error input");
		}
		
		if("delete".equals(e.getActionCommand())){
			if(!("".equals(text3.getText()))){
				this.getCal().deleteByID(text3.getText());
				load();
			}else{
				javax.swing.JOptionPane.showMessageDialog(null, "null");
			}
		}
	}
	
	//get
	
	
	public JTextField getText1(){
		if(text1 == null){
			text1 = new JTextField();
			text1.setBounds(10, 10, 100, 25);
		}
		return text1;
	}
	
	public JTextField getText2(){
		if(text2 == null){
			text2 = new JTextField();
			text2.setBounds(110, 10, 250, 25);
		}
		return text2;
	}
	
	public JTextField getText3(){
		if(text3 == null){
			text3 = new JTextField();
			text3.setBounds(110, 40, 250, 25);
		}
		return text3;
	}
	
	public JButton getBtn1(){
		if(btn1 == null){
			btn1 = new JButton();
			btn1.setBounds(362, 10, 100, 25);
			btn1.setActionCommand("add");
			btn1.setText("add");
		}
		return btn1;
	}
	
	public JButton getBtn2(){
		if(btn2 == null){
			btn2 = new JButton();
			btn2.setBounds(362, 40, 100, 25);
			btn2.setActionCommand("delete");
			btn2.setText("delete");
		}
		return btn2;
	}
	
	public JTextArea getTextArea(){
		if(textArea == null){
			textArea = new JTextArea();
			textArea.setBounds(10, 80, 455, 378);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
		}
		return textArea;
	}
	
	public XMLCRUD getXmlCrud(){
		if(xmlcrud == null){
			xmlcrud = new XMLCRUD();
		}
		return xmlcrud;
	}
	
	public Calculate getCal(){
		if(cal == null){
			cal = new Calculate();
		}
		return cal;
	}
	
}
