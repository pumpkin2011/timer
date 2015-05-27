package gui;

import hello.Jtable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import action.XMLCRUD;

//weekly publication read
public class Frame4{
	private JFrame frame= null;
	private JScrollPane scrollPane= null;
	private JTable table = null;
	private table_model tmodel = null;
	private String xmlPath = "config/weeklyRecord.xml";
	private String date = null;
	private String content = null;
	private XMLCRUD xc = new XMLCRUD();
	private List list = null;
	private String cont[] = null;
	
	public Frame4(){
		list = new ArrayList();
		frame = new JFrame("Weekly publication");
		frame.setBounds(300, 200, 300, 400);
		frame.setVisible(true);
		tmodel = new table_model();
		table = new JTable(tmodel);
		scrollPane = new JScrollPane(table);
		frame.add(scrollPane);
		
//		xc.C(xmlPath, content, date);
		
		list = xc.R(xmlPath);
		Iterator it = list.iterator();
		while(it.hasNext()){
			cont = it.next().toString().split("/");
			tmodel.addRow(cont[0], cont[1]);
//			System.out.println(cont[0] + "||" + cont[1]);
			table.updateUI();
		}
	}
	
	public static void main(String args[]){
		new Frame4();
	}
	
}

class table_model extends AbstractTableModel{
	
	private Vector content = new Vector();
	private String title_name[] = {"Date", "Content"};
	
	public void addRow(String date, String cont){
		Vector v = new Vector(3);
		v.add(0, date);
		v.add(1, cont);
		
		content.add(v);
	}
	
	//set title 
	public String getColumnName(int col){
		return title_name[col];
	}
	
	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector)content.get(rowIndex)).get(columnIndex);
	}
	
}