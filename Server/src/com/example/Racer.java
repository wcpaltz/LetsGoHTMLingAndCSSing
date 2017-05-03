package Server.src.com.example;

public class Racer implements Comparable<Object>{

	private String bib;
	private String time;
	private String name = "N/A";
	
	public Racer(String bib, String time) {
		this.bib = bib;
		this.time = time;
		
	}
	
	@Override
	public String toString() {
		return name + ", with bib " + bib + ", has time of " + time;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Racer) {
			Racer other = (Racer) o;
			return time.compareTo(other.time);
		}
		return 0;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getBib(){
		return bib;
	}
	
	public String HTMLRacer(){

		return "<td>" + this.time + "</td><td>"
		+ this.bib + "</td><td>" 
		+ this.name + "</td>";
	}

}
