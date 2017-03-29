package Client.src;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Client {

	public static void main(String[] args) {
		
		Scanner stdIn = new Scanner(System.in);
		
		try {
			System.out.println("in the client");

			// Client will connect to this location
			URL site = new URL("http://localhost:8000/sendresults");
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();

			while(true){ //TODO: Remove this in place for UI?
				String content = "";

//				// now create a POST request
//				conn.setRequestMethod("POST");
//				conn.setDoOutput(true);
//				conn.setDoInput(true);
//				DataOutputStream out = new DataOutputStream(conn.getOutputStream());

				//Take user input
				String command = stdIn.nextLine();
				
				while(!(command.equalsIgnoreCase("ADD")||command.equalsIgnoreCase("PRINT")||command.equalsIgnoreCase("CLEAR")||command.equalsIgnoreCase("QUIT"))){
					System.err.println("Invalid arguments!");
					command = stdIn.nextLine();
				}
				
				if(!command.equalsIgnoreCase("QUIT")){
					
					if(command.equalsIgnoreCase("ADD")){
						ArrayList<Employee> empList = new ArrayList<Employee>();
						String cont = "";
						
						do{
							System.out.println("Please enter employee's first name.");
							String first = stdIn.nextLine();
							System.out.println("Please enter employee's last name.");
							String last = stdIn.nextLine();
							System.out.println("Please enter employee's department.");
							String dept = stdIn.nextLine();
							System.out.println("Please enter employee's phone number.");
							String phone = stdIn.nextLine();
							
							empList.add(new Employee(first, last, dept, phone));
							
							System.out.println("\nType 'STOP' to stop entering employees, or enter anything else to continue.");
							cont = stdIn.nextLine();
							
							
						}while(!cont.equalsIgnoreCase("STOP"));
						
						// now create a POST request
						conn.setRequestMethod("POST");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
						
						// build a string that contains JSON from console
						content = "ADD " + getJSON(empList); 
						
						// write out string to output buffer for message
						out.writeBytes(content);
						out.flush(); //cleans up the buffer
						out.close(); //sends it to the server
		
						System.out.println("Done sent to server");

						
					}
					
					else if(command.equalsIgnoreCase("PRINT")){
						
						// now create a DISPLAY request
						conn.setRequestMethod("DISPLAY");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
						
						// build a string that contains JSON from console
						content = "PRINT";
						
						// write out string to output buffer for message
						out.writeBytes(content);
						out.flush(); //cleans up the buffer
						out.close(); //sends it to the server
		
						System.out.println("Done sent to server");
					}
					
					else if(command.equalsIgnoreCase("CLEAR")){
						content = "CLEAR";
					}
					
				}
				
				else{ //Quit function
					System.err.println("Acquiring Thai Candy...");
					break;
				}
				
				
				
//				// build a string that contains JSON from console
//				//String content = "";
//				//content = command.toUpperCase() + getJSON(); 
//
//				// write out string to output buffer for message
//				out.writeBytes(content);
//				out.flush(); //cleans up the buffer
//				out.close(); //sends it to the server
//
//				System.out.println("Done sent to server");

				InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

				// string to hold the result of reading in the response
				StringBuilder sb = new StringBuilder();

				// read the characters from the request byte by byte and build up
				// the Response
				int nextChar;
				while ((nextChar = inputStr.read()) > -1) {
					sb = sb.append((char) nextChar);
				}
				System.out.println("Return String: " + sb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getJSON(ArrayList<Employee> empList) {
		Gson g = new Gson();
		String json = g.toJson(empList);
		return json;
	}

}