import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class server {

	public static void main(String[] args) throws Exception {
		// Create server with port 3000
		HttpServer httpserver = HttpServer.create(new InetSocketAddress(3000), 0);
		httpserver.createContext("/", new MyHandler());
		// creates a default executor
		httpserver.setExecutor(null);
		httpserver.start();

		System.out.println("Server running....");
		System.out.println("Press CTRL+C to stop the server");
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange httpexchange) throws IOException {
			//Read HTTP header
			InputStream is = httpexchange.getRequestBody();
			int charValue;
			
			ArrayList<Character> chars = new ArrayList<Character>();
			
			// Print data from header
			while ((charValue = is.read()) != -1) {
				chars.add((char) charValue);
				System.out.print((char) charValue);
			}
			
			StringBuilder builder = new StringBuilder(chars.size());
			
			//Build string from characters
			for (Character ch : chars) {
				builder.append(ch);
			}
			
			//Check which number is calling
			if (builder.toString().contains("from=491786067209"))
				System.out.println("\nDo something here");

			System.out.println("\n200 OK");
			OutputStream os = httpexchange.getResponseBody();
			os.close();
		}
	}

}
