package Server.src.com.example;

import java.util.ArrayList;

//Controls the mapping of the bibs to the name
public class NameMap {
	
	ArrayList<String> bibList;
	ArrayList<String> nameList;
	
	public NameMap(){
		bibList = new ArrayList<String>();
		nameList = new ArrayList<String>();
	}

	public void clear() {
		bibList = new ArrayList<String>();
		nameList = new ArrayList<String>();
	}

	public boolean containsKey(String bib) {

		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i).equals(bib))
				return true;
		}
		
		return false;
	}

	public boolean containsValue(String name) {

		for(int i = 0; i < nameList.size(); i++){
			if(nameList.get(i).equals(name))
				return true;
		}
		
		return false;
	}

	public String get(String bib) {
		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i).equals(bib))
				return nameList.get(i);
		}
		
		return null;
	}

	public boolean isEmpty() {
		return (bibList.size() == 0 && nameList.size() == 0);
	}

	public void put(String bib, String name) {
		bibList.add(bib);
		nameList.add(name);
	}

	public boolean remove(String bib) {
		boolean removed = false;
		
		for(int i = 0; i < bibList.size(); i++){
			if(bibList.get(i).equals(bib)){
				nameList.remove(i);
				bibList.remove(i);
				removed = true;
			}
				
		}
		
		return removed;
	}

	public int size() {
		return bibList.size();
	}

	public ArrayList<String> values() {
		return nameList;
	}

}
