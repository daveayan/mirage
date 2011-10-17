package sample.domain;
import java.util.ArrayList;
import java.util.List;


public class Results {
	private List<Person> list_of_persons = new ArrayList<Person>() ;
	
	public static Results getFullyLoadedObject() {
		return Results.newInstance().addPerson(Person.getFullyLoadedInstance()).addPerson(Person.getFullyLoadedInstance());
	}
	
	public Results addPerson(Person p) {
		this.list_of_persons.add(p);
		return this;
	}
	
	public static Results newInstance() {
		return new Results();
	}
	
	private Results() {}
}
