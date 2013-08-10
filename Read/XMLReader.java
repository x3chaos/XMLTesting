package org.x3chaos.XMLTesting;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	private static final double version = 0.1;

	private static final String ROOT = "The root element is: <%s>";

	private static final String DEF_OPTION =   "  %s: %s";
	private static final String LOC_OPTION =   "      %s: %s";
	private static final String COMMAND = "      - %s=%s";

	public static void main(String[] args) throws Exception {
		printInfo();

		// -------

		// create the document instance
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("Outcomes.xml"));

		// -------

		// get the root element as an object
		Element root = doc.getDocumentElement();
		String tagName = root.getTagName();
		System.out.println(String.format(ROOT, tagName) + "");

		// -------

		// get the default options
		NodeList dopList = root.getElementsByTagName("DefaultOptions");
		Element defOps = (Element) dopList.item(0);

		// print all default options
		System.out.println("\nDefault options:");
		NodeList optionsList = defOps.getChildNodes();

		for (int i = 0; i < optionsList.getLength(); i++) {
			Node optionNode = optionsList.item(i);

			if (optionNode.getNodeType() == Node.ELEMENT_NODE) {
				Element option = (Element) optionNode;
				String name = option.getTagName();
				String value = option.getTextContent();

				System.out.println(String.format(DEF_OPTION, name, value));
			}
		}

		// -------

		// get all outcomes
		NodeList outcomesList = root.getElementsByTagName("Outcome");

		// print info for each outcome
		System.out.println("\nOutcomes:");
		for (int i = 0; i < outcomesList.getLength(); i++) {
			Node outcomeNode = outcomesList.item(i);

			if (outcomeNode.getNodeType() == Node.ELEMENT_NODE) {
				Element outcome = (Element) outcomeNode;
				String name = outcome.getAttribute("name");
				System.out.println("  " + name + ":");

				// print local options, if they exist
				Element options = (Element) outcome.getElementsByTagName(
						"Options").item(0);

				if (options != null) {
					System.out.println("    Options:");
					NodeList locOps = options.getChildNodes();

					for (int j = 0; j < locOps.getLength(); j++) {
						Node locOpNode = locOps.item(j);

						if (locOpNode.getNodeType() == Node.ELEMENT_NODE) {
							Element locOp = (Element) locOpNode;
							String opName = locOp.getTagName();
							String opValue = locOp.getTextContent();

							System.out.println(String.format(LOC_OPTION,
									opName, opValue));
						}
					}
				} else {
					System.out.println("No local options.");
				}
				
				// print commands
				System.out.println("    Commands:");
				NodeList cmdList = outcome.getElementsByTagName("Command");
				
				for (int k = 0; k < cmdList.getLength(); k++) {
					Node cmdNode = cmdList.item(k);
					
					if (cmdNode.getNodeType() == Node.ELEMENT_NODE) {
						Element cmd = (Element) cmdNode;
						String src = cmd.getAttribute("src");
						String command = cmd.getTextContent();
						
						System.out.println(String.format(COMMAND, src, command));
					}
				}
			}
		}

	}

	private static void printInfo() {
		System.out.println("XMLReader v" + version);
		System.out.println("Author: x3chaos");
		System.out.println("Website: http://github.com/x3chaos/XMLTesting\n");
	}

}
