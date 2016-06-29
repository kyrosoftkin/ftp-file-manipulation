package fileManipulate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ftp.FTPFile;

import FTPconnect.FTPconnect;

public class FileManipulator {
	protected FTPconnect conFtp;
	protected long size;
	protected long erasedSize;
	protected int files;
	protected int directories;
	protected int erasedFiles;
	protected int erasedDirectories;
	
	public FileManipulator(FTPconnect conFtp) {
		this.conFtp = conFtp;
	}
	
	/*
	 * Scan a directory, all the folders and read all the files, looking for the 
	 * ones that has the year informed and erasing them.
	 * The empty directories also are erased.
	 */
	protected void delete(String dir, String ano) throws IOException {
		FTPFile[] files = conFtp.getClient().listFiles(dir.replace(" ", "\\ "));
		DateFormat dateFormater = new SimpleDateFormat("yyyy");
		
		for (FTPFile file : files) {
			String name = file.getName();
			long size = file.getSize();
			
			if(!DirectoryValidator.isDotDirectory(name)) {
				String filePath = dir+"/"+name;
				
				if (file.isDirectory()) {
			    	this.delete(filePath, ano);
			    	this.directories += 1;
					boolean deletar = deleteDirectory(filePath);

					if(deletar)
						this.erasedDirectories += 1;
					
				} else {
					this.files += 1;
					this.size += size;
			    	
					if(dateFormater.format(file.getTimestamp().getTime()).equals(ano)) {
			    		boolean deletar = deleteFile(filePath);
			    		
			    		if(deletar) {
			    			this.erasedFiles += 1;
			    			this.erasedSize += size;
			    		}
					}
			    }	
			}    
		}
	}
	
	/*
	 * Scan a directory, reading all the files and folders.
	 */
	protected void listFiles(String dir) throws IOException {
		FTPFile[] files = DirectoryValidator.validDirectoryPath(this.conFtp, dir);
		DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
		
		for (FTPFile file : files) {
			String name = file.getName();
			long size = file.getSize();
			
			if(!DirectoryValidator.isDotDirectory(name)) {
				String details = dir+"/"+name;
				
				if (file.isDirectory()) {
			    	this.listFiles(details);
				} else {
					this.size += size;
					details += "\t\t erasedSize: " + Convert.convertBytes(size, true);
					details += "\t\t Data da �ltima altera��o: " + dateFormater.format(file.getTimestamp().getTime());
					System.out.println(details);
				}
			}    
		}
	}
	
	/*
	 * Erase files.
	 */
	private boolean deleteFile(String filePath) {
		try {
			boolean deleted = conFtp.getClient().deleteFile(filePath);
			if (deleted) {
		        System.out.println("O arquivo: "+filePath+" foi removido com sucesso.");
		        return true;
			} else {
		        System.out.println("N�o foi poss�vel remover o arquivo: "+filePath+". Talvez ele n�o exista.");
		        return false;
			}
		} catch (IOException ex) {
    	    System.err.println("Ah n�o! Ocorreu um erro: " + ex.getMessage());
    	}
		
		return false;
	
	}
	
	/*
	 * Erase empty directories.
	 */
	private boolean deleteDirectory(String filePath) {
		try {
		    boolean deleted = conFtp.getClient().removeDirectory(filePath);
		    if (deleted) {
		        System.out.println("O diret�rio: "+filePath+" foi removido com sucesso.");
		        return true;
		    } else {
		        System.out.println("Diret�rio n�o removido: "+filePath+".");
		        return false;
		    }
		} catch (IOException ex) {
		    System.out.println("Ah n�o! Ocorreu um erro: " + ex.getMessage());
		}
		
		return false;

	}
	
	
}
