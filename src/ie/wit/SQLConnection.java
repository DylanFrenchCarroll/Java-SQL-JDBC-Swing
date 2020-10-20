package ie.wit;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLConnection {
	private static Connection con;

	public SQLConnection() throws SQLException {
		System.out.println("Attempting to connect....");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost/test_create_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=BST",
				"root", "");
	}

	public ArrayList<Data> read() throws SQLException {

		ArrayList<Data> dataList = new ArrayList<Data>();

		System.out.println("Getting all data from table..");
		Statement stmt = con.createStatement();
		String sql = "SELECT * FROM data";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			dataList.add(new Data(rs.getString("name"), rs.getString("lastname"), rs.getInt("ssn"), rs.getInt("salary"),
					rs.getString("gender").charAt(0)));
		}
		stmt.close();
		rs.close();
		return dataList;
	}

	public void create(Data data) throws SQLException {

		Statement stmt = con.createStatement();

		String sqlName = data.getFirstName();
		String sqlLastName = data.getLastName();
		int sqlSSN = data.getSsn();
		int sqlSalary = data.getSalary();
		char sqlGender = data.getGender();

		String sql = "INSERT INTO `data` VALUES ('" + sqlName + "', '" + sqlLastName + "', '" + sqlSSN + "', '"
				+ sqlSalary + "', '" + sqlGender + "')";

		stmt.executeUpdate(sql);
		stmt.close();

	}

	public void delete(int tableSsn) throws SQLException {

		Statement stmt = con.createStatement();
		String sql = "DELETE FROM `data` WHERE ssn='" + tableSsn + "'";
		stmt.executeUpdate(sql);
		stmt.close();

	}
	public void update(Data data, int tableSsn) throws SQLException {

		Statement stmt = con.createStatement();

		String sqlName = data.getFirstName();
		String sqlLastName = data.getLastName();
		int sqlSSN = data.getSsn();
		int sqlSalary = data.getSalary();
		char sqlGender = data.getGender();
		
		



		String sql = "UPDATE data \n" + "SET name='" + sqlName + "', \n lastname='" + sqlLastName + "',\n ssn='"
				+ sqlSSN + "', \n salary='" + sqlSalary + "', \n gender='" + sqlGender + "' \n" + "WHERE ssn='"
				+ tableSsn + "'";

	

	
			stmt.executeUpdate(sql);
			stmt.close();

	}


}
