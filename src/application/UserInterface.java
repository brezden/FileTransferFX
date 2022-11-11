package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;

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
	
	@FXML
	private MenuBar topMenubar;
	
	@FXML
	private TextField destinationField;
	
	@FXML
	private RadioButton ExistingFileStatus;
	
	@FXML
	private ProgressBar progressBar;
	
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
	    	  File presetFile = new File("preset/" + presetName + ".json");
	    	  
	    	  if (presetFile.length() == 0) {
	    		  presetFile.delete();
	    	  }
	    	  
	    	  else if (!presetNames.contains(presetName))
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
		
		if (DirectoryListView.getItems().isEmpty() || (DirectoryListView.getItems().size() <= 1)) {
			consoleLabelEdit("Must have atleast 2 files or folders added");
			return;
		}
		
		String inputPresentName = presetTextField.getText();
		String presetName = inputPresentName.replaceAll("\\s+","");
		
		//Checking if the preset name is not long enough
		if (presetName.length() < 1) {
			consoleLabelEdit("Enter a name for the preset in the text box");
			return;
		}
		
		//Removing all the whitespace in the preset name
		PresetClass.PresetHandler(presetName.replaceAll("\\s+",""));
		
		//Checking if the preset name exists in the list
		if (!presetListView.getItems().contains(presetName)) {
			presetListView.getItems().add(presetName);
			consoleLabelEdit("Created preset with the name " + presetName);
		}
		
		//If the name does exist then we change the console log to show "Edited"
		else {
			consoleLabelEdit("Edited preset with the name " + presetName);
		}
	}
	
	//Removes the preset that is selected
	public void removePreset(ActionEvent event) throws IOException {
		String preset = presetListView.getSelectionModel().getSelectedItem();
		
		if (!(preset == null)) {
			//Making the file empty so we can delete it when the program is restarted
			FileWriter file = new FileWriter("preset/" + preset + ".json");
			presetListView.getItems().remove(preset);
			consoleLabelEdit("Removed " + preset + " preset");
		}
	}
	
	//Loads the preset into the list and assigns the values to the correct list
	public void loadPresetList(ActionEvent event) throws IOException, ParseException {
		String preset = presetListView.getSelectionModel().getSelectedItem();
		
		if (!(preset == null)) {
			List<String> presetList = PresetClass.PresetGetter(preset);
			//Clearing lists and previous paths
			DirectoryListView.getItems().clear();
			DirectoryClass.clearFilePaths();
			DirectoryClass.clearFolderPaths();
			
			for (int i = 0; i < presetList.size(); i++) {
				String directoryStringPath = presetList.get(i);
				Path directoryPath = Paths.get(directoryStringPath);
				//Checking if the path is a directory or a file
				if (Files.isDirectory(directoryPath)) {
					DirectoryClass.addFolderPath(directoryStringPath);
				}
				else {
					DirectoryClass.addFilePath(directoryStringPath);
				}
				//Adding the path to the list
				DirectoryListView.getItems().add(directoryStringPath);
			}
			
			consoleLabelEdit("Loaded " + preset + " preset");
		}
	}
	
	public void chooseDestination(ActionEvent event) {
		DirectoryChooser FolderDestination = new DirectoryChooser();
		FolderDestination.setTitle("Select Destination");
		
		File SelectedFolderDestination = FolderDestination.showDialog(null);
		
		if (SelectedFolderDestination != null) {
			destinationField.setAlignment(Pos.CENTER_LEFT); // Changing the align to left for the text field
			String FolderDestinationPath = SelectedFolderDestination.getAbsolutePath();
			DirectoryClass.directoryDestinationSetter(FolderDestinationPath);
			destinationField.setText(FolderDestinationPath);
			consoleLabelEdit("Set Destination to " + FolderDestinationPath);
			return;
		}
		
		consoleLabelEdit("Choose a valid destination");
	}
	
	//Calls the function to set the existing value status
	public void ExistingFileStatusSetter(ActionEvent event) {
		DirectoryClass.ExistingValueSetter(ExistingFileStatus.isSelected());
		String value;
		
		//Setting the console output to the corresponding value
		if (ExistingFileStatus.isSelected()) {
			value = "On";
		}
		else {
			value = "Off";
		}
		
		consoleLabelEdit("Turned " + value + " Overwrite Existing Files");
	}
	
	//Method that calls on the helper class to transfer the files
	public void copyFilesButton(ActionEvent event) throws InterruptedException {
		TransferTask transferTask = new TransferTask(topMenubar, progressBar, DirectoryClass.filePathGetter(), 
													 DirectoryClass.folderPathGetter(), DirectoryClass.directoryDestinationGetter());
		
		progressBar.progressProperty().bind(transferTask.progressProperty());
		transferTask.valueProperty().addListener((observable, oldValue, newValue) -> consoleLabel.setText(String.valueOf(newValue)));

		Thread th = new Thread(transferTask);
		th.setDaemon(true);
		th.start();
	}
}