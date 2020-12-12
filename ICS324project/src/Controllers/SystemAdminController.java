package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

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
import utility.CreateDbConnection;
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
	Statement stmt = null;
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

	public void addClub(ActionEvent event) {
		String clubID = txtClubID.getText();
		System.out.println(clubID);
		String name = txtName.getText();
		System.out.println(name);
		name = "'" + name + "'";
		String address = txtAddress.getText();
		address = "'" + address + "'";
		String phone = txtPhone.getText();
		String desc = txtDesc.getText();
		desc = "'" + desc + "'";
		Integer depID = addClubDepID.getValue();
		System.out.println(depID);
		Integer statusID = addClubStatusID.getValue();
		if (name.isEmpty() || address.isEmpty()) {
			// statusLbl.setTextFill(Color.RED);
			// statusLbl.setText("Enter LogIn Information!");
			System.out.println("write something");
		} else {
			try {
				stmt = conn.createStatement();
				int tst = stmt
						.executeUpdate("INSERT INTO Club(ID, Name, Address, Phone, des, DepartmentID, StatusID) VALUES("
								+ clubID + "," + name + "," + address + "," + phone + "," + desc + "," + depID + ","
								+ statusID + ");");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Club Added Successfully");
				alert.showAndWait();
			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addMember(ActionEvent event) {
		Integer clubID = addMemberClubID.getValue();
		Integer StudentID = addMemberStudentID.getValue();
		Integer StatusID = addMemberStatusID.getValue();
		// LocalDate tmpDate =addMemberFromDate.getValue();
		String fromDate = addMemberFromDate.getValue().toString();
		fromDate = "'" + fromDate + "'";
		// System.out.println(fromDate);
		String toDate = addMemberToDate.getValue().toString();
		toDate = "'" + toDate + "'";

		if (clubID.equals(null) || StudentID.equals(null)) {
			// statusLbl.setTextFill(Color.RED);
			// statusLbl.setText("Enter LogIn Information!");
			System.out.println("write something");
		} else {
			try {
				stmt = conn.createStatement();
				int tst = stmt.executeUpdate(
						"INSERT INTO Clubmember VALUES(" + clubID + "," + StudentID + "," + "STR_TO_DATE(" + fromDate
								+ ", '%Y-%m-%d'), STR_TO_DATE(" + toDate + ", '%Y-%m-%d')," + StatusID + ") ;");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Member Added Successfully");
				alert.showAndWait();
			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void ChangeMemberRole(ActionEvent event) {
		Integer clubID = changeRoleClubID.getValue();
		Integer studentID = changeRoleStdID.getValue();
		String newRole = role.getValue();
		newRole = "'" + newRole + "'";
		String fromDate = changeRoleFromDate.getValue().toString();
		fromDate = "'" + fromDate + "'";
		String toDate = changeRoleToDate.getValue().toString();
		toDate = "'" + toDate + "'";
		try {
			stmt = conn.createStatement();
			// tst = stmt.executeUpdate("Delete from clubmember WHERE
			// ID="+studentID);
			stmt.executeUpdate("INSERT INTO Clubadmin VALUES(" + clubID + "," + studentID + "," + "STR_TO_DATE("
					+ fromDate + ", '%Y-%m-%d'), STR_TO_DATE(" + toDate + ", '%Y-%m-%d')," + newRole + ") ;");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Member Role Changed Successfully");
			alert.showAndWait();
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getClubMembers(ActionEvent event) {
		changeRoleStdID.getItems().clear();
		Integer clubID = changeRoleClubID.getValue();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT STUDENTID FROM clubmember WHERE clubId = " + clubID);
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
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT COUNT(studentid) FROM clubmember where clubid =" + clubID);
			rs.next();
			int count = rs.getInt(1);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Number of club members is: "+ count);
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void countProjectMembers(ActionEvent event) {
		Integer clubID = computeProjects.getValue();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT COUNT(*) FROM project where clubid =" + clubID);
			rs.next();
			int count = rs.getInt(1);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Number of club members is: "+ count);
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM department");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				addClubDepID.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 4");
			while (rs.next()) {
				addClubStatusID.getItems().add(rs.getInt(1));
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM club");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				addMemberClubID.getItems().add(rs.getInt(1));
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM student");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				addMemberStudentID.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 1");
			while (rs.next()) {
				addMemberStatusID.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT ID FROM club");
			while (rs.next()) {

				changeRoleClubID.getItems().add(rs.getInt(1));
			}
			/*
			 * rs = stmt.executeQuery("SELECT ID FROM student");
			 * while(rs.next()){
			 *
			 * changeRoleStdID.getItems().add(rs.getInt(1)); }
			 */
			role.getItems().addAll("President", "Secretary");

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM club");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				computeMembers.getItems().add(rs.getInt(1));
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM club");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				computeProjects.getItems().add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
