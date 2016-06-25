package manipuladorArquivos;

import java.io.IOException;
import FTPconnect.FTPconnect;

public class FTPFileReader extends FileManipulator {
	private String dir;
	
	public FTPFileReader(FTPconnect conFtp, String dir) {
		super(conFtp);
		this.dir = dir;
	}
	
	/*
	 * Apaga arquivos.
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
	 * Lista arquivos devolvendo todos os dados correspondentes aos mesmos.
	 * Devolve o tamanho, caminho, nome, extens�o e ultima data de modifica��o.
	 */
	public void listarArquivos() {
		try{
			this.conFtp.openConnection();
			super.listFiles(dir);
			
			SizeCalculator.totalSize(super.tamanho);;
		} catch(IOException ex){
			System.out.println("Oops! Algo deu errado!");
	        ex.printStackTrace();
		} finally { 
			conFtp.closeConnection();
		}
	}
}