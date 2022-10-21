package application;

import java.util.ArrayList;

public class FileController {
	
	ArrayList<String> tempFilePaths = new ArrayList<String>();
	ArrayList<String> tempFolderPaths = new ArrayList<String>();
	
	public FileController() {
		/*
		
		String source = "C:\\Users\\Callum\\Documents\\Documents\\Personal\\School\\UFV\\Fall 2022\\COMP 230\\Week 1";
		File srcDir = new File(source);
		
		String destination = "C:\\Users\\Callum\\Desktop\\Test";
		File destDir = new File(destination);
		
		try {
			FileUtils.copyFile(srcDir, destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
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
