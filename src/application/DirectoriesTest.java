package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DirectoriesTest {
		
	Directories testClass = new Directories();
	
	@Test // Adding and removing a file
	public void fileListInsertDelete() {
		testClass.addFilePath("C:\\Java.txt");
		assertEquals(testClass.tempFilePaths.get(0), "C:\\Java.txt");
		
		testClass.removeFilePath("C:\\Java.txt");
		assertEquals(testClass.tempFilePaths.isEmpty(), true);
	}
	
	@Test // Adding and removing a folder
	public void folderListInsertDelete() {
		testClass.addFolderPath("C:\\Java");
		assertEquals(testClass.tempFolderPaths.get(0), "C:\\Java");
	
		testClass.removeFolderPath("C:\\Java");
		assertEquals(testClass.tempFolderPaths.isEmpty(), true);
	}
	
	@Test // Checking if a file is present
	public void fileListSearch() {
		testClass.addFilePath("C:\\Java.txt");
		assertEquals(testClass.filePathExist("C:\\Java.txt"), true);
		assertEquals(testClass.filePathExist("C:\\Javas.txt"), false);
	}	
	
	@Test // Checking if a folder is present
	public void folderListSearch() {
		testClass.addFolderPath("C:\\Java");
		assertEquals(testClass.folderPathExist("C:\\Java"), true);
		assertEquals(testClass.folderPathExist("C:\\Javas"), false);
	}
	
	@Test // Clearing the file list
	public void fileListClear() {
		testClass.addFilePath("C:\\Java.txt");
		testClass.addFilePath("C:\\JavaUtil.jar");
		assertEquals(testClass.tempFilePaths.isEmpty(), false);
		
		testClass.clearFilePaths();
		assertEquals(testClass.tempFilePaths.isEmpty(), true);
	}
	
	@Test // Clearing the folder list
	public void folderListClear() {
		testClass.addFolderPath("C:\\Java");
		testClass.addFolderPath("C:\\JavaUtil");
		assertEquals(testClass.tempFolderPaths.isEmpty(), false);
		
		testClass.clearFolderPaths();
		assertEquals(testClass.tempFolderPaths.isEmpty(), true);
	}
}
