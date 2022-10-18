package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;

public class FileController {
	
	Queue<String> tempFilePaths = new ArrayDeque<String>();
	Queue<String> tempFolderPaths = new ArrayDeque<String>();
	
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
}
