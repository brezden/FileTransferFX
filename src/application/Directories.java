package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.DirectoryChooser;

public class Directories{
	
	ArrayList<String> tempFilePaths = new ArrayList<String>();
	ArrayList<String> tempFolderPaths = new ArrayList<String>();
		
	public boolean filePathExist(String filePath) {
		return(tempFilePaths.contains(filePath));
	}
	
	public boolean folderPathExist(String folderPath) {
		return(tempFolderPaths.contains(folderPath));
	}
	
	public void addFilePath(String filePath) {
		tempFilePaths.add(filePath);
	}
	
	public void addFolderPath(String folderPath) {
		tempFolderPaths.add(folderPath);
	}
	
	public void removeFilePath(String filePath) {
		tempFilePaths.remove(filePath);
	}
	
	public void removeFolderPath(String folderPath) {
		tempFolderPaths.remove(folderPath);
	}
	
	public void clearFilePaths() {
		tempFilePaths.clear();
	}
	
	public void clearFolderPaths() {
		tempFolderPaths.clear();
	}
}

