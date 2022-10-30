package application;

import java.util.ArrayList;

public class Directories{
	
	static ArrayList<String> tempFilePaths = new ArrayList<String>();
	static ArrayList<String> tempFolderPaths = new ArrayList<String>();
	static String directoryDestination;
	static boolean ExistingFileStatus = false; //Default is false

	public boolean filePathExist(String filePath) {
		return(tempFilePaths.contains(filePath));
	}
	
	public boolean folderPathExist(String folderPath) {
		return(tempFolderPaths.contains(folderPath));
	}
	
	public ArrayList<String> filePathGetter(){
		return tempFilePaths;
	}
	
	public ArrayList<String> folderPathGetter(){
		return tempFolderPaths;
	}
	
	public String directoryDestinationGetter() {
		return directoryDestination;
	}
	
	public void directoryDestinationSetter(String directoryPath) {
		directoryDestination = directoryPath;
	}
	
	public void ExistingValueSetter(boolean status) {
		ExistingFileStatus = status;
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

