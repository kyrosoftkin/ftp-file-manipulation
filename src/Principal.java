import conexaoFTP.ConectaFTP;
import manipuladorArquivos.LerArquivoFTP;

public class Principal {
	public static void main(String[] args)
	{
		ConectaFTP con = new ConectaFTP("", "", "");
		LerArquivoFTP arq = new LerArquivoFTP(con, "");
		
		arq.listarArquivos();
	}
}