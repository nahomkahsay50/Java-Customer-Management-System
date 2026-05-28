package week15finalproject;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Name: ViewCustomers
 * Abstract: Parses customers.xml file and displays customer information
 * in a formatted report listing. Includes XML logging for enhancement.
 * 
 * @author Nahom Kahsay
 * @version 1.0
 * @since 12/09/2025
 */

public class ViewCustomers {

	// Step 5 Enhancement - Created a logger for tracking customer viewing operations
	private static final Logger LOGGER = Logger.getLogger(ViewCustomers.class.getName());
	
	/**
	 * Name: DisplayCustomers
	 * Abstract: Reads and displays customer data from customers.xml
	 * Enhancement: Logs all operations at INFO, WARNING, and SEVERE levels
	 */
	public static void DisplayCustomers() {
		FileHandler fh = null;
		String strFilePath = "customers.xml";
		File xmlFile = new File(strFilePath);
		
		try {
			// Step 5 Enhancement - Set up XML logging
			fh = new FileHandler("customer_operations.xml", true);
			LOGGER.setLevel(Level.ALL);
			LOGGER.addHandler(fh);
			
			//Step 5 Enhancement - Log program start
			LOGGER.log(Level.INFO, "ViewCustomers operation started");
			
			// Check if file exists
			if (!xmlFile.exists()) {
				// Step 5 Enhancement - Log SEVERE error for missing file
				LOGGER.log(Level.SEVERE, "customers.xml file not found in project directory");
				throw new Exception("customers.xml file not found in project directort");
			}
			
			// Step 5 Enhancement - Log INFO for successful file location
			LOGGER.log(Level.INFO, "customers.xml file found successfully");
			
			// Create document builder
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			
			// Normalize document
			doc.getDocumentElement().normalize();
			
			
			// Display root element
			System.out.println("root element is: " + doc.getDocumentElement().getNodeName());
			System.out.println();
			
			
			// Get all customers elements
			NodeList customerList = doc.getElementsByTagName("Customer");
			
			
			// Check if customer exist
			if (customerList.getLength() == 0) {
				// Step 5 Enhancement - Log WARNING for empty customer list
				LOGGER.log(Level.WARNING, "No customers found in the XML file");
				System.out.println("No customers found in the XML file");
				return;
			}
			
			// Step 5 Enhancement - LOG INFO with customer count
			LOGGER.log(Level.INFO, "Found " + customerList.getLength() + " customers in file.");
			
			
			// Loop through each customer and display information
			for (int i = 0; i < customerList.getLength(); i++) {
				Element customer = (Element) customerList.item(i);
				
				// Get customer ID from attribute
				String strId = customer.getAttribute("id");
				
				// Get customer details from child elements
				String strName = GetElementValue(customer, "name");
				String strType = GetElementValue(customer, "type");
				String strAddress = GetElementValue(customer, "address");
				String strCity = GetElementValue(customer, "city");
				String strState = GetElementValue(customer, "state");
				String strZipcode = GetElementValue(customer, "zipcode");
				
				// Display customer information
				System.out.println("Customer ID " + strId);
				System.out.println("Name: " + strName);
				System.out.println("Type: " + strType);
				System.out.println("Address: " + strAddress);
				System.out.println(strCity + ", " + strState + " " + strZipcode);
				System.out.println();
			}
			
			System.out.println("Total customers displayed: " + customerList.getLength());
			
			
			// Step 5 enhancement - Log successful completion
			LOGGER.log(Level.INFO, "ViewCustomers operation completed successfully");
			
		} catch (SAXException e) {
			// Step 5 Enhancement - Log SEVERE error for XML parsing issues
			LOGGER.log(Level.SEVERE, "XML parsing error: " + e.getMessage(), e);
			System.out.println("Error parsing customers.xml file");
			System.out.println("Error details: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			// Step 5 Enhancement - Log SEVERE error for parser configuration
			LOGGER.log(Level.SEVERE, "Parser configuration error: " + e.getMessage(), e);
			System.out.println("Error configuring XML parser");
			System.out.println("Error details: " + e.getMessage());
		} catch (IOException e) {
			// Step 5 Enhancement - Log SEVERE error for I/O issues
			LOGGER.log(Level.SEVERE, "I/O error reading files: " + e.getMessage(), e);
			System.out.println("Error reading customer.xml file");
			System.out.println("Error details: " + e.getMessage());
		} catch (Exception e) {
			// Step 5 Enhancement - Log SEVERE error for any other issues
			LOGGER.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
			System.out.println("Error reading customers.xml file");
			System.out.println("Error details: " + e.getMessage());
		} finally {
			// Step 5 Enhancement - Close file handler to ensure data is written
			if (fh != null) {
				fh.close();
			}
		}
	}
	
	/**
	 * Name: GetElementValue
	 * Abstract: Extracts text content from specified XML element
	 * 
	 * @param parent - parent element containing the child element
	 * @param strTagName - name of the child element to extract
	 * @return text content of the element, or empty string if not found
	 */
	private static String GetElementValue(Element parent, String strTagName) {
		NodeList nodeList = parent.getElementsByTagName(strTagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Element element = (Element) nodeList.item(0);
			if (element.getFirstChild() != null) {
				return element.getFirstChild().getNodeValue();
			}
		}
		return "";
	}

}
