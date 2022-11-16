package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;

public class TransferTask extends Task{
	
	private ArrayList<String> fileList;
	private ArrayList<String> folderList;
	private String directoryDestination;
	private ProgressBar progressBar;
	private MenuBar topMenubar;
	private Button presetAddButton;
	private boolean overwriteFiles;
	private RadioButton ExistingFileStatus;
	private Button copyFilesButton;
	private Button browseButton;
	
	public TransferTask(boolean overwriteFiles, MenuBar topMenubar, 
						ProgressBar progressBar, ArrayList<String> fileList, 
						ArrayList<String> folderList, String directoryDestination,
						Button presetAddButton, RadioButton ExistingFileStatus,
						Button copyFilesButton, Button browseButton) {
		this.progressBar = progressBar;
		this.overwriteFiles = overwriteFiles;
		this.topMenubar = topMenubar;
		this.fileList = fileList;
		this.folderList = folderList;
		this.directoryDestination = directoryDestination;
		this.presetAddButton = presetAddButton;
		this.ExistingFileStatus = ExistingFileStatus;
		this.copyFilesButton = copyFilesButton;
		this.browseButton = browseButton;
	}
	
	@Override
	protected Object call() throws Exception {
		progressBar.setVisible(true);
		topMenubar.setDisable(true);
		presetAddButton.setDisable(true);
		ExistingFileStatus.setDisable(true);
		copyFilesButton.setDisable(true);
		browseButton.setDisable(true);
		
		int progressSum = folderList.size()  + fileList.size();
		int progressStatus = 0;
		int transferCompleted = 0;
		int fileNotFound = 0;
		int fileArraySize = fileList.size();
		int folderArraySize = folderList.size();
				
		File destination = new File(directoryDestination);
		
		// Methods for overwriting existing files
		if (overwriteFiles) {
			for (int i = 0; i < fileArraySize; i++) {
				String filePath = fileList.get(i);
				File source = new File(filePath);
				
				if (source.exists() && (!(source.toString().equals(destination.toString())))) {
					updateValue("Copying " + filePath + " file");
					
					try {
					    FileUtils.copyFileToDirectory(source, destination);
					} catch (IOException e) {
					    e.printStackTrace();
					}
					
					transferCompleted++;
				}
				
				progressStatus++;
				updateProgress(progressStatus, progressSum);
			}
			
			for (int i = 0; i < folderArraySize; i++) {
				String folderPath = folderList.get(i);
				File source = new File(folderPath);
				
				updateValue("Copying " + folderPath +" and all the content included");
				
				File tempFolder = (new File(destination.toString() + "\\" +  folderPath.substring(folderPath.lastIndexOf("\\") + 1).trim()));
				String result = folderPath.substring(0, folderPath.indexOf("\\"));

				if (source.exists() && (!(destination.toString().equals(Paths.get(folderPath).getParent().toString())))) {

					if (tempFolder.exists()) {
						FileUtils.deleteDirectory(tempFolder);
					}
					
					try {
					    FileUtils.copyDirectoryToDirectory(source, destination);
					} catch (IOException e) {
					    e.printStackTrace();
					}
					
					transferCompleted++;
				}
				
				progressStatus++;
				updateProgress(progressStatus, progressSum);
			}
		}
		
		// Methods for skipping existing files
		else {
			for (int i = 0; i < fileArraySize; i++) {
				String filePath = fileList.get(i);
				File source = new File(filePath);
				
				if (!(source.toString().equals(destination.toString()))) {
					if ((!(new File(destination.toString() + "\\" +  filePath.substring(filePath.lastIndexOf("\\") + 1).trim()).exists())) && source.exists()) {
						updateValue("Copying " + filePath + " file");
						try {
						    FileUtils.copyFileToDirectory(source, destination);
						} catch (IOException e) {
						    e.printStackTrace();
						    fileNotFound++;
						}
						
						
						transferCompleted++;
					}
				}

				progressStatus++;
				updateProgress(progressStatus, progressSum);
			}
			
			for (int i = 0; i < folderArraySize; i++) {
				String folderPath = folderList.get(i);
				File source = new File(folderPath);
					
				if (!(destination.toString().equals(Paths.get(folderPath).getParent().toString()))) {
					if ((!(new File(destination.toString() + "\\" +  folderPath.substring(folderPath.lastIndexOf("\\") + 1).trim()).exists())) && source.exists()) {
						updateValue("Copying " + folderPath +" and all the content included");
						
						try {
						    FileUtils.copyDirectoryToDirectory(source, destination);
						} catch (IOException e) {
						    e.printStackTrace();
						    fileNotFound++;
						}
						
						transferCompleted++;
					}
				}
				progressStatus++;
				updateProgress(progressStatus, progressSum);
			}
		}
		
		progressBar.setVisible(false);
		topMenubar.setDisable(false);
		presetAddButton.setDisable(false);
		ExistingFileStatus.setDisable(false);
		copyFilesButton.setDisable(false);
		browseButton.setDisable(false);
		
		if ((progressStatus - transferCompleted) > 1){
			return ("Completed Copying. Skipped " + (progressStatus - transferCompleted) + " copies due to path already existing or not being found");
		}
		
		else if ((progressStatus - transferCompleted) == 1){
			return ("Completed Copying. Skipped 1 copy due to path already existing or not being found");
		}
		
		return "Completed Copying";
	}
}
