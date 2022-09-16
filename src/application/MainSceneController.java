package application;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

public class MainSceneController {
	
	@FXML
	private Button btn2;
	
	@FXML
	private ListView fileListView;

	public void Button2Action(ActionEvent event) {
		FileChooser fc = new FileChooser();
		List<File> selectedFiles = fc.showOpenMultipleDialog(null);
		
		if (selectedFiles != null) {
			for (int i = 0; i < selectedFiles.size(); i++) {
				fileListView.getItems().add(selectedFiles.get(i).getAbsolutePath());
			}
		}
		else {
			System.out.println("File is not valid!");
		}
	}
	
}
