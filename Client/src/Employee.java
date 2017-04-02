package Client.src;

public class Employee implements Comparable<Object>{

	private String title;
	private String firstName;
	private String lastName;
	private String department;
	private String phoneNumber;
	private String gender;
	
	public Employee(String title, String firstName, String lastName, String department, String phoneNum, String gender) {
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.phoneNumber = phoneNum;
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " \tin " + department + " \thas phone number of " + phoneNumber;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Employee) {
			Employee other = (Employee) o;
			return lastName.compareTo(other.lastName);
		}
		return 0;
	}
	
	public String HTMLEmployee(){
		//Firstname, Lastname, Department, Phonenumber
		return "<td>" + this.title + "</td><td>"
		+ this.firstName + "</td><td>" 
		+ this.lastName + "</td><td>"
		+ this.department + "</td><td>"
		+ this.phoneNumber + "</td><td>"
		+ this.gender + "</td>";
	}

}
