package distSystemsLab;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

public class main {

	public static ResultSet rs;
	public static String name = "", email = "";
	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	static JTextField nameField = new JTextField(20);
	static JTextField lastNameField = new JTextField(20);
	static JTextField ssnField = new JTextField(20);
	static JTextField salaryField = new JTextField(20);
	static JTextField genderField = new JTextField(20);
	static JButton createButton = new JButton("Create");
	static JButton deleteButton = new JButton("Delete");
	static JButton updateButton = new JButton("Update");
	static int row = 0;
	static Connection con;
	static JTable table;
	static DefaultTableModel tableModel = new DefaultTableModel();
	static ArrayList<String[]> dataList = new ArrayList<String[]>();

	public static void main(String[] args) {

		try {
			System.out.println("Attempting to connect....");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/test_create_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=BST",
					"root", "");

			read();
			layout();
		} catch (Exception e) {
			e.printStackTrace();
		}

		createButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Statement stmt = null;
				try {
					stmt = con.createStatement();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String sqlName = nameField.getText();
				String sqlLastName = lastNameField.getText();
				String sqlSSN = ssnField.getText();
				String sqlSalary = salaryField.getText();
				String sqlGender = genderField.getText();
				String sql = "INSERT INTO `data` VALUES ('" + sqlName + "', '" + sqlLastName + "', '" + sqlSSN + "', '"
						+ sqlSalary + "', '" + sqlGender + "')";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				try {
					read();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Statement stmt = null;
				try {
					stmt = con.createStatement();
				} catch (SQLException e1) {

					e1.printStackTrace();

				}

				row = table.getSelectedRow();
				String sqlSSN = (String) table.getValueAt(row, 2);
				String sql = "DELETE FROM `data` WHERE ssn='" + sqlSSN + "'";

				System.out.println(sql);
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				try {
					refresh();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		updateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Statement stmt = null;
				try {
					stmt = con.createStatement();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String sqlName = nameField.getText();
				String sqlLastName = lastNameField.getText();
				String sqlSSN = ssnField.getText();
				String sqlSalary = salaryField.getText();
				String sqlGender = genderField.getText();

				int row = table.getSelectedRow();

				String sqlRowIdentifier = (String) table.getValueAt(row, 2);

				String sql = "UPDATE data \n" + "SET name='" + sqlName + "', \n lastname='" + sqlLastName + "',\n ssn='"
						+ sqlSSN + "', \n salary='" + sqlSalary + "', \n gender='" + sqlGender + "' \n" + "WHERE ssn='"
						+ sqlRowIdentifier + "'";

				System.out.println(sql);

				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				try {
					read();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public static void refresh() throws SQLException {
		System.out.println("Getting all data from table..");
		Statement stmt = con.createStatement();

		String sql = "SELECT * FROM data";
		ResultSet rs = stmt.executeQuery(sql);
		
		try {
			tableModel.setRowCount(0);
			int i = 0;
			for (String[] arrayTwo : dataList) {
				tableModel.addRow(arrayTwo);
				i++;
			}

			System.out.println("Table Created..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void read() throws SQLException {

		System.out.println("Getting all data from table..");
		Statement stmt = con.createStatement();

		String sql = "SELECT * FROM data";
		ResultSet rs = stmt.executeQuery(sql);

		 
		while (rs.next()) {
			String[] stringArray = new String[5];
			stringArray[0] = rs.getString("name");
			stringArray[1] = rs.getString("lastname");
			stringArray[2] = rs.getString("ssn");
			stringArray[3] = rs.getString("salary");
			stringArray[4] = rs.getString("gender");
			dataList.add(stringArray);
		}

		// got data

		tableModel.setColumnCount(0);
		String columns[] = { "name", "lastname", "ssn", "salary", "gender" };
		for (String columnName : columns) {
			tableModel.addColumn(columnName);
		}

		try {

			tableModel.setRowCount(0);
			int i = 0;
			for (String[] arrayTwo : dataList) {
				tableModel.addRow(arrayTwo);
				i++;
			}

			System.out.println("Table Created..");

		} catch (Exception e) {
			e.printStackTrace();
		}

		table = new JTable(tableModel);

		table.setBounds(210, 20, 200, 300);
		tableModel.fireTableDataChanged();

	}

	public static void layout() {

		panel.setLayout(null);
		frame.setPreferredSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel nameLabel = new JLabel("First Name: ");
		JLabel lastNameLabel = new JLabel("Last Name: ");
		JLabel ssnNumLabel = new JLabel("SSN Number: ");
		JLabel salaryLabel = new JLabel("Salary: ");
		JLabel genderLabel = new JLabel("Gender: ");

		nameLabel.setBounds(20, 20, 100, 20);
		nameField.setBounds(100, 20, 100, 20);
		panel.add(nameLabel);
		panel.add(nameField);

		lastNameLabel.setBounds(20, 50, 100, 20);
		lastNameField.setBounds(100, 50, 100, 20);
		panel.add(lastNameLabel);
		panel.add(lastNameField);

		ssnNumLabel.setBounds(20, 80, 100, 20);
		ssnField.setBounds(100, 80, 100, 20);
		panel.add(ssnNumLabel);
		panel.add(ssnField);

		salaryLabel.setBounds(20, 110, 100, 20);
		salaryField.setBounds(100, 110, 100, 20);
		panel.add(salaryLabel);
		panel.add(salaryField);

		genderLabel.setBounds(20, 140, 100, 20);
		genderField.setBounds(100, 140, 100, 20);
		panel.add(genderLabel);
		panel.add(genderField);

		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(210, 20, 200, 300);
		panel.add(scrollpane);

		createButton.setBounds(20, 260, 100, 20);
		panel.add(createButton);

		deleteButton.setBounds(20, 290, 100, 20);
		panel.add(deleteButton);

		updateButton.setBounds(20, 320, 100, 20);
		panel.add(updateButton);

		frame.add(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}

}
