package br.com.restdoc.util;


import java.io.File;
import java.io.FileFilter;
import java.util.List;


public class Utils {

    public static void findFiles(File srcDir, List<String> fileNames, FileFilter fileFilter) {
		File[] dirsFiltered = srcDir.listFiles(fileFilter);
		for (File file : dirsFiltered) {
			fileNames.add(file.getAbsolutePath());
		}
		File[] dirs = srcDir.listFiles();
		for (File file : dirs) {
			if(file.isDirectory()){
				Utils.findFiles(file, fileNames, fileFilter);
			}
		}
    }
}
