package conexaoFTP;
import java.util.ArrayList;
import java.io.IOException;
 
import org.apache.commons.net.ftp.FTPClient;

public class ConectaFTP {
	private static ArrayList<String> dados = new ArrayList<String>();
	private FTPClient ftpClient = new FTPClient();
	
	public ConectaFTP(String host, String user, String pass){
		dados.add(host);
		dados.add(user);
		dados.add(pass);
	}
	
	public FTPClient getClient(){
		return ftpClient;
	}
	
	public void abreConexao(){
		try{	
			ftpClient.connect(dados.get(0), 21);
			ftpClient.login(dados.get(1), dados.get(2));
		} catch(IOException ex){
			System.out.println("Oops! Algo deu errado!");
	        ex.printStackTrace();
		}
	}
	
	public void fechaConexao(){
		try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
	}
}