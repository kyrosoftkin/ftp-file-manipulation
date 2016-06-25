package fileManipulate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ftp.FTPFile;

import FTPconnect.FTPconnect;

public class FileManipulator {
	protected FTPconnect conFtp;
	protected long tamanho;
	protected long tamanhoApagado;
	protected int arquivos;
	protected int diretorios;
	protected int arquivosApagados;
	protected int diretoriosApagados;
	
	public FileManipulator(FTPconnect conFtp) {
		this.conFtp = conFtp;
	}
	
	/*
	 * Varre um diretorio, todas as pastas e l� todos os arquivos 
	 * procurando os que tem o ano informado e apagando-os.
	 * Os diretorios vazios tamb�m s�o apagados.
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
			    	this.diretorios += 1;
					boolean deletar = deleteDirectory(filePath);

					if(deletar)
						this.diretoriosApagados += 1;
					
				} else {
					this.arquivos += 1;
					this.tamanho += size;
			    	
					if(dateFormater.format(file.getTimestamp().getTime()).equals(ano)) {
			    		boolean deletar = deleteFile(filePath);
			    		
			    		if(deletar) {
			    			this.arquivosApagados += 1;
			    			this.tamanhoApagado += size;
			    		}
					}
			    }	
			}    
		}
	}
	
	/*
	 * Varre um diretorio, todas as pastas e l� todos os arquivos, listando-os.
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
					this.tamanho += size;
					details += "\t\t Tamanho: " + Convert.convertBytes(size, true);
					details += "\t\t Data da �ltima altera��o: " + dateFormater.format(file.getTimestamp().getTime());
					System.out.println(details);
				}
			}    
		}
	}
	
	/*
	 * Apaga arquivos.
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
	 * Apaga diretorios vazios.
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
