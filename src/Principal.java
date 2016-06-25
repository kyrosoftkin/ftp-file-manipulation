import FTPconnect.FTPconnect;
import manipuladorArquivos.FTPFileReader;

public class Principal {
	public static void main(String[] args)
	{
		FTPconnect con = new FTPconnect("", "", "");
		FTPFileReader arq = new FTPFileReader(con, "");
		
		arq.listarArquivos();
	}
}