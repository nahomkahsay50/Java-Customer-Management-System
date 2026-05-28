package week15finalproject;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Name: ModifyCustomers
 * Abstract: Reads customers.xml and creates a modified version (customers_modified.xml)
 * with additional fields: phone, contactName, and email
 * 
 * @author Nahom Kahsay
 * @version 1.0
 * @since 12/09/2025
 */
public class ModifyCustomers {

	/**
	 * Name: CreateModifiedCustomerFile
	 * Abstract: Reads customers.xml and creates customer_modified.xml
	 * with additional customer information fields
	 * 
	 */
	public static void CreateModifiedCustomerFile() {
		
		String strFilePath = "customers.xml";
		File xmlFile = new File(strFilePath);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Reading customers.xml file...");
			
			// Add new fields to each customer
			AddNewFields(doc);
			
			System.out.println("Modifying customers records...");
			
			// Write the updated document to file
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("customer_modified.xml"));
			
			
			// Format XML output with indentation
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			transformer.transform(source, result);
			
			System.out.println();
			System.out.println("XML file created successfully!");
			System.out.println("File: customer_modified.xml");
			System.out.println("New fields added: phone, contact name, email address");
			
		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
			System.out.println("Error modifying customers.xml file");
			System.out.println("Error details: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Name: AddNewFields
	 * Abstract: Adds phones, contactName, and email elements to each customer record
	 * 
	 * @param doc - the Document object to modify
	 */
	private static void AddNewFields(Document doc) {
		NodeList customers = doc.getElementsByTagName("Customer");
		Element customer = null;
		
		// Loop for each customer 
		for (int i = 0; i < customers.getLength(); i++) {
			customer = (Element) customers.item(i);
			
			// Get customer ID for generating test data
			String strCustomerId = customer.getAttribute("id");
			
			// Add phone element
			Element phoneElement = doc.createElement("phone");
			phoneElement.appendChild(doc.createTextNode(GeneratePhone(strCustomerId)));
			customer.appendChild(phoneElement);
			
			// Add contactName element
			Element contactElement = doc.createElement("contactName");
			contactElement.appendChild(doc.createTextNode(GenerateContactName(strCustomerId)));
			customer.appendChild(contactElement);
			
			// Add email element
			Element emailElement = doc.createElement("email");
			emailElement.appendChild(doc.createTextNode(GenerateEmail(strCustomerId)));
			customer.appendChild(emailElement);
		}
	}
	
	/**
	 * Name: GeneratePhone
	 * Abstract: Generates mock phone number based on customer ID
	 * 
	 * @param strCustomerId - customer ID used to generate unique phone number
	 * @return formatted phone number string
	 */
	private static String GeneratePhone(String strCustomerId) {
		int intId = Integer.parseInt(strCustomerId);
		int intBaseNumber = 5550100 + (intId * 10);
		return String.format("(513) 555-%04d", intBaseNumber % 10000);
	}
	
	/**
	 * Name: GenerateContactName
	 * Abstract: Generates mock contact name based on customer ID 
	 * 
	 * @param strCustomerId - customer ID used to generate unique contact name
	 * @return contact name string
	 */
	private static String GenerateContactName(String strCustomerId) {
		String[] astrFirstNames = {"John", "Sarah", "Michael", "Jennifer", "David", "Lisa", "Robert", "Mary"};
		String[] astrLastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
		
		int intId = Integer.parseInt(strCustomerId);
		int intFirstIndex = (intId - 1) % astrFirstNames.length;
		int intLastIndex = (intId - 1) % astrLastNames.length;
		
		return astrFirstNames[intFirstIndex] + " " + astrLastNames[intLastIndex];
	}
	
	/**
	 * Name: GenerateEmail
	 * Abstract: Generates mock email address based on customer ID
	 * 
	 * @param strCustomerId - customer ID used to generate unique email
	 * @return email address string
	 */
	private static String GenerateEmail(String strCustomerId) {
		String[] astrDomains = {"gmail.com", "yahoo.com", "outlook.com", "company.com", "business.com"};
		
		int intId = Integer.parseInt(strCustomerId);
		int intDomainIndex = (intId - 1) % astrDomains.length;
		
		return "customer" + strCustomerId + "@" + astrDomains[intDomainIndex];
	}
}
