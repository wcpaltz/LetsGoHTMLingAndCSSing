/**
 * Simple HTTP handler for testing ChronoTimer
 */
package Server.src.com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

	// a shared area where we get the POST data and then use it in the other
	// handler
	static String sharedResponse = "";
	static boolean gotMessageFlag = false;

	public static void main(String[] args) throws Exception {

		// How to grab css
		// server.createContext("/style.css", new StaticFileServer());
		
		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", new PostHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	static class DisplayHandler implements HttpHandler { // DELETE ALL THIS SHIT
		// Create route that handles the HREF for the link

		public void handle(HttpExchange t) throws IOException {

			//This will have to be used for reading, uncomment when we start implementing - WP
		//	String encoding = "UTF-8";
			//t.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
			//add CSS - Zak
			String response =
			"<!DOCTYPE html>  "
			+ "<html>"   
		  	+ "<head>"
		    + "<link rel=\"stylesheet\" href=\"style.css\">"
		    + "</head>"
		    + "<style>"
		    + "</style> <body>"
		    + "<h2>Lab9 - Give us the Thai candy</h2>"
		    + "	<table>"
		    + "   <tr class=\"header\">"
		    + "       <th>Title</th>"
		    + "       <th>First Name</th>"
		    + "       <th>Last Name</th>"
		    + "       <th>Department</th>"
		    + "       <th>Phone</th>"
		    + "       <th>Gender</th>"
		    + "  </tr>";
		    //TODO
			
			//Add HTML Response	
			
			//This will finish this, uncomment when we start implementing - WP
//			t.sendResponseHeaders(200, response.length());
//			Writer os = new OutputStreamWriter(t.getResponseBody(), encoding);
//			os.write(response);
//			os.close();
			
			//String response = "Begin of response\n";
			Gson g = new Gson();
			// set up the header
			System.out.println(response);
			ArrayList<Employee> fromJson = new ArrayList<Employee>();
			try {
				if (!sharedResponse.isEmpty()) {
					System.out.println(response);
					fromJson = g.fromJson(sharedResponse,
							new TypeToken<Collection<Employee>>() {
					}.getType());

					System.out.println(response);
					response += "Before sort\n";
					for (Employee e : fromJson) {
						response += e + "\n";
					}
					Collections.sort(fromJson);
					response += "\nAfter sort\n";
					for (Employee e : fromJson) {
						response += e + "\n";
					}
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
			boolean oddEmployee = true;
			for(Employee emp: fromJson){
				if(oddEmployee){
					response+= "<tr class=\"Oemployee\">";
					oddEmployee = false;
				}else{
					response+= "<tr class=\"Eemployee\">";
					oddEmployee = true;
				}
				response += emp.HTMLEmployee() + "</tr>";
			}
		    response += 
		    "	</table>"
		    + "</body>"
		    + "</html>";
			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {

			// shared data that is used with other handlers
			sharedResponse = "";

			// set up a stream to read the body of the request
			InputStream inputStr = transmission.getRequestBody();

			// set up a stream to write out the body of the response
			OutputStream outputStream = transmission.getResponseBody();

			// string to hold the result of reading in the request
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb = sb.append((char) nextChar);
				nextChar = inputStr.read();
			}

			if (sharedResponse.equalsIgnoreCase("ADD ")) { // ADD response
				try {

					PrintWriter writer = new PrintWriter("server.txt", "UTF-8");
					writer.println(sb.toString());
					writer.close();

				} catch (Exception e) {
					System.out.println();
				}
			}

			else if (sharedResponse.equalsIgnoreCase("PRINT")) {
				Gson g = new Gson();
				ArrayList<Employee> fromText = new ArrayList();
			}

			else { // CLEAR response
				try {

					PrintWriter writer = new PrintWriter("server.txt", "UTF-8");
					writer.println("");
					writer.close();

				} catch (Exception e) {
					System.out.println();
				}
			}

			// create our response String to use in other handler
			sharedResponse = sharedResponse + sb.toString();

			// respond to the POST with ROGER
			String postResponse = "POST REQUEST RECEIVED";

			System.out.println("response: " + sharedResponse);

			// Desktop dt = Desktop.getDesktop();
			// dt.open(new File("raceresults.html"));

			// assume that stuff works all the time
			transmission.sendResponseHeaders(300, postResponse.length());

			// write it and return it
			outputStream.write(postResponse.getBytes());

			outputStream.close();
		}
	}

}