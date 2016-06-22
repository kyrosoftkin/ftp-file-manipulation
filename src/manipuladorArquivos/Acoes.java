package manipuladorArquivos;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ftp.FTPFile;

import conexaoFTP.ConectaFTP;

public class Acoes {
	protected ConectaFTP conFtp;
	long tamanho = 0;
	long tamanhoApagado = 0;
	int arquivos = 0;
	int diretorios = 0;
	int arquivosApagados = 0;
	int diretoriosApagados = 0;
	
	public Acoes(ConectaFTP conFtp) {
		this.conFtp = conFtp;
	}
	
	/*
	 * Varre um diretorio, todas as pastas e lê todos os arquivos 
	 * procurando os que tem o ano informado e apagando-os.
	 * Os diretorios vazios também s�o apagados.
	 */
	protected void apagar(String dir, String ano) throws IOException {
		FTPFile[] files = conFtp.getClient().listFiles(dir.replace(" ", "\\ "));
		DateFormat dateFormater = new SimpleDateFormat("yyyy");
			
		for (FTPFile file : files) {
			if(!file.getName().equals("..") && !file.getName().equals(".")) {
				String nome = file.getName();
				String caminhoArquivo = dir+"/"+nome;
				
				if (file.isDirectory()) {
			    	apagar(caminhoArquivo, ano);
			    	this.diretorios += 1;
					boolean deletar = deletarDiretorio(caminhoArquivo);

					if(deletar)
						this.diretoriosApagados += 1;
					
				} else {
					this.arquivos += 1;
					this.tamanho += file.getSize();
			    	
					if(dateFormater.format(file.getTimestamp().getTime()).equals(ano)) {
			    		boolean deletar = deletarArquivo(caminhoArquivo);
			    		
			    		if(deletar) {
			    			this.arquivosApagados += 1;
			    			this.tamanhoApagado += file.getSize();
			    		}
					}
			    }	
			}    
		}
	}
	
	/*
	 * Varre um diretorio, todas as pastas e l� todos os arquivos, listando-os.
	 */
	protected void listar(String dir) throws IOException {
		FTPFile[] files = conFtp.getClient().listFiles(dir.replace(" ", "\\ "));
		DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
		
		for (FTPFile file : files) {
			if(!file.getName().equals("..") && !file.getName().equals(".")) {
				String nome = file.getName();
				String detalhes = dir+"/"+nome;
				
				if (file.isDirectory()) {
			    	listar(detalhes);
				} else {
					this.tamanho += file.getSize();
					detalhes += "\t\t Tamanho: " + converterBytes(file.getSize(), true);
					detalhes += "\t\t Data da �ltima altera��o: " + dateFormater.format(file.getTimestamp().getTime());
					System.out.println(detalhes);
				}
			}    
		}
	}
	
	/*
	 * Converte de bytes para tamanho legivel.
	 */
	public static String converterBytes(long bytes, boolean sim) {
	    int unit = sim ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (sim ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (sim ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	/*
	 * Apaga arquivos.
	 */
	private boolean deletarArquivo(String caminhoArquivo) {
		try {
			boolean deletado = conFtp.getClient().deleteFile(caminhoArquivo);
			if (deletado) {
		        System.out.println("O arquivo: "+caminhoArquivo+" foi removido com sucesso.");
		        return true;
			} else {
		        System.out.println("N�o foi poss�vel remover o arquivo: "+caminhoArquivo+". Talvez ele n�o exista.");
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
	private boolean deletarDiretorio(String caminhoDiretorio) {
		try {
		    boolean deletado = conFtp.getClient().removeDirectory(caminhoDiretorio);
		    if (deletado) {
		        System.out.println("O diret�rio: "+caminhoDiretorio+" foi removido com sucesso.");
		        return true;
		    } else {
		        System.out.println("Diret�rio n�o removido: "+caminhoDiretorio+".");
		        return false;
		    }
		} catch (IOException ex) {
		    System.out.println("Ah n�o! Ocorreu um erro: " + ex.getMessage());
		}
		
		return false;

	}
}
