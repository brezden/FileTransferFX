package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
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
	private ListView pathListView;
	
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
			String folderPath = selectedFolder.getAbsolutePath();
			folderPath = folderPath.replace("\\" , "\\\\");
			
			if (fileController.folderPathExist(folderPath)) {
				System.out.println("Folder Exists!");; //Set Program Message to show that this folder exists already
			}
			else {
				pathListView.getItems().add(folderPath);
				fileController.addFolderPath(folderPath);
			}
		}
	}
	
	public void addFilesButton(ActionEvent event){
		
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
				String filePath = selectedFiles.get(i).getAbsolutePath();
				filePath = filePath.replace("\\" , "\\\\");
				
				if (fileController.filePathExist(filePath)) {
					System.out.println("File Exists!");; //Set Program Message to show that this file exists already
				}
				else {
					pathListView.getItems().add(filePath);
					fileController.addFilePath(filePath);
				}
			}
		}
	}
	
	public void removeFileButton(ActionEvent event) {
		Object fileRemoveObject = pathListView.getSelectionModel().getSelectedItem();
		
		if (fileRemoveObject != null) {
			String fileRemovePath = fileRemoveObject.toString();
			
			if (fileController.filePathExist(fileRemovePath)) {
				pathListView.getItems().remove(fileRemovePath);
				fileController.removeFilePath(fileRemovePath);
			}
		}
	}
	
	public void removeFolderButton(ActionEvent event) {
		Object folderRemoveObject = pathListView.getSelectionModel().getSelectedItem();
		
		if (folderRemoveObject != null) {
			String folderRemovePath = folderRemoveObject.toString();
			
			if (fileController.folderPathExist(folderRemovePath)) {
				pathListView.getItems().remove(folderRemovePath);
				fileController.removeFolderPath(folderRemovePath);
			}
		}
	}
}
