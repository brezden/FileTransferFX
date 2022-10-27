package application;

import java.util.ArrayList;

public class Directories{
	
	ArrayList<String> tempFilePaths = new ArrayList<String>();
	ArrayList<String> tempFolderPaths = new ArrayList<String>();
		
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

