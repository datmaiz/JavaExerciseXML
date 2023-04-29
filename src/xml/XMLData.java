package xml;

import entities.ListStudent;
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
import javax.xml.transform.TransformerConfigurationException;
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

	public static void writeXML(ArrayList<Student> listStudents) {
		try {
			DocumentBuilderFactory documentBuilderFactor = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactor.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element root = document.createElement("class");
			document.appendChild(root);

			for (Student student : listStudents) {
				Element studentElement = document.createElement("student");
				root.appendChild(studentElement);

				Element nameStudents = document.createElement("name");
				nameStudents.appendChild(document.createTextNode(student.getName()));
				studentElement.appendChild(nameStudents);

				Element nameClass = document.createElement("school");
				nameClass.appendChild(document.createTextNode(student.getNameSchool()));
				studentElement.appendChild(nameClass);

				Element gender = document.createElement("gender");
				gender.appendChild(document.createTextNode(student.getGender()));
				studentElement.appendChild(gender);

				Element age = document.createElement("age");
				age.appendChild(document.createTextNode(String.valueOf(student.getAge())));
				studentElement.appendChild(age);

				Element mark = document.createElement("mark");
				mark.appendChild(document.createTextNode(String.valueOf(student.getMark())));
				studentElement.appendChild(mark);

			}
			// write file

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlPath));

			System.out.println("Done");

			transformer.transform(source, streamResult);

		} catch (ParserConfigurationException | TransformerException e) {
			throw new RuntimeException(e);
		}

	}

	public static ArrayList<Student> readXML() {
		ArrayList<Student> listStudent = new ArrayList<Student>();

		File file = new File(xmlPath);

		try {
			// create DocumentBuilderFactory instance
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			// create DocumentBuilder instance
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList listNode = document.getElementsByTagName("student");
			for (int i = 0; i < listNode.getLength(); i++) {
				Node node = listNode.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Element name = (Element) element.getElementsByTagName("name").item(0);
					Element school = (Element) element.getElementsByTagName("school").item(0);
					Element gender = (Element) element.getElementsByTagName("gender").item(0);
					Element age = (Element) element.getElementsByTagName("age").item(0);
					Element mark = (Element) element.getElementsByTagName("mark").item(0);

					Student student = new Student(
							name.getTextContent(),
							school.getTextContent(),
							gender.getTextContent(),
							Integer.parseInt(age.getTextContent()),
							Double.parseDouble(mark.getTextContent())
					);
					System.out.println(student.getName());
					listStudent.add(student);
				}
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}

		return listStudent;

	}

	public static void main(String[] args) {
		writeXML(ListStudent.listStudents);
	}

}
