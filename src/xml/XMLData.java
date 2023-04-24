package xml;

import entities.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLData {

	public static final String xmlPath = "output.xml";

	public static void writeXML() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// create new root
//			Element elementRoot = document.createElement("students");
			Element elementRoot = (Element) document.getElementsByTagName("students").item(0);

			// add element to document
			document.appendChild(elementRoot);

			// create student element
			Element studentElement = document.createElement("student");
			elementRoot.appendChild(studentElement);

			// add info for student
			Element nameStudent = document.createElement("name");
			nameStudent.appendChild(document.createTextNode("Ngô Thanh Đạt"));
			studentElement.appendChild(nameStudent);
			Element schoolName = document.createElement("school-name");
			schoolName.appendChild(document.createTextNode("VKU"));
			studentElement.appendChild(schoolName);
			Element gender = document.createElement("gender");
			gender.appendChild(document.createTextNode("Nam"));
			studentElement.appendChild(gender);
			Element age = document.createElement("age");
			age.appendChild(document.createTextNode("19"));
			studentElement.appendChild(age);
			Element mark = document.createElement("mark");
			mark.appendChild(document.createTextNode("10"));
			studentElement.appendChild(mark);

			// write file

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new FileWriter(xmlPath, true));

			System.out.println("Done");

			transformer.transform(source, streamResult);


		} catch (ParserConfigurationException | TransformerException | IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static ArrayList<Student> readXML() {
		ArrayList<Student> list = new ArrayList<>();

		File file = new File(xmlPath);

		try {
			// create DocumentBuilderFactory instance
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			// create DocumentBuilder instance
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList listNode = document.getElementsByTagName("student");
			for(int i = 0; i < listNode.getLength(); i++) {
				Node node = listNode.item(i);
				System.out.println("Element: " + node.getNodeName());
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println("Name: " + element.getElementsByTagName("name").item(0).getTextContent());
				}
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}

		return list;

	}


}
