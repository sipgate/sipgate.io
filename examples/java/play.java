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
import javax.xml.transform.TransformerConfigurationException;
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

public class play
{
	public static void main(String[] args) throws Exception
	{
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(3000),0);
		httpServer.createContext("/", new AwesomeRejectHandler());
		httpServer.setExecutor(null);
		httpServer.start();

		System.out.println("Server running....");
		System.out.println("Press CTRL+C to stop the server");
	}

	static class AwesomeRejectHandler implements HttpHandler
	{
		public void handle(HttpExchange httpExchange) throws IOException
		{
			InputStream inputStream = httpExchange.getRequestBody();
			int charValue;
			ArrayList<Character> chars = new ArrayList<Character>();

			while ((charValue = inputStream.read()) != -1)
			{
				chars.add((char) charValue);
			}

			StringBuilder stringBuilder = new StringBuilder(chars.size());

			for (Character character : chars)
			{
				stringBuilder.append(character);
			}
			// Print from, to and direction
			System.err.println("INFO: DATA: " + stringBuilder.toString());

			String XMLResponse = "";
			XMLBuilder xmlBuilder;
			try
			{
				xmlBuilder = new XMLBuilder();
				if (stringBuilder.toString().contains("from=4915799912345"))
				{
					XMLResponse = xmlBuilder.play("http://yourawesomewavfile.wav");
				}

			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			}

			// Print XML
			System.err.println("INFO: XML-Response: " + XMLResponse);

			Headers headers = httpExchange.getResponseHeaders();
			headers.set("Content-Type", "application/xml");
			headers.set("Accept-Encoding", "gzip");
			httpExchange.sendResponseHeaders(200, XMLResponse.length());
			OutputStream outputStream = httpExchange.getResponseBody();
			outputStream.write(XMLResponse.getBytes());
			outputStream.close();
		}
	}

	static class XMLBuilder
	{
		private Document document;
		private Element root;

		public XMLBuilder() throws ParserConfigurationException
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
			//<Response> is the root tag 
			this.root = document.createElement("Response");
			document.appendChild(this.root);
			this.document = document;
		}

		private String dial(String number)
		{
			Element xmlNodeDial = document.createElement("Dial");
			root.appendChild(xmlNodeDial);
			Element xmlNodeNumber = document.createElement("Number");
			xmlNodeDial.appendChild(xmlNodeNumber);
			xmlNodeNumber.setTextContent(number);
			return build();
		}

		private String send2voicemail()
		{
			Element xmlNodeDial = document.createElement("Dial");
			root.appendChild(xmlNodeDial);
			Element xmlNodeVoicemail = document.createElement("Voicemail");
			xmlNodeDial.appendChild(xmlNodeVoicemail);
			return build();
		}
		
		private String reject(String reason)
		{
			Element xmlNodeDial = document.createElement("Reject");
			root.appendChild(xmlNodeDial);
			Attr xmlAttribute = document.createAttribute("reason");
			xmlAttribute.setValue(reason);
			xmlNodeDial.setAttributeNode(xmlAttribute);
			return build();
		}
        
		private String play(String path)
		{
			Element xmlNodePlay = document.createElement("Play");
			root.appendChild(xmlNodePlay);
			Element xmlNodeUrl = document.createElement("Url");
			xmlNodePlay.appendChild(xmlNodeUrl);
			xmlNodeUrl.setTextContent(path);
			return build();
		}

		private String build()
		{
			String XMLResponse = "";

			try
			{
				// Write the document into XMl format and return it
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource domSource = new DOMSource(document);
				StringWriter stringWriter = new StringWriter();
				StreamResult streamResult = new StreamResult(stringWriter);
				transformer.transform(domSource, streamResult);
				XMLResponse = stringWriter.toString();
			}
			catch (TransformerException tranformerException)
			{
				tranformerException.printStackTrace();
			}
			return XMLResponse;
		}
	}
}
