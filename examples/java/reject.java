import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class reject {

	public static void main(String[] args) throws Exception {

		HttpServer httpServer = HttpServer.create(new InetSocketAddress(1194),
				0);
		httpServer.createContext("/", new AwesomeRejectHandler());
		httpServer.setExecutor(null);
		httpServer.start();

		System.out.println("Server running....");
		System.out.println("Press CTRL+C to stop the server");
	}

	static class AwesomeRejectHandler implements HttpHandler {

		public void handle(HttpExchange httpExchange) throws IOException {

			InputStream inputStream = httpExchange.getRequestBody();
			int charValue;
			ArrayList<Character> chars = new ArrayList<Character>();

			while ((charValue = inputStream.read()) != -1) {
				chars.add((char) charValue);
			}

			StringBuilder stringBuilder = new StringBuilder(chars.size());

			for (Character character : chars) {
				stringBuilder.append(character);
			}
			// Print data
			System.err.println("INFO: DATA: " + stringBuilder.toString());

			String XMLResponse = "";

			if (stringBuilder.toString().contains("from=49157123456789")) {
				XMLResponse = XMLParser("busy"); //sent busy signal
			} else {
				XMLResponse = XMLParser(""); // default reason value for reject
			}

			// Print XML
			System.err.println("INFO: XML-Response: " + XMLResponse);

			Headers headers = httpExchange.getResponseHeaders();
			headers.set("Content-Type", "application/xml");
			httpExchange.sendResponseHeaders(200, XMLResponse.length());
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(XMLResponse.getBytes());
			outputStream.close();
		}

		// Build XML with DOM and return as string
		public String XMLParser(String reason) throws IOException {
			String XMLResult = "";

			try {
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory
						.newDocumentBuilder();

				// First node
				Document document = documentBuilder.newDocument();
				document.setXmlStandalone(true);
				Element xmlNodeResponse = document.createElement("Response");
				document.appendChild(xmlNodeResponse);

				// Second node
				Element xmlNodeReject = document.createElement("Reject");
				xmlNodeResponse.appendChild(xmlNodeReject);

				// Second node attribute
				if (!reason.isEmpty()) {
					Attr xmlAttribute = document.createAttribute("reason");
					xmlAttribute.setValue(reason);
					xmlNodeReject.setAttributeNode(xmlAttribute);
				}

				// Write the content into XMl format
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();

				DOMSource domSource = new DOMSource(document);

				StringWriter stringWriter = new StringWriter();

				StreamResult streamResult = new StreamResult(stringWriter);

				transformer.transform(domSource, streamResult);
				XMLResult = stringWriter.toString();

			} catch (ParserConfigurationException parserConfigurationException) {
				parserConfigurationException.printStackTrace();
			} catch (TransformerException tranformerException) {
				tranformerException.printStackTrace();
			}
			return XMLResult.toString();
		}
	}
}
