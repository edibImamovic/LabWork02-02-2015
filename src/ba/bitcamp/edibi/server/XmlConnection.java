package ba.bitcamp.edibi.server;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlConnection {

	private static Document xmlDoc;
	private static DocumentBuilder docreader;
	private static XPath xPath;

	public XmlConnection() throws ParserConfigurationException, SAXException,
			IOException {

		docreader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDoc = docreader.parse(new File("./File/New.xml"));

		xPath = XPathFactory.newInstance().newXPath();

	}

	public static int userLogin(String username, String password)
			throws TransformerConfigurationException {

		String expresion = "//User[@name = \"" + username
				+ "\" and @password =\"" + password + "\"]";
		System.out.println(expresion);
		try {
			Node user = (Node) xPath.compile(expresion).evaluate(xmlDoc,
					XPathConstants.NODE);
			if (user == null) {
				String expresion2 = "//User[@name = \"" + username + "\"]";
				Node user2 = (Node) xPath.compile(expresion2).evaluate(xmlDoc,
						XPathConstants.NODE);
				if (user2 == null) {

					Element newUser = xmlDoc.createElement("User");
					newUser.setAttribute("name", username);
					newUser.setAttribute("password", password);
					xmlDoc.getElementsByTagName("Users").item(0)
							.appendChild(newUser);
					StreamResult file = new StreamResult(new File(
							"./File/New.xml"));
					Transformer transformer;

					transformer = TransformerFactory.newInstance()
							.newTransformer();
					DOMSource source = new DOMSource(xmlDoc);
					transformer.transform(source, file);
					return 0;
				} else {
					return -1;
				}

			}

		} catch (XPathExpressionException
				| TransformerFactoryConfigurationError | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		}
		return 0;
	}

	public static void main(String[] args) {
		try {
			XmlConnection test = new XmlConnection();
			int num;
			try {
				num = XmlConnection.userLogin("Edibigi", "password");
				System.out.println(num);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
