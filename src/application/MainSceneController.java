package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainSceneController {
	
	@FXML
	private Button btn2;
	
	@FXML
	private ListView fileListView;
	
	FileController fileController = new FileController();
	
	public void menuBarHelpButton(ActionEvent event) {
		try {
		    Desktop.getDesktop().browse(new URL("https://github.com/brezden/FileTransferFX").toURI());
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (URISyntaxException e) {
		    e.printStackTrace();
		}
	}

	public void addFolderButton(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Folder");

		File selectedFolder = directoryChooser.showDialog(null);
		
		if (selectedFolder != null) {
			fileListView.getItems().add(selectedFolder.getAbsolutePath());	
		}
	}
	
	public void addFilesButton(ActionEvent event) {
	
		fileController.FileTest();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Files");

		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("All Files", "*.*"),
		        new ExtensionFilter("Text Files", "*.txt"),
		        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		        new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
		
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
		
		if (selectedFiles != null) {
			for (int i = 0; i < selectedFiles.size(); i++) {
				fileListView.getItems().add(selectedFiles.get(i).getAbsolutePath());
			}
		}
	}
	
	public void removeFilesButton(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Close File");

		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("All Files", "*.*"),
		        new ExtensionFilter("Text Files", "*.txt"),
		        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		        new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
		
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
		
		if (selectedFiles != null) {
			for (int i = 0; i < selectedFiles.size(); i++) {
				fileListView.getItems().remove(selectedFiles.get(i).getAbsolutePath());
			}
		}
	}
	
	public void removeFolderButton(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Close Folder");

		File selectedFolder = directoryChooser.showDialog(null);
		
		if (selectedFolder != null) {
			fileListView.getItems().remove(selectedFolder.getAbsolutePath());	
		}
	}
	
}
