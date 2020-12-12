package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utility.BCrypt;
import utility.CreateDbConnection;
import utility.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private Button logInbtn;
	@FXML
	private Button guestButton;
	@FXML
	private Label welcomeLbl;
	@FXML
	private Label statusLbl;
	@FXML
	private TextField userID;
	@FXML
	private PasswordField password;


	 Connection conn = null;
	 PreparedStatement stmt = null;
	 ResultSet rs = null;
	 static String id = null;

	 protected static String getUserID(){
		 return id;
	 }
	public LoginController() {
		try{
			conn = CreateDbConnection.createConnection();

		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	public void login(ActionEvent event) {
		id = userID.getText();

		String passW = password.getText();
		int userType= -1;
		if (id.isEmpty() || passW.isEmpty()) {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Enter LogIn Information!");
		} else {
			try {
				int uid = Integer.parseInt(id);
				stmt = conn.prepareStatement("SELECT password FROM user WHERE StudentID = ?");
				stmt.setInt(1, uid);
				rs = stmt.executeQuery();
				rs.next();
				String encPassW = rs.getString(1);
				if(!BCrypt.checkpw(passW, encPassW)){
					statusLbl.setTextFill(Color.RED);
					statusLbl.setText("invalid LogIn Information! please try again");
				}else{
					stmt = conn.prepareStatement("SELECT userTypeID FROM user WHERE StudentID = ?");
					stmt.setInt(1, uid);
					rs = stmt.executeQuery();
					rs.next();
					userType = rs.getInt(1);
					statusLbl.setTextFill(Color.GREEN);
					statusLbl.setText("Login successful!");
				}

			} catch (SQLException e) {
				ShowDialog.showErrorDialogue("Database Error", "Error", "An error was encountered, please make sure your login information is correct");
				e.printStackTrace();
			}
		}
		if(userType == 1){
			try {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/SystemAdmin.fxml")));
				stage.setScene(scene);
				stage.show();

			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
		else if(userType == 2){
			try {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/ClubAdmin.fxml")));
				stage.setScene(scene);
				stage.show();

			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
		else if(userType == 3){
			try {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				//stage.setMaxHeight(1000);
				//stage.setMaxWidth(1500);
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/ClubMember.fxml")));
				stage.setScene(scene);
				stage.show();

			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
		else if(userType == 4){
			try {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				//stage.setMaxHeight(1000);
				//stage.setMaxWidth(1500);
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/Guest.fxml")));
				stage.setScene(scene);
				stage.show();

			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	public void guestLogin(ActionEvent event){
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/application/Guest.fxml")));
			stage.setScene(scene);
			stage.show();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (conn == null) {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Server Error : Check Server Connection");
		} else {
			statusLbl.setTextFill(Color.GREEN);
			statusLbl.setText("Server is up : Good to go");
		}
		/*BooleanBinding bb = new BooleanBinding() {       // binds the functionality of buttons with the text in the text area
			{
				super.bind();

			}

			@Override
			protected boolean computeValue() {
				return conn.equals(null);

			}
		};
		guestButton.disableProperty().bind(bb);*/
	}
}
