package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.paint.Color;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;
import javafx.util.Callback;
import utility.BCrypt;
import utility.CreateDbConnection;

public class GuestController implements Initializable {
	@FXML
	private Button logoutBtn;
	@FXML
	private Button registerBtn;
	@FXML
	private TableView ClubsTable;
	@FXML
	private TextField stuID;
	@FXML
	private TextField passW;


	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	private ObservableList<ObservableList> data;

	public GuestController() {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data = FXCollections.observableArrayList();

		try {
			stmt = conn.prepareStatement("SELECT * FROM club");
			rs = stmt.executeQuery();

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

				ClubsTable.getColumns().addAll(col);

			}
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				data.add(row);

			}
			ClubsTable.setItems(data);

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private String[] getClubInfo(){
		TablePosition pos = (TablePosition) ClubsTable.getSelectionModel().getSelectedCells().get(0);
		int row = pos.getRow();

		Object item = ClubsTable.getItems().get(row);
		String temp = item.toString();

		temp = temp.substring(1, temp.length()-1);
		System.out.println(temp);

		String[] info = temp.split(", ");
		for(int i=0;i<info.length;i++){
			System.out.println(info[i]);
		}
		return info;
	}
	public void registerInClub(ActionEvent event){
		String[] clubInfo = getClubInfo();
		int clubId = Integer.parseInt(clubInfo[0]);
		int studentId = Integer.parseInt(stuID.getText());
		String password = passW.getText();
		String encPassWord = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(encPassWord);
		try {

			// stores the information in club_applicant table
		    stmt = conn.prepareStatement("INSERT INTO club_applicant VALUES (?, ?, ?, ?)");
		    stmt.setInt(1, clubId);
		    stmt.setInt(2, studentId);
		    stmt.setDate(3, getCurrentDateAsSQL());
		    stmt.setInt(4, 21);
		    stmt.execute();

		    Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("a Request to join "+clubInfo[1]+ " has been sent! Awating approval" );
			alert.showAndWait();
		    stmt = conn.prepareStatement("SELECT StudentID from user ");
		    rs = stmt.executeQuery();
		    boolean flag = false;
		    while(rs.next()){
		    	if(rs.getInt(1) == studentId){
		    		flag = true; // a user with the same StudentID exists
		    	}
		    }
		    if(!flag){
			//stores the information in user table
			stmt = conn.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?)");
			stmt.setInt(1, studentId);
			stmt.setInt(2, 4);
			stmt.setString(3, encPassWord);
			stmt.setInt(4, 20);
		    stmt.execute();
		    }

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	private java.sql.Date getCurrentDateAsSQL() {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
		}
}
