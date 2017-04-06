package Client.src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Display extends JPanel {
	private JComboBox comboBox;
	private String chosenTitle;
	
	private JTextField[] fields;
	
	private String chosenGender;
	private JRadioButton Male;
	private JRadioButton Female;
	private JRadioButton Other;
	private ButtonGroup group;

	public static void main(String[] args) {
		String[] labels = { "First Name:", "Last Name:", "Department:", "IP Address:", "Phone Number", "Title:", "Gender:" };
		String[] comboButton = { "Mr.", "Mrs.", "Ms.", "Dr." };
		String[] radioButton = { "Male", "Female" };
		int[] widths = { 15, 15, 15, 15, 15 };
		String[] descs = { "First Name", "Last Name", "Department", "IP Address", "Phone Number" };

		final Display form = new Display(labels, comboButton, radioButton, widths, descs);

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
		
		JButton Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Printing It
				System.out.print(form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2)
				+ ":" + form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio());
				
				//Storing It
				String response = form.getText(0) + ":" + form.getText(1) + ":" + form.getText(2)
				+ ":" + form.getText(3) + ":" + form.getText(4) + ":" + form.getCombo() + ":" + form.getRadio();
				
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
	}

	// Create a form with the specified labels, tooltips, and sizes.
	public Display(String[] labels, String[] comboButton, String[] radioButton, int[] widths, String[] textField) {

		//Setup
		super(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
		add(labelPanel, BorderLayout.WEST);
		add(fieldPanel, BorderLayout.CENTER);
		fields = new JTextField[labels.length - 2];

		//Add all the text fields
		for (int i = 0; i < 5; i++) {
			fields[i] = new JTextField();
			if (i < textField.length)
				fields[i].setToolTipText(textField[i]);
			if (i < widths.length)
				fields[i].setColumns(widths[i]);

			JLabel lab = new JLabel(labels[i], JLabel.RIGHT);
			lab.setLabelFor(fields[i]);

			labelPanel.add(lab);
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(fields[i]);
			fieldPanel.add(p);
		}
		
		//Setup ComboBox
		JLabel lab = new JLabel(labels[5], JLabel.RIGHT);
		labelPanel.add(lab);
		comboBox = new JComboBox(comboButton);
		comboBox.setSelectedIndex(0);
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//Add Combo Box
		p.add(comboBox);
		fieldPanel.add(p);
		
		//Setup Radio
		lab = new JLabel(labels[6], JLabel.RIGHT);
		labelPanel.add(lab);
		Male = new JRadioButton("Male");
		Female = new JRadioButton("Female");
		Other = new JRadioButton("Other");
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//Add all radio buttons together
		group = new ButtonGroup();
		group.add(Male);
		group.add(Female);
		group.add(Other);
		p.add(Male);
		p.add(Female);
		p.add(Other);
		fieldPanel.add(p);
	}
	
	//return the value of the combo button as a String
	public String getCombo(){
		return (String) comboBox.getSelectedItem().toString();
	}
	
	//return the value of the radio button group as a String
	public String getRadio(){
		if(Male.isSelected()){ return "Male"; }
		else if(Female.isSelected()){ return "Female"; }
		else return "Other";
	}
	
	//return the value of all the text fields as a String
	public String getText(int i) {
		return (fields[i].getText());
	}
}