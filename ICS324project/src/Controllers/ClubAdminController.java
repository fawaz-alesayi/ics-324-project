package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import club.IClubMemberRepository;
import club.IClubRepository;
import club.MySQLClubMemberRepository;
import club.MySQLClubRepository;
import club_applications.IClubApplicationRepository;
import club_applications.MySQLClubApplicationRepository;
import guest.IStudentRepository;
import guest.MySQLStudentRepository;
import guest.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import project.IProjectRepository;
import project.MySQLProjectRepository;
import utility.CreateDbConnection;

public class ClubAdminController implements Initializable {
	@FXML
	Button logoutbtn;
	@FXML
	TextField addProjectId;
	@FXML
	TextField addProjectName;
	@FXML
	ComboBox<Integer> addProjectTypeId;
	@FXML
	ComboBox<Integer> addProjectClubId;
	@FXML
	ComboBox<Integer> addProjectStatusId;
	@FXML
	TextField addProjectDesc;
	@FXML
	private DatePicker addProjectFromDate;
	@FXML
	private DatePicker addProjectToDate;
	@FXML
	private ComboBox<Integer> changeProjectId;
	@FXML
	private ComboBox<Integer> changeProjectStatusId;
	@FXML
	private Button changeProjectStatusBtn;
	@FXML
	private ComboBox<Integer> addMemberId;
	@FXML
	private ComboBox<Integer> addMemberProjectId;
	@FXML
	private Button addMemberBtn;
	@FXML
	private DatePicker addMemberFromDate;
	@FXML
	private DatePicker addMemberToDate;
	@FXML
	private ComboBox<Integer> computeProjectsClubId;

	@FXML
	private Button selectProjectLeader;
	@FXML
	private ComboBox<Integer> selectLeaderComboBox;

	@FXML
	private ComboBox<Integer> clubIdComboBox;
	@FXML
	private ComboBox<Student> applicantListComboBox;
	
	IStudentRepository studentRepo;
	IClubMemberRepository clubMemberRepo;
	IProjectRepository projectRepo;
	IClubRepository clubRepo;
	IClubApplicationRepository clubAppRepo;

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	final String USER_ID = LoginController.getUserID();

	public ClubAdminController() {
		conn = CreateDbConnection.createConnection();
	}

	public void AddProject(ActionEvent event) {
		String projectId = addProjectId.getText();
		String name = addProjectName.getText();
		name = "'" + name + "'";
		Integer projectTypeId = addProjectTypeId.getValue();
		String desc = addProjectDesc.getText();
		desc = "'" + desc + "'";
		Integer clubId = addProjectClubId.getValue();
		Integer statusID = addProjectStatusId.getValue();
		String fromDate = addProjectFromDate.getValue().toString();
		fromDate = "'" + fromDate + "'";
		String toDate = addProjectToDate.getValue().toString();
		toDate = "'" + toDate + "'";
		if (name.isEmpty() || projectId.isEmpty()) {
			// statusLbl.setTextFill(Color.RED);
			// statusLbl.setText("Enter LogIn Information!");
			System.out.println("write something");
		} else {
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO project VALUES(" + projectId + "," + name + "," + projectTypeId + ","
						+ clubId + "," + desc + "," + fromDate + "," + toDate + "," + statusID + ")");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Project Added Successfully");
				alert.showAndWait();
			}

			catch (SQLException e) {
				showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
				e.printStackTrace();
			}
		}
	}

	public void ChangeProjectStatus(ActionEvent event) {
		Integer projectId = changeProjectId.getValue();
		Integer statusId = changeProjectStatusId.getValue();
		try {
			stmt.executeUpdate("UPDATE project SET statusid=" + statusId + " Where id=" + projectId);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Project Status Changed Successfully");
			alert.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addMember(ActionEvent event) {
		Integer memberId = addMemberId.getValue();
		Integer projectId = addMemberProjectId.getValue();
		String fromDate = addMemberFromDate.getValue().toString();
		fromDate = "'" + fromDate + "'";
		String toDate = addMemberToDate.getValue().toString();
		toDate = "'" + toDate + "'";
		String role = "'" + "Member" + "'";
		try {
			stmt.executeUpdate("INSERT INTO workson VALUES(" + memberId + "," + projectId + "," + fromDate
					+ "," + toDate + "," + role + ")");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Member Added to Project Successfully");
			alert.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getNumberOfProjects(ActionEvent event) {
		Integer clubId = computeProjectsClubId.getValue();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT COUNT(*) FROM project where clubid =" + clubId);
			rs.next();
			int count = rs.getInt(1);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Number of projects is: " + count);
			alert.showAndWait();
		} catch (SQLException e) {
			showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
			e.printStackTrace();
		}
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

	public void selectProjectLeader(ActionEvent event) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE workson SET Role = ? " + "WHERE workson.StudentId = ?;");
			ps.setString(1, "Leader");
			int selectedMemberId = selectLeaderComboBox.getValue();
			ps.setInt(2, selectedMemberId);
			ps.execute();
			showCompletionDialogueWithHeader(
					"Member with ID: " + selectedMemberId + " has been promoted to project leader");
			while (rs.next()) {
				selectLeaderComboBox.getItems().add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.err.println(e);
			showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
		}
	}

	public void loadProjectLeaderComboBox() {
		try {
			rs = stmt.executeQuery(
					"SELECT studentid from clubmember where clubid in (SELECT clubid from clubadmin where studentid ="
							+ USER_ID + ")");
			while (rs.next()) {
				selectLeaderComboBox.getItems().add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.err.println(e);
			showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
		}
	}

	public void loadProjectLeaderComboBox(ActionEvent event) {
		try {
			System.out.println("Changed Applicant Box");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void loadApplicantListComboBox(ActionEvent event) {
		try {
			int selectedClubId = clubIdComboBox.getValue();
			List<Student> s = studentRepo.findStudentsWhoAreApplyingToClubId(selectedClubId);
			clearApplicantListComboBox();
			applicantListComboBox.getItems().addAll(s);

		} catch (Exception e) {
			showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
			e.printStackTrace();
		}
	}

	private void clearApplicantListComboBox() {
		applicantListComboBox.getItems().clear();
	}

	public void approveApplicantToJoinClub(ActionEvent event) {
		int selectedClubId = clubIdComboBox.getValue();
		Student s = applicantListComboBox.getValue();
		int selectedApplicantStudentId = s.id;
		try {
			insertMemberToClub(selectedClubId, selectedApplicantStudentId);
			changeApplicationStatusToApproved(selectedApplicantStudentId);
			showCompletionDialogueWithHeader(s.info.getFirstName() + " has joined the club.");
		} catch (Exception e) {
			showErrorDialogue("Database Error", "Error", "An error was encountered please try again later");
			e.printStackTrace();
		}
	}
	
	private void insertMemberToClub(int clubId, int studentId) throws Exception {
		PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO clubmember(ClubID, StudentID, StatusID, FromDate) VALUES(?, ?, ?, ?);");
		statement.setInt(1, clubId);
		statement.setInt(2, studentId);
		statement.setInt(3, DatabaseDefinitions.ACTIVE_MEMBER);
		statement.setDate(4, getCurrentDateAsSQL());
		statement.execute();
	}
	
	private void changeApplicationStatusToApproved(int applicantId) {
		
	}

	private java.sql.Date getCurrentDateAsSQL() {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public void showErrorDialogue(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	public void showCompletionDialogueWithHeader(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(text);
		alert.showAndWait();
	}

	public int getClubId() throws Exception {
		rs = stmt.executeQuery("SELECT clubid from clubadmin where studentid =" + USER_ID + ";");
		while (rs.next()) {
			addProjectClubId.getItems().add(rs.getInt(1));
		}
		return 0;
	}

	public void loadComboBoxWithClubIds(ComboBox<Integer> comboBox) throws Exception {
		rs = stmt.executeQuery("SELECT clubid from clubadmin where studentid=" + USER_ID);
		while (rs.next()) {
			comboBox.getItems().add(rs.getInt(1));
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ID FROM projecttype");
			while (rs.next()) {
				addProjectTypeId.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT clubid from clubadmin where studentid=" + USER_ID);
			while (rs.next()) {
				addProjectClubId.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 3");
			while (rs.next()) {
				addProjectStatusId.getItems().add(rs.getInt(1));
			}
			rs = stmt.executeQuery(
					"SELECT id FROM project WHERE clubid in (SELECT clubid from clubadmin where studentid =" + USER_ID
							+ ")");
			while (rs.next()) {
				changeProjectId.getItems().add(rs.getInt(1));
			}
			rs = stmt.executeQuery("SELECT ID FROM status WHERE statustypeid = 3");
			while (rs.next()) {
				changeProjectStatusId.getItems().add(rs.getInt(1));
			}

			rs = stmt.executeQuery(
					"SELECT studentid from clubmember where clubid in (SELECT clubid from clubadmin where studentid ="
							+ USER_ID + ")");
			while (rs.next()) {
				addMemberId.getItems().add(rs.getInt(1));
			}
			rs = stmt.executeQuery(
					"SELECT id FROM project WHERE clubid in (SELECT clubid from clubadmin where studentid =" + USER_ID
							+ ")");
			while (rs.next()) {
				addMemberProjectId.getItems().add(rs.getInt(1));
			}

			loadComboBoxWithClubIds(computeProjectsClubId);
			loadComboBoxWithClubIds(clubIdComboBox);

			loadProjectLeaderComboBox();
			
			loadRepositories(conn);
			
			changeComboBoxViewToShowFullNameAndId(applicantListComboBox);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	private void changeComboBoxViewToShowFullNameAndId(ComboBox<Student> combobox) {
		combobox.setConverter(new StringConverter<Student>() {
			
			@Override
			public String toString(Student s) {
				return s.info.getFirstName() + " " + s.info.getLastName() + " - ID: " + s.id;
			}

			@Override
			public Student fromString(String string) {
				return combobox.getSelectionModel().getSelectedItem();
			}
		});
		
	}

	private void loadRepositories(Connection conn) {
		studentRepo = new MySQLStudentRepository(conn);
		clubRepo = new MySQLClubRepository(conn);
		clubMemberRepo = new MySQLClubMemberRepository(conn);
		projectRepo = new MySQLProjectRepository(conn, clubRepo);
		clubAppRepo = new MySQLClubApplicationRepository(conn, studentRepo, clubRepo);
	}
}
