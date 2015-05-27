package event;

import gui.Frame3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener{
	
	Frame3 f3 = new Frame3();;
	
	public void actionPerformed(ActionEvent e) {
		
		if("action".equals(e.getActionCommand()));
//			f3.frame3();
		
		if("add".equals(e.getActionCommand()));
//			f3.add();
	}

}
