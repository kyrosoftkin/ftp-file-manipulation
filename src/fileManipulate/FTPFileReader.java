package fileManipulate;

import java.io.IOException;
import FTPconnect.FTPconnect;

public class FTPFileReader extends FileManipulator {
	private String dir;
	
	public FTPFileReader(FTPconnect conFtp, String dir) {
		super(conFtp);
		this.dir = dir;
	}
	
	/*
	 * Erase files.
	 */
	public void apagarArquivos() {
		try{
			super.conFtp.openConnection();
			super.delete(dir, "2016");
			
			SizeCalculator.totalSize(super.tamanho);
			SizeCalculator.totalDeletedSize(super.tamanhoApagado);
			SizeCalculator.actualSize(super.tamanho, super.tamanhoApagado);
			} catch(IOException ex){
			System.out.println("Oops! Algo deu errado!");
	        ex.printStackTrace();
		} finally { 
			conFtp.closeConnection();
		}
	}
	
	/*
	 * Lists all the files and return all of it`s informations.
	 * Returns it`s size, path, name, extension and the last modified date.
	 */
	public void listarArquivos() {
		try{
			this.conFtp.openConnection();
			super.listFiles(dir);
			
			SizeCalculator.totalSize(super.tamanho);;
		} catch(IOException ex){
			System.out.println("Oops! Something is wrong!");
	        ex.printStackTrace();
		} finally { 
			conFtp.closeConnection();
		}
	}
}