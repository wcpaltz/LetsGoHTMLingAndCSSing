package Client.src;
//Racer without name
public class Racer implements Comparable<Object>{

	private String bib;
	private String time;
	
	public Racer(String bib, String time) {
		this.bib = bib;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return bib + ":" + time;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Racer) {
			Racer other = (Racer) o;
			return time.compareTo(other.time);
		}
		return 0;
	}
	
//	public String HTMLRacer(){
//		//Firstname, Lastname, Department, Phonenumber
//		return "<td>" + this.title + "</td><td>"
//		+ this.firstName + "</td><td>" 
//		+ this.lastName + "</td><td>"
//		+ this.department + "</td><td>"
//		+ this.phoneNumber + "</td><td>"
//		+ this.gender + "</td>";
//	}

}
