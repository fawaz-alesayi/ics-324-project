package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;
import utility.*;

public class ClubMemberController implements Initializable {
	@FXML
	private Button logoutBtn;
	@FXML
	private Button terminateBtn;
	@FXML
	private Button joinProjectBtn;
	@FXML
	private TableView projectTable;
	@FXML
	private ComboBox<Integer> joinProjectId;
	@FXML
	private ComboBox<Integer> terminateClubId;
	@FXML
	private DatePicker joinFromDate;
	@FXML
	private DatePicker joinToDate;

	private ObservableList<ObservableList> data;

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	final String USER_ID = LoginController.getUserID();

	public ClubMemberController() {
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

	private void initializeComboBoxes() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM project WHERE clubid in (SELECT clubid from clubmember "
					+ "where studentid =" + USER_ID + ")");
			while (rs.next()) {

				System.out.println(rs.getInt(1));
				joinProjectId.getItems().add(rs.getInt(1));
			}
			rs = stmt.executeQuery("SELECT clubid from clubmember where studentid=" + USER_ID);
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				terminateClubId.getItems().add(rs.getInt(1));
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		data = FXCollections.observableArrayList();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM project WHERE clubid in "+
			"(SELECT clubid from clubmember where studentid =" + USER_ID + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				projectTable.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");

			}
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}
			projectTable.setItems(data);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		initializeComboBoxes();
	}

	public void joinProject(ActionEvent event) {
		Integer projectId = joinProjectId.getValue();
		String fromDate = joinFromDate.getValue().toString();
		fromDate = "'" + fromDate + "'";
		String toDate = joinToDate.getValue().toString();
		toDate = "'" + toDate + "'";
		String role = "'" + "Member" + "'";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO workson VALUES(" + USER_ID + "," + projectId + "," + fromDate + "," + toDate
					+ "," + role + ")");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Volenteered for Project Successfully");
			alert.showAndWait();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void terminateMember(ActionEvent event) {
		try {
			stmt = conn.createStatement();
			Integer clubID = terminateClubId.getValue();
			stmt.executeUpdate("DELETE FROM clubmember WHERE clubid=" + clubID + " and studentid=" + USER_ID);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Terminated from Club Successfully");
			alert.showAndWait();
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
