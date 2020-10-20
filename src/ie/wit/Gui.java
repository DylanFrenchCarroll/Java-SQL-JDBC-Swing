package ie.wit;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gui {

	private static final Object[] String = null;
	private SQLConnection con;
	public JTextField nameField = createTextField(100, 20, 100, 20);
	public JTextField lastNameField = createTextField(100, 50, 100, 20);
	public JTextField ssnField = createTextField(100, 80, 100, 20);
	public JTextField salaryField = createTextField(100, 110, 100, 20);
	public JTextField genderField = createTextField(100, 140, 100, 20);
	public JLabel nameLabel = createLabel(20, 20, 100, 20, "First Name: ");
	public JLabel lastNameLabel = createLabel(20, 50, 100, 20, "Last Name: ");
	public JLabel ssnLabel = createLabel(20, 80, 100, 20, "SSN: ");
	public JLabel salaryLabel = createLabel(20, 110, 100, 20, "Salary: ");
	public JLabel genderLabel = createLabel(20, 140, 100, 20, "Gender: ");
	static DefaultTableModel tableModel = new DefaultTableModel();
	static JTable table;
	public JButton createButton = createJButton(20, 260, 100, 20, "Create", (ActionEvent e) -> {
		try {
			con.create(getData());
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	});
	
	public JButton deleteButton = createJButton(140, 260, 100, 20, "Delete", (ActionEvent e) -> {
		try {

			int row = table.getSelectedRow();
			if(row <0) {
				return;
			}
			
			con.delete(Integer.valueOf((String)table.getValueAt(row, 2)));
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	});
	
	public JButton updateButton = createJButton(260, 260, 100, 20, "Update", (ActionEvent e) -> {
		try {

			int row = table.getSelectedRow();
			if(row <0) {
				return;
			}
			
			con.update(getData(), Integer.valueOf((String)table.getValueAt(row, 2)));
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	});

	public Gui() throws SQLException {
		con = new SQLConnection();
		JFrame frame = new JFrame();

		frame.add(layout());
		frame.setPreferredSize(new Dimension(800, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	private JPanel layout() throws SQLException {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(nameField);
		panel.add(lastNameField);
		panel.add(ssnField);
		panel.add(salaryField);
		panel.add(genderField);
		panel.add(nameLabel);
		panel.add(lastNameLabel);
		panel.add(ssnLabel);
		panel.add(salaryLabel);
		panel.add(genderLabel);
		panel.add(createButton);
		panel.add(deleteButton);
		panel.add(updateButton);

		tableModel.setColumnCount(0);
		String columns[] = { "name", "lastname", "ssn", "salary", "gender" };
		for (String columnName : columns) {
			tableModel.addColumn(columnName);
		}
		refreshTable();
		table = new JTable(tableModel);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(370, 20, 400, 260);
		panel.add(scrollpane);

		return panel;
	}

	private JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		return textField;
	}

	private JLabel createLabel(int x, int y, int width, int height, String label) {
		JLabel textField = new JLabel(label);
		textField.setBounds(x, y, width, height);
		return textField;
	}

	private JButton createJButton(int x, int y, int width, int height, String label, ActionListener act) {
		JButton button = new JButton(label);
		button.setBounds(x, y, width, height);
		button.addActionListener(act);
		return button;
	}

	private void refreshTable() throws SQLException {

		tableModel.setRowCount(0);
		ArrayList<Data> dataList = con.read();
		for (Data arrayTwo : dataList) {
			ArrayList<String> rowArray = new ArrayList<String>();
			rowArray.add(arrayTwo.getFirstName());
			rowArray.add(arrayTwo.getLastName());
			rowArray.add(Integer.toString(arrayTwo.getSsn()));
			rowArray.add(Integer.toString(arrayTwo.getSalary()));
			rowArray.add(Character.toString(arrayTwo.getGender()));
			tableModel.addRow(rowArray.toArray());
		}
	}

	private Data getData() {
		String sqlName 		= nameField.getText();
		String sqlLastName  = lastNameField.getText();
		int sqlSSN = Integer.valueOf(ssnField.getText());
		int sqlSalary = Integer.valueOf(salaryField.getText());
		char sqlGender = genderField.getText().charAt(0);
		return new Data(sqlName, sqlLastName, sqlSSN, sqlSalary, sqlGender);
	}
	
	

}
