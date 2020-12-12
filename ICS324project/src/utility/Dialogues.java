package utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dialogues {

	public static void showErrorDialogue(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	public static void showCompletionDialogueWithHeader(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(text);
		alert.showAndWait();
	}
}
