package action;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gui.Frame2;
import gui.Frame3;
import gui.FrameMain;

public class Start {
	
	public static void main(String args[]){
		String currentTime, workTime, time0=null;
		int flag = 0;
		
		FrameMain fm = new FrameMain();
		Frame2 f2 = new Frame2();
		Frame3 f3 = new Frame3();
		Calculate cal = new Calculate();
		XMLCRUD xmlcrud = new XMLCRUD();
		
		//开始时清空
		cal.deleteAll();
		
		/* 循环 */
		while(true){
			currentTime = cal.getCurremtTime();
			
			if(flag == 0){
				time0 = currentTime;
				if(1 == cal.timeCompare("11:25:00", time0))
					xmlcrud.C("config/record.xml", "11:25:00" + "/下班", "98");
				
				if(1 == cal.timeCompare("18:00:00", time0))
					xmlcrud.C("config/record.xml", cal.getEndTime(time0) + "/下班", "99");
				flag++;
			}
			
			workTime = cal.getTime(currentTime, time0);
			
			//当时间为一个小时或xml中的时间时 提醒
			if(0 == cal.timeCompare(currentTime, cal.getMinTime(currentTime).split("/")[1])){
				f2.setLabel2("        " + cal.getMinTime(currentTime).split("/")[2]);
				f2.setVisible(true);
				cal.deleteByID(cal.getMinTime(currentTime).split("/")[0]);
				fm.getF3().load();
			}
			if(cal.remind(workTime)){
				f2.setLabel2("        一个小时了。");
				f2.setVisible(true);
			}
			
			fm.getLabel1().setText("当前时间 ：" + currentTime);
			fm.getLabel2().setText("已花时间 ：" + workTime);
			fm.getLabel4().setText("上班时间 ：" + time0);
			fm.getLabel5().setText("下班时间 ：" + cal.getEndTime(time0));
			
			//循环控制器
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}


