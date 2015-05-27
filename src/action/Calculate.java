package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calculate {
	private Calendar calendar;
	private int h, m, s;
	private Date d1, d2;
	private long wd;
	private int flag = 0;
	private String[] str;
	private ArrayList arrID = new ArrayList();
	private ArrayList arrTime = new ArrayList();
	private DateFormat df = new SimpleDateFormat("HH:mm:ss");
	private XMLCRUD xmlcrud = new XMLCRUD();
	
	public static void main(String args[]){
		Calculate c = new Calculate();
//		System.out.println(new Calculate().getTime("25:12:15", "12:12:13"));
//		new Calculate().loadID();
//		new Calculate().deleteByID("1");
		c.deleteAll();
	}
	
	/* 取得当前时间的 hh:mm:ss格式 */
	public String getCurremtTime(){
		calendar = Calendar.getInstance();
		h = calendar.get(Calendar.HOUR_OF_DAY);
		m = calendar.get(Calendar.MINUTE);
		s = calendar.get(Calendar.SECOND);
		return (h+":"+m+":"+s);
	}
	
	/* 取得时间差 */
	public String getTime(String t1, String t2){
		try{
			d1 = df.parse(t1);
			d2 = df.parse(t2);
		}catch(Exception e){
			System.out.println(e);
		}
		wd = d1.getTime() - d2.getTime();
		return wd / (1000 * 60 * 60) + ":" + wd / (1000 * 60)%60 + ":" + (wd / 1000)%60;
	}
	
	/* 计算下班时间 */
	public String getEndTime(String t1){
		if("".equals(t1) || t1 == null){
			return "18:00:00";
		}else{
			String t[] = t1.split(":");
			int h1 = Integer.parseInt(t[0]);
			
			if(h1 > 9)
				return "18:00:00";
			else
				return (h1+9) + ":" + t[1] + ":" + t[2];
		}
	}
	
	/* 比较时间 */
	public int timeCompare(String time1, String time2){
		if("".equals(time1) || "".equals(time2)){
			return 100;
		}else{
			try {
				if(df.parse(time1).getTime() == df.parse(time2).getTime())
					return 0;
				else if(df.parse(time1).getTime() > df.parse(time2).getTime())
					return 1;
				else
					return 2;
			} catch (ParseException e) {
				e.printStackTrace();
				return 100;
			}
		}
	}
	
	/* Message */
	public boolean remind(String t){
		t = t.split(":")[0];
		int it = Integer.parseInt(t);
		if(flag != it){
			flag = it;
			return true;
		}
		return false;
	}
	
	/* load id */
	public ArrayList loadID(){
		try {
			ArrayList al = xmlcrud.R("config/record.xml");

			for (int i = 0; i < al.size(); i++) {
				str = al.get(i).toString().split("/");
				arrID.add(str[0]);
			}
		} catch (Exception e) {
			System.out.println(e);
			javax.swing.JOptionPane.showMessageDialog(null, e);
		}
		return arrID;
	}
	
	/* load time */
	public ArrayList loadTime(){
		try {
			ArrayList al = xmlcrud.R("config/record.xml");

			for (int i = 0; i < al.size(); i++) {
				str = al.get(i).toString().split("/");
				arrTime.add(str[1]);
			}
		} catch (Exception e) {
			System.out.println(e);
			javax.swing.JOptionPane.showMessageDialog(null, e);
		}
		return arrTime;
	}
	
	/* get the latest time and id */
	public String getMinTime(String now){
		ArrayList al = xmlcrud.R("config/record.xml");
		String str[], time, id, txt, minID=null, minTime=null, minTxt=null;
		
		for(int i=0; i<al.size(); i++){
			str = al.get(i).toString().split("/");
			id = str[0];
			time = str[1];
			txt = str[2];
			try{
//				System.out.println(now + " || " + time + " || " + (df.parse(now).getTime() < df.parse(time).getTime()));
				if((minTime==null || minTime=="") && df.parse(now).getTime() < df.parse(time).getTime()){
					minID = id;
					minTime = time;
					minTxt = txt;
//					System.out.println(time);
				} else {
//					System.out.println(i
//							+ " || time="
//							+ time
//							+ " || now="
//							+ now
//							+ " || minTime="
//							+ minTime
//							+ (df.parse(now).getTime() < df.parse(time).getTime())
//							+ "||"
//							+ (df.parse(minTime).getTime() > df.parse(time)
//									.getTime()));
					if(!("" == minTime || minTime == null)){
						if (df.parse(minTime).getTime() > df.parse(time).getTime()) {
							minID = id;
							minTime = time;
							minTxt = txt;
							System.out.println(minID + "/" + minTime + "/" + minTxt);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		System.out.println(minID + "/" + minTime + "/" + minTxt);
		return minID + "/" + minTime + "/" + minTxt;
	}
	
	/* delete by id */
	public void deleteByID(String id){
		if("".equals(id)){
			return;
		}else{
			xmlcrud.D("config/record.xml", id);
		}
	}
	
	/* delete all */
	public void deleteAll(){
		loadID();
		for(int i=0; i<arrID.size(); i++){
			if(Integer.parseInt(arrID.get(i).toString())<100)
				deleteByID(arrID.get(i).toString().trim());
		}
	}
}
