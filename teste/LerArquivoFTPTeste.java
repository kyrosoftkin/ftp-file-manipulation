import static org.junit.Assert.*;

import org.junit.Test;

import conexaoFTP.ConectaFTP;
import manipuladorArquivos.LerArquivoFTP;

public class LerArquivoFTPTeste {
	ConectaFTP con = new ConectaFTP("", "", "");
	LerArquivoFTP a = new LerArquivoFTP(con, "");
	
	@Test
	public void lerArquivo() 
	{
		
	}

}
