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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class UserInterface implements Initializable{
	
	@FXML
	public ListView<String> DirectoryListView;
	
	@FXML
	public ListView<String> presetListView;
	
	@FXML
	private Label consoleLabel;
	
	@FXML
	private TextField presetTextField;
	
	@FXML
	private Button presetAddButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DirectoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		consoleLabel.setText("Welcome to File Transfer FX");
		presetListSet();
	}
	
	Directories DirectoryClass = new Directories();
	Preset PresetClass = new Preset();
	
	public void presetListSet() {
		File directoryPath = new File("preset");
		String contents[] = directoryPath.list();
	      ObservableList<String> presetNames = presetListView.getItems();
	      for(int i=0; i<contents.length; i++) {
	    	  String presetName = (contents[i].substring(0, contents[i].lastIndexOf('.')));
	    	  if (!presetNames.contains(presetName))
	    		  presetListView.getItems().add(presetName);
	      }
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
	
	public void consoleLabelEdit(String textChange) {
		consoleLabel.setText(textChange);
	}
	
	public void addFolderButton(ActionEvent event) {
		DirectoryChooser FolderSelector = new DirectoryChooser();
		FolderSelector.setTitle("Select Folder");
		
		File SelectedFolder = FolderSelector.showDialog(null);

		if (SelectedFolder != null) {
			String FolderPath = SelectedFolder.getAbsolutePath();
			//FolderPath = FolderPath.replace("\\" , "\\\\");
			folderPathHandler(FolderPath);
			consoleLabelEdit("Added " +  FolderPath + " and its contents.");
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
			filePathHandler(selectedFiles);
			consoleLabelEdit("Added " + selectedFiles.size() + " files.");
		}
	}
	
	public void removeFileButton(ActionEvent event) {
		ObservableList<String> fileRemoveObject = DirectoryListView.getSelectionModel().getSelectedItems();
		Object[] fileRemoveArray = fileRemoveObject.toArray();
		int arraySize = fileRemoveArray.length;
		removeAllFiles(fileRemoveArray, arraySize);
		consoleLabelEdit("Removed selected files.");
	}
	
	public void removeFolderButton(ActionEvent event) {
		ObservableList<String> folderRemoveObject = DirectoryListView.getSelectionModel().getSelectedItems();
		Object[] folderRemoveArray = folderRemoveObject.toArray();
		int arraySize = folderRemoveArray.length;
		removeAllFolders(folderRemoveArray, arraySize);
		consoleLabelEdit("Removed selected folders.");
	}
	
	public void selectedItemButton(ActionEvent event) {
		ObservableList<String> filePathsObject = DirectoryListView.getSelectionModel().getSelectedItems();
		Object[] filePathsArray = filePathsObject.toArray();
		int arraySize = filePathsArray.length;
		
		removeAllFolders(filePathsArray, arraySize);
		removeAllFiles(filePathsArray, arraySize);
		consoleLabelEdit("Removed selected items.");
	}
	
	public void allFilesRemove(ActionEvent event) {
		Object[] tempFileArray = DirectoryClass.tempFilePaths.toArray();
		int arraySize = tempFileArray.length;		
		removeAllFiles(tempFileArray, arraySize);
		DirectoryClass.clearFilePaths();
		consoleLabelEdit("Removed all files.");
	}
	
	public void allFoldersRemove(ActionEvent event) {
		Object[] tempFolderArray = DirectoryClass.tempFolderPaths.toArray();
		int arraySize = tempFolderArray.length;		
		removeAllFolders(tempFolderArray, arraySize);
		DirectoryClass.clearFolderPaths();
		consoleLabelEdit("Removed all folders.");
	}
	
	public void clearButton(ActionEvent event) {
		DirectoryClass.clearFilePaths();
		DirectoryClass.clearFolderPaths();
		DirectoryListView.getItems().clear();
		consoleLabelEdit("Removed all items.");
	}
	
	public void filePathHandler(List<File> selectedFiles) {
		for (int i = 0; i < selectedFiles.size(); i++) {
			String filePath = selectedFiles.get(i).getAbsolutePath();
			//filePath = filePath.replace("\\" , "\\\\");
			
			if (DirectoryClass.filePathExist(filePath) == false) {
				DirectoryListView.getItems().add(filePath);
				DirectoryClass.addFilePath(filePath);
			}
		}
	}
	
	public void folderPathHandler(String folderPath) {
		if (DirectoryClass.folderPathExist(folderPath) == false) {
			DirectoryListView.getItems().add(folderPath);
			DirectoryClass.addFolderPath(folderPath);
		}
	}
	
	public void removeAllFiles(Object[] fileRemoveArray, int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			String filePath = (String) fileRemoveArray[i];
			
			if (DirectoryClass.filePathExist(filePath)) {
				DirectoryListView.getItems().remove(filePath);
				DirectoryClass.removeFilePath(filePath);
			}
		}
	}
	
	public void removeAllFolders(Object[] folderRemoveArray, int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			String folderPath = (String) folderRemoveArray[i];
			
			if (DirectoryClass.folderPathExist(folderPath)) {
				DirectoryListView.getItems().remove(folderPath);
				DirectoryClass.removeFolderPath(folderPath);
			}
		}
	}
	
	public void addPreset(ActionEvent event) throws IOException {
		String presetName = presetTextField.getText();
		PresetClass.PresetHandler(presetName.replaceAll("\\s+",""));
		presetListSet();
	}
}