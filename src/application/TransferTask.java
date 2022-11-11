package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import javafx.concurrent.Task;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;

public class TransferTask extends Task{
	
	private ArrayList<String> fileList;
	private ArrayList<String> folderList;
	private String directoryDestination;
	private ProgressBar progressBar;
	private MenuBar topMenubar;
	
	public TransferTask(MenuBar topMenubar, ProgressBar progressBar, ArrayList<String> fileList, ArrayList<String> folderList, String directoryDestination) {
		this.progressBar = progressBar;
		this.topMenubar = topMenubar;
		this.fileList = fileList;
		this.folderList = folderList;
		this.directoryDestination = directoryDestination;
		
	}
	
	@Override
	protected Object call() throws Exception {
		progressBar.setVisible(true);
		topMenubar.setDisable(true);
		
		int progressSum = folderList.size()  + fileList.size();
		int progressStatus = 0;
		int fileArraySize = fileList.size();
		int folderArraySize = folderList.size();
		File destination = new File(directoryDestination);

		for (int i = 0; i < fileArraySize; i++) {
			String filePath = fileList.get(i);
			File source = new File(filePath);
			
			updateValue("Copying " + filePath + " file");
			
			try {
			    FileUtils.copyFileToDirectory(source, destination);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			progressStatus++;
			updateProgress(progressStatus, progressSum);
		}
		
		for (int i = 0; i < folderArraySize; i++) {
			String folderPath = folderList.get(i);
			File source = new File(folderPath);
			
			updateValue("Copying " + folderPath +" and all the content included");
						
			try {
			    FileUtils.copyDirectoryToDirectory(source, destination);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			progressStatus++;
			updateProgress(progressStatus, progressSum);
		}
		
		progressBar.setVisible(false);
		topMenubar.setDisable(false);
		
		return "Completed Copying";
	}
}
