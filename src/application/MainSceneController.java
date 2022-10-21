package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainSceneController implements Initializable {
	
	@FXML
	private Button btn2;
	
	@FXML
	private ListView<String> pathListView;

	FileController fileController = new FileController();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pathListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
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
		ObservableList<String> fileRemoveObject = pathListView.getSelectionModel().getSelectedItems();
		Object[] fileRemoveArray = fileRemoveObject.toArray(); // Converting the interface to an array
		
		int arraySize = fileRemoveArray.length;
		
		for (int i = 0; i < arraySize; i++) {
			String filePath = (String) fileRemoveArray[i];
			
			if (fileController.filePathExist(filePath)) {
				pathListView.getItems().remove(filePath);
				fileController.removeFilePath(filePath);
			}
		}
	}
	
	public void removeFolderButton(ActionEvent event) {
		ObservableList<String> folderRemoveObject = pathListView.getSelectionModel().getSelectedItems();
		Object[] FolderRemoveArray = folderRemoveObject.toArray(); // Converting the interface to an array
		
		int arraySize = FolderRemoveArray.length;
			
		for (int i = 0; i < arraySize; i++) {
			String folderPath = (String) FolderRemoveArray[i];
			
			if (fileController.folderPathExist(folderPath)) {
				pathListView.getItems().remove(folderPath);
				fileController.removeFolderPath(folderPath);
			}
		}
	}
	
	public void clearButton(ActionEvent event) {
		pathListView.getItems().clear();
		fileController.clearFilePaths();
		fileController.clearFolderPaths();
	}
}