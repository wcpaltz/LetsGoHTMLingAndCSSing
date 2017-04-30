/**
 * Simple HTTP handler for testing ChronoTimer
 */
package Server.src.com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Client.src.Participants;

public class Test {

	// a shared area where we get the POST data and then use it in the other
	// handler
	static String sharedResponse = "";
	static String localResponse = "";
	static boolean gotMessageFlag = false;
//	static ArrayList<Employee> fromJson = new ArrayList<Employee>();
//	static ArrayList<Employee> eList = new ArrayList<Employee>();
	
	static Racer newRacer;
	static ArrayList<Racer> rList = new ArrayList<Racer>();
	static String fileName = "server.txt";
	static Scanner scan;
	static NameMap nameMap = new NameMap();
	
	public static void main(String[] args) throws Exception {

		try{
		scan = new Scanner(fileName);
		}catch(Exception e){
			System.err.println("No such file found.");
		}
		
		//Create NameMap database
		while(scan.hasNextLine()){
			String[] content = scan.nextLine().split(":");
			String bib = content[0].trim();
			String name = content[1].trim();
			
			nameMap.put(bib, name);
		}
		
		// How to grab css
		// server.createContext("/style.css", new StaticFileServer());

		// set up a simple HTTP server on our local host, sets up socket
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());

		server.createContext("/displayresults/css", new CSSHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", new PostHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	static class CSSHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// This will have to be used for reading, uncomment when we start
			// implementing - WP
			String response = "";
//			t.getResponseHeaders().set("Content-Type", "text/plain; charset=" + response);

			//You should probably use a file reader...
			response += "table{width: 800px; text-align:center;}";
			response += "tr:header{color:blue;}";
			response += "tr.Eemployee, tr.Oemployee{font-size: 14px;}";
			response += "tr.Eemployee{ background-color:#efefef;}";
			response += "th{font-size: 22px;border-bottom: 2px solid #111111;border-top: 1px solid #999;}";

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	// call will be executed when you type route in web browser/or from client.
	// Executed every time someone goes to site
	static class DisplayHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// This will have to be used for reading, uncomment when we start
			// implementing - WP
			String response = "UTF-8";
			t.getResponseHeaders().set("Content-Type", "text/html; charset=" + response);
			response = // TODO: Response+= previously, now changed
					"<!DOCTYPE html>  " + "<html>" + "<head>"
							+ "<link href=\"/displayresults/css\" rel=\"stylesheet\" type=\"text/css\">" + "</head>" + "<style>"
							+ "</style> <body>" + "<h2>Lab9 - Give us the Thai candy</h2>" + "	<table>"
							+ "   <tr class=\"header\">" + "       <th>Title</th>" + "       <th>First Name</th>"
							+ "       <th>Last Name</th>" + "       <th>Department</th>" + "       <th>Phone</th>"
							+ "       <th>Gender</th>" + "  </tr>";
			// TODO

			// Add HTML Response
			// String response = "Begin of response\n";
			// set up the header

			System.out.println(response);

			boolean oddRacer = true;
			for (Racer r : rList) {
				if (oddRacer) {
					response += "<tr class=\"Oemployee\">";
					oddRacer = false;
				} else {
					response += "<tr class=\"Eemployee\">";
					oddRacer = true;
				}
				response += r.HTMLRacer() + "</tr>";
			}
			response += "	</table>" + "</body>" + "</html>";

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	// start with post and grab information correctly to store information
	// correctly
	static class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {

			// shared data that is used with other handlers
			sharedResponse = "";

			// set up a stream to read the body of the request, what we get from
			// client
			InputStream inputStr = transmission.getRequestBody();

			// set up a stream to write out the body of the response
			OutputStream outputStream = transmission.getResponseBody();

			// string to hold the result of reading in the request
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				// should be whatever the client sends to us
				sharedResponse += ((char) nextChar);
				nextChar = inputStr.read();
			}
			Gson g = new Gson();
			// parse command TODO, need better way to write to file possibly
			if (sharedResponse.substring(0, 3).equals("ADD")) { // ADD response
				sharedResponse = sharedResponse.substring(4);
				
				Racer r = g.fromJson(sharedResponse, new TypeToken<Racer>(){
				}.getType());
				
				if(nameMap.containsKey(r.getBib())){
					r.setName(nameMap.get(r.getBib()));
				}
				
				rList.add(r);

			}

			// else if (sharedResponse.substring(0,5).equals("PRINT")) {
			// Gson g = new Gson();
			// ArrayList<Employee> fromText = new ArrayList();
			// }

			// make else if TODO
			// else if { // CLEAR response
			// try {
			//
			// PrintWriter writer = new PrintWriter("server.txt", "UTF-8");
			// writer.println("");
			// writer.close();
			//
			// } catch (Exception e) {
			// System.out.println();
			// }
			// }

			else {
				System.err.println("Unrecognized command.");
			}

			// create our response String to use in other handler
			sharedResponse = sharedResponse + sb.toString();

			// respond to the POST with ROGER
			String postResponse = "POST REQUEST RECEIVED";

			// System.out.println("" + fromJson.get(0).HTMLEmployee());
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