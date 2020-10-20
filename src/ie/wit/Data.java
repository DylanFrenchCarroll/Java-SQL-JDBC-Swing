package ie.wit;

public class Data {

	private String firstName;
	private String lastName;
	private int ssn;
	private int salary;
	private char gender;

	public String getLastName() {
		return lastName;
	}

	public Data(String firstName, String lastName, int ssn, int salary, char gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.salary = salary;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Data [firstName=" + firstName + ", lastName=" + lastName + ", ssn=" + ssn + ", salary=" + salary
				+ ", gender=" + gender + "]";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
