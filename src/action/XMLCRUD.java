package action;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLCRUD {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private String xmlPath = null;
	
	public static void main(String args[]){
//		new XMLCRUD().R();
//		new XMLCRUD().C("a");
//		new XMLCRUD().U();
//		new XMLCRUD().D("0");
	}
	
	//查
	public ArrayList R(String xmlPath) {
		ArrayList alist = new ArrayList();
		
		dbf.setIgnoringElementContentWhitespace(true);

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlPath);

			NodeList sonlist = doc.getElementsByTagName("p");
			for (int i = 0; i < sonlist.getLength(); i++) // 循环处理对象
			{
				Element son = (Element) sonlist.item(i);

				for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						String name = node.getNodeName().toString();
						String id = node.getAttributes().getNamedItem("id").toString();
						String value = node.getFirstChild().getNodeValue();
						alist.add(id.split("\"")[1] + "/" + value);
//						System.out.println(name + " : " + value + ":" +id.split("\"")[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alist;
	}

	//增
	public void C(String xmlPath, String str, String id){
		dbf.setIgnoringElementContentWhitespace(false);

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmldoc = db.parse(xmlPath);

			Element root = xmldoc.getDocumentElement();

			// 删除指定节点

//			Element son = xmldoc.createElement("event");
//			son.setAttribute("id", "004");

			Element name = xmldoc.createElement("event");
			name.setAttribute("id", id);
			name.setTextContent(str);
//			son.appendChild(name);

//			Element age = xmldoc.createElement("age");
//			age.setTextContent("0");
//			son.appendChild(age);

			root.appendChild(name);
			// 保存
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//改
	public void U(String xmlPath){
		 dbf.setIgnoringElementContentWhitespace(true);
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmldoc = db.parse(xmlPath);

			Element root = xmldoc.getDocumentElement();
			
			Element per = (Element)	selectSingleNode("son[@id='004']", root);
			per.getElementsByTagName("age").item(0).setTextContent("27");

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删
	public void D(String xmlPath, String id){
		System.out.println(id);      
		dbf.setIgnoringElementContentWhitespace(true);
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmldoc = db.parse(xmlPath);

			Element root = xmldoc.getDocumentElement();
			
			Element son = (Element)selectSingleNode("event[@id="+ id.trim() +"]", root);
			root.removeChild(son);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));

		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, "delete wrong id");
		}
	}
	
	public static Node selectSingleNode(String express, Element source) {
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath
					.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}
	
}