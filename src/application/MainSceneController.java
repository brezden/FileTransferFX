package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MainSceneController {
	@FXML
	private TextField tfTitle;

	// Event Listener on Button.onAction
	@FXML
	public void btnClick(ActionEvent event) {
		Stage mainWindow =(Stage) tfTitle.getScene().getWindow();
		String title = tfTitle.getText();
		mainWindow.setTitle(title);
		
	}
}
