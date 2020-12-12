package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import utility.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SystemAdminController implements Initializable {
	@FXML
	private Button addClubBtn;
	@FXML
	private Button addMemberBtn;
	@FXML
	private Button changeRoleBtn;
	@FXML
	private Button computeMembersBtn;
	@FXML
	private Button computeProjectsBtn;
	@FXML
	private Button logoutbtn;
	@FXML
	private TextField txtClubID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtDesc;
	@FXML
	private ComboBox<Integer> addClubDepID;
	@FXML
	private ComboBox<Integer> addClubStatusID;
	@FXML
	private ComboBox<Integer> addMemberClubID;
	@FXML
	private ComboBox<Integer> addMemberStudentID;
	@FXML
	private ComboBox<Integer> addMemberStatusID;
	@FXML
	private ComboBox<Integer> changeRoleClubID;
	@FXML
	private ComboBox<Integer> changeRoleStdID;
	@FXML
	private ComboBox<String> role;
	@FXML
	private ComboBox<Integer> computeMembers;
	@FXML
	private ComboBox<Integer> computeProjects;
	@FXML
	private DatePicker changeRoleFromDate;
	@FXML
	private DatePicker changeRoleToDate;
	@FXML
	private DatePicker addMemberFromDate;
	@FXML
	private DatePicker addMemberToDate;

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	final String USER_ID = LoginController.getUserID();

	public SystemAdminController() {
		conn = CreateDbConnection.createConnection();
	}

	public void logout(ActionEvent event) {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to log out ?");
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				// go to login page
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/Login.fxml")));
				stage.setScene(scene);
				stage.show();
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private java.sql.Date getCurrentDateAsSQL() {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public void addClub(ActionEvent event) {
		try {
			int clubID = Integer.parseInt(txtClubID.getText());
			String name = txtName.getText();
			name = "'" + name + "'";
			String address = txtAddress.getText();
			address = "'" + address + "'";
			String phone = txtPhone.getText();
			String desc = txtDesc.getText();
			desc = "'" + desc + "'";
			Integer depID = addClubDepID.getValue();
			Integer statusID = addClubStatusID.getValue();

			stmt = conn.prepareStatement("INSERT INTO Club(ID, Name, Address, Phone, des, DepartmentID, StatusID)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ? )");
			stmt.setInt(1, clubID);
			stmt.setString(2, name);
			stmt.setString(3, address);
			stmt.setString(4, phone);
			stmt.setString(5, desc);
			stmt.setInt(6, depID);
			stmt.setInt(7, statusID);

			stmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Club Added Successfully");
			alert.showAndWait();

			filladdClubDepID();

			filladdMemberClubID();

			fillchangeRoleClubID();

			fillcomputeMembers();

			fillcomputeProjects();

		} catch (SQLException e) {
			ShowDialog.showErrorDialogue("Error", "club creation error",
					"make sure you entered valid club information");
			e.printStackTrace();
		} catch (Exception e) {
			ShowDialog.showErrorDialogue("Error", "info required", "please enter all required infromation");
			e.printStackTrace();
		}
	}

	public void addMember(ActionEvent event) {
		try {
			Integer clubID = addMemberClubID.getValue();
			Integer StudentID = addMemberStudentID.getValue();
			Integer StatusID = addMemberStatusID.getValue();
			java.sql.Date dateFrom;
			try {
				dateFrom = Date.valueOf(addMemberFromDate.getValue());
			} catch (Exception e) {
				dateFrom = getCurrentDateAsSQL();
			}
			java.sql.Date dateTo;
			try {
				dateTo = Date.valueOf(addMemberToDate.getValue());
			} catch (Exception e) {
				dateTo = getCurrentDateAsSQL();
			}

			stmt = conn.prepareStatement("INSERT INTO Clubmember VALUES(?, ?, ?, ?, ?);");
			stmt.setInt(1, clubID);
			stmt.setInt(2, StudentID);
			stmt.setDate(3, dateFrom);
			stmt.setDate(4, dateTo);
			stmt.setInt(5, StatusID);
			stmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Member Added Successfully");
			alert.showAndWait();
			String password = StudentID.toString();
			password = BCrypt.hashpw(password, BCrypt.gensalt());
			stmt = conn.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?);");
			stmt.setInt(1, StudentID);
			stmt.setInt(2, 3); // usertype is clubMember.
			stmt.setString(3, password);
			stmt.setInt(4, 19);
			alert.setHeaderText("user created Successfully with password = studentID");
			alert.showAndWait();
		} catch (SQLException e) {
			ShowDialog.showErrorDialogue("Error", "member not added", "make sure you entered valid member information");
			e.printStackTrace();
		} catch (Exception e) {
			ShowDialog.showErrorDialogue("Error", "info required", "please enter all required infromation");
			e.printStackTrace();
		}
	}

	public void ChangeMemberRole(ActionEvent event) {
		try {
			Integer clubID = changeRoleClubID.getValue();
			Integer studentID = changeRoleStdID.getValue();
			String newRole = role.getValue();
			java.sql.Date dateFrom;
			try {
				dateFrom = Date.valueOf(addMemberFromDate.getValue());
			} catch (Exception e) {
				dateFrom = getCurrentDateAsSQL();
			}
			java.sql.Date dateTo;
			try {
				dateTo = Date.valueOf(addMemberToDate.getValue());
			} catch (Exception e) {
				dateTo = getCurrentDateAsSQL();
			}

			stmt = conn.prepareStatement("INSERT INTO Clubadmin VALUES(?, ?, ?, ?, ?);");
			stmt.setInt(1, clubID);
			stmt.setInt(2, studentID);
			stmt.setDate(3, dateFrom);
			stmt.setDate(4, dateTo);
			stmt.setString(5, newRole);
			stmt.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Member Role Changed Successfully");
			alert.showAndWait();
			stmt = conn.prepareStatement("UPDATE user SET userTypeID = ? WHERE StudentID = ?");
			stmt.setInt(1, 2);
			stmt.setInt(2, studentID);
			stmt.executeUpdate();
			alert.setHeaderText("user type changed to ClubAdmin Successfully");
			alert.showAndWait();

		}catch (SQLException e) {
			ShowDialog.showErrorDialogue("Error", "failed to change role", "make sure you entered valid information");
			e.printStackTrace();
		} catch (Exception e) {
			ShowDialog.showErrorDialogue("Error", "info required", "please enter all required infromation");
			e.printStackTrace();
		}
	}

	public void getClubMembers(ActionEvent event) {
		changeRoleStdID.getItems().clear();
		Integer clubID = changeRoleClubID.getValue();
		try {
			PreparedStatement st = conn
					.prepareStatement("SELECT STUDENTID FROM CLUBMEMBER WHERE clubId =  ? " + "and statusID = 12");
			st.setInt(1, clubID);
			rs = st.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				changeRoleStdID.getItems().add(rs.getInt(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void countClubMembers(ActionEvent event) {
		Integer clubID = computeMembers.getValue();
		try {
			stmt = conn.prepareStatement("SELECT DISTINCT COUNT(studentid) FROM clubmember where clubid = ?");
			stmt.setInt(1, clubID);
			rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Number of club members is: " + count);
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			ShowDialog.showErrorDialogue("Error", "No Club ID", "please Choose a club ID to Count its members");
			e.printStackTrace();
		}

	}

	public void countProjectMembers(ActionEvent event) {
		Integer clubID = computeProjects.getValue();
		try {
			stmt = conn.prepareStatement("SELECT DISTINCT COUNT(*) FROM project where clubid = ?");
			stmt.setInt(1, clubID);
			rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Number of club members is: " + count);
			alert.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			ShowDialog.showErrorDialogue("Error", "No Club ID", "please Choose a club ID to Count its projects");
			e.printStackTrace();
		}

	}

	private void filladdClubDepID(){
		addClubDepID.getItems().clear();
		try {
			stmt = conn.prepareStatement("SELECT ID FROM department");
			rs = stmt.executeQuery();
			while (rs.next()) {
				addClubDepID.getItems().add(rs.getInt(1));
			}
		} catch (SQLException e) {
			ShowDialog.showErrorDialogue("fatalError", "Uknown Error", "something bad happend");
			e.printStackTrace();
		}

	}
	private void filladdMemberClubID(){
		addMemberClubID.getItems().clear();
		try {
			stmt = conn.prepareStatement("SELECT ID FROM club");
			rs = stmt.executeQuery();
			while (rs.next()) {
				addMemberClubID.getItems().add(rs.getInt(1));
			}
		} catch (SQLException e) {
			ShowDialog.showErrorDialogue("fatalError", "Uknown Error", "something bad happend");
			e.printStackTrace();
		}
	}
	private void fillchangeRoleClubID(){
		changeRoleClubID.getItems().clear();
		try{
			rs = stmt.executeQuery("SELECT ID FROM club");
			while (rs.next()) {

				changeRoleClubID.getItems().add(rs.getInt(1));
			}
		}catch (SQLException e) {
			ShowDialog.showErrorDialogue("fatalError", "Uknown Error", "something bad happend");
			e.printStackTrace();
		}
	}
	private void fillcomputeMembers(){
		computeMembers.getItems().clear();
		try{
			stmt = conn.prepareStatement("SELECT ID FROM club");
			rs = stmt.executeQuery();
			while (rs.next()) {
				computeMembers.getItems().add(rs.getInt(1));
			}
		}catch (SQLException e) {
			ShowDialog.showErrorDialogue("fatalError", "Uknown Error", "something bad happend");
			e.printStackTrace();
		}
	}
	private void fillcomputeProjects(){
		computeProjects.getItems().clear();
		try{
			stmt = conn.prepareStatement("SELECT ID FROM club");
			rs = stmt.executeQuery();
			while (rs.next()) {
				computeProjects.getItems().add(rs.getInt(1));
			}
		}catch (SQLException e) {
			ShowDialog.showErrorDialogue("fatalError", "Uknown Error", "something bad happend");
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			filladdClubDepID();

			filladdMemberClubID();

			fillchangeRoleClubID();

			fillcomputeMembers();

			fillcomputeProjects();

			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 4");
			while (rs.next()) {
				addClubStatusID.getItems().add(rs.getInt(1));
			}


			stmt = conn.prepareStatement("SELECT ID FROM student");
			rs = stmt.executeQuery();
			while (rs.next()) {
				addMemberStudentID.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 5");
			while (rs.next()) {
				addMemberStatusID.getItems().add(rs.getInt(1));
			}

			role.getItems().addAll("President", "Secretary");


		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
