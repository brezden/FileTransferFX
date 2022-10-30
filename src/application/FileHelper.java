package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FileHelper {
	//Method that will copy all files over to destination
	public void FileCopy(ArrayList<String> fileList, String fileDestination) {
		int arraySize = fileList.size();
		File destination = new File(fileDestination);
		
		for (int i = 0; i < arraySize; i++) {
			String filePath = fileList.get(i);
			File source = new File(filePath);
			
			try {
			    FileUtils.copyFileToDirectory(source, destination);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}
	
	//Method that will copy all folders and contents over to destination
	public void FolderCopy(ArrayList<String> folderList, String folderDestination) {
		int arraySize = folderList.size();
		File destination = new File(folderDestination);
		
		for (int i = 0; i < arraySize; i++) {
			String folderPath = folderList.get(i);
			File source = new File(folderPath);
			
			try {
			    FileUtils.copyDirectoryToDirectory(source, destination);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}
}
