package manipuladorArquivos;

import java.io.IOException;
import conexaoFTP.ConectaFTP;

public class LerArquivoFTP extends Acoes {
	private String dir;
	
	public LerArquivoFTP(ConectaFTP conFtp, String dir) {
		super(conFtp);
		this.dir = dir;
	}
	
	/*
	 * Apaga arquivos.
	 */
	public void apagarArquivos() {
		try{
			conFtp.abreConexao();
			apagar(dir, "2016");
			
			long atual = tamanho - tamanhoApagado;
			String tamanhoTotal = converterBytes(tamanho, true);
			String tamanhoTotalApagado = converterBytes(tamanhoApagado, true);
			String tamanhoAtual = converterBytes(atual, true);
			
			System.out.println("Tamanho total do diretório: " + tamanhoTotal);
			System.out.println("Tamanho total apagado: " + tamanhoTotalApagado);
			System.out.println("Tamanho atual do diretório: " + tamanhoAtual);
		} catch(IOException ex){
			System.out.println("Oops! Algo deu errado!");
	        ex.printStackTrace();
		} finally { 
			conFtp.fechaConexao();
		}
	}
	
	/*
	 * Lista arquivos devolvendo todos os dados correspondentes aos mesmos.
	 * Devolve o tamanho, caminho, nome, extensão e ultima data de modificação.
	 */
	public void listarArquivos() {
		try{
			conFtp.abreConexao();
			listar(dir);
			
			String tamanhoTotal = converterBytes(tamanho, true);
			System.out.println("Tamanho total do diretório: " + tamanhoTotal);
		} catch(IOException ex){
			System.out.println("Oops! Algo deu errado!");
	        ex.printStackTrace();
		} finally { 
			conFtp.fechaConexao();
		}
	}
}