package Client.src;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Client {

	public static void main(String[] args) {

		String[] labels = { "First Name:", "Last Name:", "Department:", "IP Address:", "Phone Number", "Title:", "Gender:" };
		String[] comboButton = { "Mr.", "Mrs.", "Ms.", "Dr." };
		String[] radioButton = { "Male", "Female" };
		int[] widths = { 15, 15, 15, 15, 15 };
		String[] descs = { "First Name", "Last Name", "Department", "IP Address", "Phone Number" };


		final Display form = new Display(labels, comboButton, radioButton, widths, descs);


		try {
			System.out.println("in the client");

			// Client will connect to this location
			URL site = new URL("http://localhost:8000/sendresults");
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();

			//TODO
			JButton Print = new JButton("Print");
			Print.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//SEND command "Print" to client


				}
			});

			//TODO
			JButton Clear = new JButton("Clear");
			Clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//SEND command "Clear" to client
					System.err.println("Clearing Database...");
				}
			});

			JButton Add = new JButton("Add"); //TODO: Add Begins Here
			Add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					//Printing It
					System.out.print(form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2)
					+ ":" + form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio());

					//Storing It
					String response = form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2)
					+ ":" + form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio();

					//Begin splitting String
					ArrayList<Employee> empList = new ArrayList<Employee>();

					String[] emp = response.split(":");
					int n = 0;
					String first = "", last = "", dept = "", phone= "", ip = "", title = "", gender = "";
					for(String c : emp){
						if(n==0){first = c;}
						if(n==1){last = c;}
						if(n==2){dept = c;}
						if(n==3){ip = c;}
						if(n==4){phone = c;}
						if(n==5){title = c;}
						if(n==6){gender = c;}
						n++;

					}
					empList.add(new Employee(title, first, last, dept, phone, gender));

					String content = "";

					//TODO: Error-causing POST Request
					try{
						// now create a POST request
						conn.setRequestMethod("POST");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());


						// build a string that contains JSON from console
						content = "ADD:" + getJSON(empList); 

						// write out string to output buffer for message
						out.writeBytes(content);
						out.flush(); //cleans up the buffer
						out.close(); //sends it to the server
					}catch(Exception f){
						f.printStackTrace();
					}
					System.out.println("Done sent to server");

				}

			});


			JFrame f = new JFrame("Text Form Example");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(form, BorderLayout.NORTH);
			JPanel p = new JPanel();
			p.add(Print);
			p.add(Add);
			p.add(Clear);
			f.getContentPane().add(p, BorderLayout.SOUTH);
			f.pack();
			f.setVisible(true);	








			String content = "";

			//				// now create a POST request
			//				conn.setRequestMethod("POST");
			//				conn.setDoOutput(true);
			//				conn.setDoInput(true);
			//				DataOutputStream out = new DataOutputStream(conn.getOutputStream());


//			// now create a POST request
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//
//			// build a string that contains JSON from console
//			content = "PRINT";
//
//			// write out string to output buffer for message
//			out.writeBytes(content);
//			out.flush(); //cleans up the buffer
//			out.close(); //sends it to the server
//
//			System.out.println("Done sent to server");




			//					else if(command.equalsIgnoreCase("PRINT")){
			//
			//						// now create a POST request
			//						conn.setRequestMethod("POST");
			//						conn.setDoOutput(true);
			//						conn.setDoInput(true);
			//						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			//
			//						// build a string that contains JSON from console
			//						content = "PRINT";
			//
			//						// write out string to output buffer for message
			//						out.writeBytes(content);
			//						out.flush(); //cleans up the buffer
			//						out.close(); //sends it to the server
			//
			//						System.out.println("Done sent to server");
			//					}

			//					else if(){
			//
			//						// now create a POST request
			//						conn.setRequestMethod("POST");
			//						conn.setDoOutput(true);
			//						conn.setDoInput(true);
			//						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			//
			//						// build a string that contains JSON from console
			//						content = "CLEAR";
			//
			//						// write out string to output buffer for message
			//						out.writeBytes(content);
			//						out.flush(); //cleans up the buffer
			//						out.close(); //sends it to the server
			//
			//						System.out.println("Done sent to server");
			//
			//					}


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
