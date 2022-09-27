package application;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileController {

	public void fileInitializer() {
		
		String source = "C:\\Users\\Callum\\Documents\\Documents\\Personal\\School\\UFV\\Fall 2022\\COMP 230\\Week 1";
		File srcDir = new File(source);
		
		String destination = "C:\\Users\\Callum\\Desktop\\Test";
		File destDir = new File(destination);
		
		/*
		try {
			FileUtils.copyFile(srcDir, destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
