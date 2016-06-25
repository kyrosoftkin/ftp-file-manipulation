package fileManipulate;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPFile;

import FTPconnect.FTPconnect;

class DirectoryValidator {
	public static boolean isDotDirectory(String dir){
		return dir.equals("..") || dir.equals(".");
	}
	
	public static FTPFile[] validDirectoryPath(FTPconnect conFtp, String dir) throws IOException {
		FTPFile[] files = conFtp.getClient().listFiles(dir.replace(" ", "\\ "));
		
		return files; 
	}
}
