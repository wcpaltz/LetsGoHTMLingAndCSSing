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

		String[] labels = { "First Name:", "Last Name:", "Department:", "IP Address:", "Phone Number", "Title:",
				"Gender:" };
		String[] comboButton = { "Mr.", "Mrs.", "Ms.", "Dr." };
		String[] radioButton = { "Male", "Female" };
		int[] widths = { 15, 15, 15, 15, 15 };
		String[] descs = { "First Name", "Last Name", "Department", "IP Address", "Phone Number" };

		// Create display
		final Display form = new Display(labels, comboButton, radioButton, widths, descs);

		// try to start the client to send stuff to the server
		try {
			System.out.println("in the client");


			JButton Add = new JButton("Add"); // TODO: Add Begins Here
			Add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// Printing It
					System.out.print(form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2) + ":"
							+ form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio());

					// Storing It
					String response = form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2) + ":"
							+ form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio();

					// Begin splitting String
					ArrayList<Employee> empList = new ArrayList<Employee>();

					// separating employee characteristics
					String[] emp = response.split(":");
					int n = 0;
					String first = "", last = "", dept = "", phone = "", ip = "", title = "", gender = "";
					for (String c : emp) {
						if (n == 0) {
							first = c;
						}
						if (n == 1) {
							last = c;
						}
						if (n == 2) {
							dept = c;
						}
						if (n == 3) {
							ip = c;
						}
						if (n == 4) {
							phone = c;
						}
						if (n == 5) {
							title = c;
						}
						if (n == 6) {
							gender = c;
						}
						n++;

					}

					// add a single employee to the employee list that we are
					// going to send to the server some day
					empList.add(new Employee(title, first, last, dept, phone, gender));

					String content = "";

					// TODO: Error-causing POST Request
					try {

						// Client will connect to this location
						URL site = new URL("http://localhost:8000/sendresults");
						
						//connect to the socket, server already has socket open
						final HttpURLConnection conn = (HttpURLConnection) site.openConnection();
						
						//now create a POST request
						conn.setRequestMethod("POST");
						
						//not sure what this does
						conn.setDoOutput(true);
						conn.setDoInput(true);
						
						//creating output "channel"/data stream - client to server, output from client
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());

						// build a string that contains JSON from console, will
						// list of employees
						content = "ADD:" + getJSON(empList);

						//Packages it all up and sends to the server
						// write out string to output buffer for message
						out.writeBytes(content);
						out.flush(); // cleans up the buffer
						out.close(); // sends it to the server
						
						//reads the response from the server after post, complete connection with input stream
						InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

						//creating the response from server
						StringBuilder sb = new StringBuilder();

						// read the characters from the request byte by byte and build up
						// the Response
						int nextChar;
						while ((nextChar = inputStr.read()) > -1) {
							sb = sb.append((char) nextChar);
						}
						System.out.println("Return String: " + sb);

					} catch (Exception f) {
						f.printStackTrace();
					}
					System.out.println("Done sent to server");

				}

				// //TODO
				// JButton Print = new JButton("Print");
				// Print.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// //SEND command "Print" to client
				// }
				// });
				//
				// //TODO
				// JButton Clear = new JButton("Clear");
				// Clear.addActionListener(new ActionListener() {
				// public void actionPerformed(ActionEvent e) {
				// //SEND command "Clear" to client
				// System.err.println("Clearing Database...");
				// }
				// });
				//
			});

			JFrame f = new JFrame("Text Form Example");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(form, BorderLayout.NORTH);
			JPanel p = new JPanel();
			// p.add(Print);
			p.add(Add);
			// p.add(Clear);
			f.getContentPane().add(p, BorderLayout.SOUTH);
			f.pack();
			f.setVisible(true);

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
